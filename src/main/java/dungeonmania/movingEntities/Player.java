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
    private List<CollectableEntity> potionQueue = new ArrayList<>();

    private boolean hasKey = false;
    private boolean hasBow = false;
    private boolean hasShield = false;
    private boolean hasSceptre = false;
    private boolean hasArmour = false;
    private Inventory inventory = new Inventory(this);

    private Position prevPos = null;
    private int bribeRadius = 0;
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
    public Player(String id, Position position, String type, double health, int attack) {
        super(id, position, type, health, attack);
        this.isInvincible = false;
        this.isInvisible = false;

    }

    /**
     * Getter for inventory
     * 
     * @return inventory
     */
    public Inventory getInvClass() {
        return this.inventory;
    }

    /**
     * Getter for inventory list
     * 
     * @return inventory list
     */
    public List<CollectableEntity> getInventory() {
        return this.inventory.getInventory();
    }

    /**
     * Getter for potionQueue
     * 
     * @return potionQueue
     */
    public List<CollectableEntity> getPotionQueue() {
        return this.potionQueue;
    }

    /**
     * Adds potions to end of queue
     * 
     * @param potion
     */
    public void addPotionQueue(CollectableEntity potion) {
        this.potionQueue.add(potion);
    }

    /**
     * Getter for isInvincible
     * 
     * @return isInvincible
     */
    public boolean getInvincible() {
        return this.isInvincible;
    }

    /**
     * Setter for isInvincible
     * 
     * @param isInvincible
     */
    public void setInvincible(boolean isInvincible) {
        this.isInvincible = isInvincible;
    }

    /**
     * Getter for isInvisible
     * 
     * @return isInvisible
     */
    public boolean getInvisible() {
        return this.isInvisible;
    }

    /**
     * Setter for isInvisible
     * 
     * @param isInvisible
     */
    public void setInvisible(boolean isInvisible) {
        this.isInvisible = isInvisible;
    }

    /**
     * Getter for potionTime
     * 
     * @return
     */
    public int getPotionTime() {
        return this.potionTime;
    }

    /**
     * Setter for potionTime
     * 
     * @param time
     */
    public void setPotionTime(int time) {
        this.potionTime = time;
    }

    /**
     * Getter for hasKey
     * 
     * @return true if player has key false otherwise
     */
    public boolean getHasKey() {
        return this.hasKey;
    }

    /**
     * Setter for hasKey
     * 
     * @param hasKey
     */
    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    /**
     * Getter for hasBow
     * 
     * @return hasBow
     */
    public boolean getHasBow() {
        return this.hasBow;
    }

    /**
     * Getter for hasShield
     * 
     * @return
     */
    public boolean getHasShield() {
        return this.hasShield;
    }

    /**
     * Getter for hasSceptre
     * 
     * @return
     */
    public boolean getHasSceptre() {
        return this.hasSceptre;
    }

    /**
     * Getter has hasArmour
     * 
     * @return
     */
    public boolean getHasArmour() {
        return this.hasArmour;
    }

    /**
     * Getter for prePos
     * 
     * @return previous position of player
     */
    public Position getPrevPos() {
        return this.prevPos;
    }

    /**
     * Getter for bribeRadius
     * 
     * @return bribeRadius
     */
    public int getBribeRadius() {
        return this.bribeRadius;
    }

    @Override
    public void move(Direction direction, DungeonMap dungeon) {
        this.bribeRadius = dungeon.getConfig().BRIBE_RADIUS;
        Position old = this.getPosition();
        Position next_position = this.getPosition().translateBy(direction);
        List<Entity> entities = dungeon.getMap().get(next_position);

        if (entities == null) {
            dungeon.addPosition(next_position);
            dungeon.moveEntity(this.getPosition(), next_position, this);
            this.setPosition(next_position);
            this.prevPos = old;
            return;
        }

        if (entities.size() == 0) {
            dungeon.moveEntity(this.getPosition(), next_position, this);
            this.setPosition(next_position);
            this.prevPos = old;
            return;
        }

        int i = 0;
        for (Entity entity : entities) {
            if (entity instanceof Wall) {
                return;
            }
            if ((entity instanceof Boulder && ((Boulder) entity).moveDirection(dungeon, direction))
                    || (entity instanceof Door && ((Door) entity).getOpen())) {
                dungeon.moveEntity(old, next_position, this);
                this.setPosition(next_position);
                this.prevPos = old;
                break;
            } else if (entity instanceof Door && this.getInvClass().countItem("sun_stone") == 0) {
                CollectableEntity key = this.inventory.findKey(((Door) entity).getKeyId());
                if (key == null) {
                    return;
                }
                key.use();
                ((Door) entity).setOpen(true);
                dungeon.moveEntity(old, next_position, this);
                this.setPosition(next_position);
                this.prevPos = old;
                break;
            } else if (entity instanceof Door) {
                ((Door) entity).setOpen(true);
                dungeon.moveEntity(old, next_position, this);
                this.setPosition(next_position);
                this.prevPos = old;
                break;
            } else if (entity instanceof Portal && ((Portal) entity).teleport(dungeon, direction, this)) {
                dungeon.moveEntity(old, this.getPosition(), this);
                this.prevPos = old;
            } else if (entity instanceof Portal || entity instanceof Boulder) {
                return;
            } else if (i == entities.size() - 1) {
                dungeon.moveEntity(old, next_position, this);
                this.setPosition(next_position);
                this.prevPos = old;
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
        if (item == null) {
            throw new InvalidActionException("Item not in inventory");
        }
        if (!(item instanceof Bomb) && !(item instanceof CollectablePotion)) {
            throw new IllegalArgumentException("Item not a bomb or potion");
        }
        if (item instanceof Bomb) {
            this.getInvClass().placeBomb(itemId, dungeon);
        } else if (this.potionQueue.size() == 0) {
            CollectableEntity potion = this.getInvClass().getItem(itemId);
            this.potionQueue.add(potion);
            potion.use();
        } else {
            CollectableEntity potion = this.getInvClass().getItem(itemId);
            this.potionQueue.add(potion);
            this.getInvClass().removeItem(itemId);
        }
        this.tickPotions();
    }

    /**
     * Ticker for the Players potion time
     */
    public void tickPotions() {
        if (this.potionQueue.size() == 0) {
            return;
        }
        if (this.getPotionTime() == 0) {
            this.setInvincible(false);
            this.setInvisible(false);
        } else if (this.getPotionTime() != 0) {
            this.setPotionTime(this.getPotionTime() - 1);
        }
        if (!this.getInvincible() && !this.getInvisible() && this.potionQueue.size() == 1) {
            this.potionQueue.remove(0);
        } else if (!this.getInvincible() && !this.getInvisible() && this.potionQueue.size() > 1) {
            this.potionQueue.remove(0);
            ((CollectablePotion) this.potionQueue.get(0)).delayUse();
        }
    }

    /**
     * Helpers to check if player can build the a buildable entity
     * 
     * @return true if they can false otherwise
     */
    public boolean canBuildBow() {
        int wood_count = this.getInvClass().countItem("wood");
        int arrow_count = this.getInvClass().countItem("arrow");
        if (wood_count < 1 || arrow_count < 3) {
            return false;
        }
        return true;
    }

    public boolean canBuildShield() {
        int wood_count = this.getInvClass().countItem("wood");
        int treasure_count = this.getInvClass().countItem("treasure");
        int sun_stone_count = this.getInvClass().countItem("sun_stone");
        if (wood_count < 2 || (treasure_count < 1 && !this.getHasKey() && sun_stone_count < 1)) {
            return false;
        }
        return true;
    }

    public boolean canBuildSpectre() {
        int wood_count = this.getInvClass().countItem("wood");
        int arrow_count = this.getInvClass().countItem("arrow");
        int treasure_count = this.getInvClass().countItem("treasure");
        int stone_count = this.getInvClass().countItem("sun_stone");
        if ((wood_count < 1 && arrow_count < 2) || (treasure_count < 1 && !this.getHasKey()) || stone_count < 1) {
            return false;
        }
        return true;
    }

    public boolean canBuildArmour() {
        int sword_count = this.getInvClass().countItem("sword");
        int stone_count = this.getInvClass().countItem("sun_stone");
        if (sword_count < 1 || stone_count < 1) {
            return false;
        }
        return true;
    }

    /**
     * Build a bow, shield, sceptre or armour
     * 
     * @param dungeon
     * @param buildable_type
     * @throws IllegalArgumentException
     * @throws InvalidActionException
     */
    public void build(DungeonMap dungeon, String buildable_type)
            throws IllegalArgumentException, InvalidActionException {
        switch (buildable_type) {
            case "bow":
                if (!this.canBuildBow()) {
                    throw new InvalidActionException("Not enough materials");
                }
                this.buildBow(dungeon);
                return;
            case "shield":
                if (!this.canBuildShield()) {
                    throw new InvalidActionException("Not enough materials");
                }
                this.buildShield(dungeon);
                return;
            case "sceptre":
                if (!this.canBuildSpectre()) {
                    throw new InvalidActionException("Not enough materials");
                }
                this.buildSceptre(dungeon);
                return;
            case "midnight_armour":
                if (dungeon.getZombiePresence() || !this.canBuildArmour()) {
                    throw new InvalidActionException("Cannot build Midnight Armour");
                }
                this.buildArmour(dungeon);
                return;
            default:
                throw new IllegalArgumentException("Buildable not of a buildable entity type.");
        }
    }

    /**
     * Helper functions for building the buildables
     * 
     * @param dungeon
     */
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

    public void buildSceptre(DungeonMap dungeon) {
        this.getInvClass().sceptreMaterials();
        this.hasSceptre = true;
        Sceptre spectre = new Sceptre("builtSceptre", this.getPosition(), "sceptre",
                dungeon.getConfig().MIND_CONTROL_DURATION);
        this.getInvClass().pickup(spectre, this);
    }

    public void buildArmour(DungeonMap dungeon) {
        this.getInvClass().armourMaterials();
        this.hasArmour = true;
        MidnightArmour armour = new MidnightArmour("builtArmour", this.getPosition(), "midnight_armour",
                dungeon.getConfig().MIDNIGHT_ARMOUR_ATTACK, dungeon.getConfig().MIDNIGHT_ARMOUR_DEFENCE);
        this.getInvClass().pickup(armour, this);
    }

    /**
     * Bribe a mercenary if possible
     * 
     * @param merc
     * @throws InvalidActionException
     */
    public void bribe(Mercenary merc) throws InvalidActionException {
        if (!merc.getIsHostile()) {
            return;
        }
        merc.bribe();
        this.allies.add(merc);
        for (int i = 0; i < merc.getBribeAmount(); i++) {
            this.getInvClass().spendCoin();
        }
    }

    /**
     * Brainwash a mercenary
     * 
     * @param merc
     */
    public void brainwash(Mercenary merc, int duration) {
        if (!merc.getIsHostile()) {
            return;
        }
        merc.brainwash(duration);
        this.allies.add(merc);
    }

    /**
     * Destroy a spawner if possible
     * 
     * @param spawner
     * @throws InvalidActionException
     */
    public void destroy(ZombieToastSpawner spawner, DungeonMap dungeon) throws InvalidActionException {
        if (!this.getHasBow() && this.getInvClass().countItem("sword") < 1) {
            throw new InvalidActionException("No weapon to destroy spawner");
        }
        spawner.destroyed(dungeon);
    }

}
