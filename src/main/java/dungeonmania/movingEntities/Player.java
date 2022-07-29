package dungeonmania.movingEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dungeonmania.DungeonMap.Config;
import dungeonmania.Entity;
import dungeonmania.Inventory;
import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.collectableEntities.*;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.staticEntities.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Player extends MovingEntity {

    private boolean isInvincible = false;
    private boolean isInvisible = false;
    private int potionTime;
    private List<CollectableEntity> potionQueue = new ArrayList<>();

    private boolean hasKey = false;

    int bow_durability=0;
    int shield_durability=0;
    int sword_durability=0;
    Bow bow=null;
    Shield shield=null;
    Sword sword=null;
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
    public Player(String id, Position position, String type, double health, int attack) {
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
        return this.bow_durability>0;
    }
    public boolean getHasSword(){
        return this.sword_durability>0;
    }

    public boolean getHasShield() {
        return this.shield_durability>0;
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

            if (entity instanceof Wall) {
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

        // for (Entity entity : entities) {
        //     if (this.getInvisible()) {
        //         break;
        //     }
        //     if (entity instanceof MovingEntity && !entity.getType().equals("player")) {
        //         // battle
        //     }
        // }

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
        this.tickPotions();
        if (item instanceof Bomb) {
            this.getInvClass().placeBomb(itemId, dungeon);
        } else if (this.getPotionTime() > 0) {
            CollectableEntity potion = this.getInvClass().getItem(itemId);
            this.potionQueue.add(potion);
            this.getInvClass().removeItem(itemId);
        } else {
            this.getInvClass().useItem(itemId);
        }
    }

    /**
     * Ticker for the Players potion time
     */
    public void tickPotions() {
        if (this.getPotionTime() == 0) {
            this.setInvincible(false);
            this.setInvisible(false);
        } else if (this.getPotionTime() != 0) {
            this.setPotionTime(this.getPotionTime() - 1);
        }
        if (!this.getInvincible() && !this.getInvisible() && this.potionQueue.size() != 0) {
            ((CollectablePotion) this.potionQueue.get(0)).delayUse();
            this.potionQueue.remove(0);
        }
    }

    /**
     * Helper to check if player can build the bow
     * 
     * @return boolean
     */
    public boolean canBuildBow() {
        int wood_count = this.getInvClass().countItem("wood");
        int arrow_count = this.getInvClass().countItem("arrow");
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
        int wood_count = this.getInvClass().countItem("wood");
        int treasure_count = this.getInvClass().countItem("treasure");
        if (wood_count < 2 || (treasure_count < 1 && !this.getHasKey())) {
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
        this.bow_durability = 1;
        Bow bow = new Bow("builtBow", this.getPosition(), "bow", dungeon.getConfig().BOW_DURABILITY);
        this.getInvClass().pickup(bow, this);
        this.bow=bow;
    }

    public void buildShield(DungeonMap dungeon) {
        this.getInvClass().shieldMaterials();
        this.shield_durability = 1;
        Shield shield = new Shield("builtShield", this.getPosition(), "shield",
                dungeon.getConfig().SHIELD_DURABILITY, dungeon.getConfig().SHIELD_DEFENCE);
        this.getInvClass().pickup(shield, this);
        this.shield=shield;
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
        if (this.getInvClass().countItem("treasure") < merc.getBribeAmount()) {
            throw new InvalidActionException("Not enough gold to bribe");
        }
        merc.bribe();
        this.allies.add(merc);
        for (int i = 0; i < merc.getBribeAmount(); i++) {
            this.getInvClass().spendCoins();
        }
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
    public float attack(int entity_defense){
        int attack=5;
        if(this.getHasSword()){
            this.sword_durability-=1;
            attack+=1;
        }


        if(this.getHasBow()){
            this.bow_durability-=1;
            attack*=2;
        }
        return (float) (attack-entity_defense)/5;


    }
    public float defense(int entity_attack){
        if(this.getHasShield()){
            this.shield_durability-=1;
            return (float) (entity_attack-2)/10;
        }
        return (float) entity_attack/10;
    }

}
