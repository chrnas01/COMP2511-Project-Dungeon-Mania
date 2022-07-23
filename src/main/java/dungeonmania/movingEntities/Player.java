package dungeonmania.movingEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Entity;
import dungeonmania.Inventory;
import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.collectableEntities.*;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.staticEntities.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Player extends MovingEntity {

    private boolean isInvincible = false;
    private boolean isInvisible = false;
    private int potionTime;
    private List<String> potionQueue = new ArrayList<>();

    private boolean hasKey = false;
    private boolean hasBow = false;
    private boolean hasShield = false;
    private Inventory inventory = new Inventory(this);

    private int bribe_radius = 0;
    private List<Entity> allies = new ArrayList<>();

    /**
     * Constructor for Player
     * 
     * @param id
     * @param position
     * @param type
     * @param health
     * @param attack
     */
    public Player(String id, Position position, String type, int health, int attack) {
        super(id, position, type, health, attack);
        this.isInvincible = false;
        this.isInvisible = false;

    }

    public Inventory getInvClass() {
        return this.inventory;
    }

    public List<CollectableEntity> getInventory() {
        return this.inventory.getInventory();
    }

    public void setInvincible(boolean isInvincible) {
        this.isInvincible = isInvincible;
    }

    public boolean getInvincible() {
        return this.isInvincible;
    }

    public void setInvisible(boolean isInvisible) {
        this.isInvisible = isInvisible;
    }

    public boolean getInvisible() {
        return this.isInvisible;
    }

    public void setPotionTime(int time) {
        this.potionTime = time;
    }

    public int getPotionTime() {
        return this.potionTime;
    }

    public boolean getHasKey() {
        return this.hasKey;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    public boolean getHasBow() {
        return this.hasBow;
    }

    public boolean getHasShield() {
        return this.hasShield;
    }

    public int getBribeRadius() {
        return this.bribe_radius;
    }

    @Override
    public void move(Direction direction, DungeonMap dungeon) {
        this.bribe_radius = dungeon.getConfig().BRIBE_RADIUS;
        Position old = this.getPosition();
        Position next_position = this.getPosition().translateBy(direction);
        List<Entity> entities = dungeon.getMap().get(next_position);

        if (entities == null) {
            dungeon.addPosition(next_position);
            dungeon.moveEntity(this.getPosition(), next_position, this);
            this.setPosition(next_position);
            return;
        }

        if (entities.size() == 0) {
            dungeon.moveEntity(this.getPosition(), next_position, this);
            this.setPosition(next_position);
            return;
        }

        int i = 0;
        for (Entity entity : entities) {

            if (entity instanceof Wall || entity instanceof ZombieToastSpawner) {
                return;
            }
            if ((entity instanceof Boulder && ((Boulder) entity).moveDirection(dungeon, direction))
                    || (entity instanceof Door && ((Door) entity).getOpen())) {
                dungeon.moveEntity(old, next_position, this);
                this.setPosition(next_position);
                break;
            } else if (entity instanceof Door) {
                CollectableEntity key = this.inventory.findKey(((Door) entity).getKeyId());
                if (key == null) {
                    return;
                }
                key.use();
                ((Door) entity).setOpen(true);
                entity.setType("door_open");
                dungeon.moveEntity(old, next_position, this);
                this.setPosition(next_position);
                break;
            } else if (entity instanceof Portal && ((Portal) entity).teleport(dungeon, direction, this)) {
                dungeon.moveEntity(old, this.getPosition(), this);
            } else if (entity instanceof Portal || entity instanceof Boulder) {
                return;
            } else if (i == entities.size() - 1) {
                dungeon.moveEntity(old, next_position, this);
                this.setPosition(next_position);
                break;
            }
            i += 1;
        }

        for (Entity entity : entities) {
            if (entity instanceof CollectableEntity) {
                if (entity instanceof Bomb && ((Bomb) entity).getPlaced()) {
                    continue;
                } else if (entity instanceof Key && this.getHasKey()) {
                    continue;
                } else if (entity instanceof Key) {
                    this.setHasKey(true);
                }
                this.getInvClass().pickup(((CollectableEntity) entity), this);
                dungeon.removeCollectable(this.getPosition(), ((CollectableEntity) entity));
            }
        }

        for (Entity entity : entities) {
            if (this.getInvisible()) {
                break;
            }
            if (entity instanceof MovingEntity && !entity.getType().equals("player")) {
                // battle
            }
        }

    }

    /**
     * Use a bomb or potion with given itemid
     * 
     * @param dungeon
     * @param itemId
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public void use(DungeonMap dungeon, String itemId) throws IllegalArgumentException, InvalidActionException {
        CollectableEntity item = this.getInvClass().getItem(itemId);
        if (item.equals(null)) {
            throw new InvalidActionException("Item not in inventory");
        }
        if (!(item instanceof Bomb) && !(item instanceof CollectablePotion)) {
            throw new IllegalArgumentException("Item not a bomb or potion");
        }
        if (item instanceof Bomb) {
            this.getInvClass().useItem(itemId);
        } else if (this.getPotionTime() > 0) {
            this.potionQueue.add(itemId);
        } else {
            this.getInvClass().useItem(itemId);
        }
    }

    /**
     * Ticker for the Players potion time
     */
    public void tickPotions() {
        if (this.getPotionTime() != 0) {
            this.setPotionTime(this.getPotionTime() - 1);
        }
        if (this.getPotionTime() == 0) {
            this.setInvincible(false);
            this.setInvisible(false);
        }
        if (!this.getInvincible() && !this.getInvisible() && this.potionQueue.size() != 0) {
            this.getInvClass().useItem(this.potionQueue.get(0));
            this.potionQueue.remove(0);
        }
    }

    /**
     * Helper to check if player can build the bow
     * 
     * @return boolean
     */
    public boolean canBuildBow() {
        int wood_count = this.getInvClass().countWood();
        int arrow_count = this.getInvClass().countArrows();
        if (wood_count < 1 || arrow_count < 3) {
            return false;
        }
        return true;
    }

    /**
     * Helper to check if player can build the shield
     * 
     * @return boolean
     */
    public boolean canBuildShield() {
        int wood_count = this.getInvClass().countWood();
        int treasure_count = this.getInvClass().countTreasure();
        int key_count = this.getInvClass().countKey();
        if (wood_count < 2 || (treasure_count < 1 && key_count < 1)) {
            return false;
        }
        return true;
    }

    /**
     * Build a bow or shield
     * 
     * @param dungeon
     * @param buildable_type
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public void build(DungeonMap dungeon, String buildable_type)
            throws IllegalArgumentException, InvalidActionException {
        if (!buildable_type.equals("bow") && !buildable_type.equals("shield")) {
            throw new IllegalArgumentException("Buildable must be bow or shield");
        }
        if (buildable_type.equals("bow")) {
            if (!this.canBuildBow()) {
                throw new InvalidActionException("Not enough materials");
            }
            this.buildBow(dungeon);
        } else {
            if (!this.canBuildShield()) {
                throw new InvalidActionException("Not enough materials");
            }
            this.buildShield(dungeon);
        }
    }

    // Helper functions for building bow and shield
    public void buildBow(DungeonMap dungeon) {
        this.getInvClass().bowMaterials();
        this.hasBow = true;
        Bow bow = new Bow("builtBow", this.getPosition(), "bow", dungeon.getConfig().BOW_DURABILITY);
        this.getInvClass().pickup(bow, this);
    }

    public void buildShield(DungeonMap dungeon) {
        this.getInvClass().shieldMaterials();
        this.hasShield = true;
        Shield shield = new Shield("builtShield", this.getPosition(), "shield",
                dungeon.getConfig().SHIELD_DURABILITY, dungeon.getConfig().SHIELD_DEFENCE);
        this.getInvClass().pickup(shield, this);
    }

}
