package dungeonmania.movingEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Entity;
import dungeonmania.Inventory;
import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.collectableEntities.*;
import dungeonmania.staticEntities.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Player extends MovingEntity {

    private boolean isInvincible;
    private boolean isInvisible;
    private int potionTime;
    private List<String> potionQueue;

    private Inventory inventory = new Inventory(this);
    private List<Entity> allies = new ArrayList<>();


    /**
     * Constructor for Player
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

    public boolean getInvincible(){
        return this.isInvincible;
    }

    public void setInvisible(boolean isInvisible) {
        this.isInvisible = isInvisible;
    }

    public boolean getInvisible(){
        return this.isInvisible;
    }

    public void setPotionTime(int time) {
        this.potionTime = time;
    }

    public int getPotionTime() {
        return this.potionTime;
    }

    @Override
    public void move(Direction direction, DungeonMap dungeon) {
        Position old = this.getPosition();
        Position next_position = this.getPosition().translateBy(direction);
        List<Entity> entities = dungeon.getMap().get(next_position);

        if (entities == null) { 
            dungeon.addPosition(next_position);
            dungeon.moveEntity(this.getPosition(), next_position, this);
            this.setPosition(next_position);
            return;
        }

        for (Entity entity: entities) {
            if (entity instanceof Wall || entity instanceof ZombieToastSpawner ) {return;}
            if ((entity instanceof Boulder && ((Boulder) entity).moveDirection(dungeon, direction))
                || (entity instanceof Door && ((Door) entity).getOpen())) {
                dungeon.moveEntity(old, next_position, this);
                this.setPosition(next_position);
                break;
            } else if (entity instanceof Door) {
                CollectableEntity key = this.inventory.findKey(((Door) entity).getKeyId());
                if (key == null) {return;}
                key.use();
                ((Door)entity).setOpen(true);
                dungeon.moveEntity(old, next_position, this);
                this.setPosition(next_position);
                break;
            } else if (entity instanceof Portal && ((Portal) entity).teleport(dungeon, direction, this)) {
                dungeon.moveEntity(old, this.getPosition(), this);
            } else if (entity instanceof Portal || entity instanceof Boulder) {
                return;
            } else {
                dungeon.moveEntity(old, next_position, this);
                this.setPosition(next_position);
            }
        }

        for (Entity entity : entities) {
            if (entity instanceof CollectableEntity) {
                this.getInvClass().pickup(((CollectableEntity) entity), this);
                dungeon.removeCollectable(this.getPosition(), ((CollectableEntity) entity));
            }
        }

        for (Entity entity : entities) {
            if (this.getInvisible()) {break;}
            if (entity instanceof MovingEntity && !entity.getType().equals("player")) {
                //battle
            }
        }

    }

}
