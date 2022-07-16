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

    private boolean isinBattle;
    private boolean isInvincible;
    private int invincibleTime;
    private boolean isInvisible;
    private int invisibleTime;

    private Inventory inventory = new Inventory(this);
    private List<Entity> allies = new ArrayList<>();

    public Player(String id, Position position, String type, double health, double attack) {
        super(id, position, type, health, attack);
        this.isinBattle = false;
        this.isInvincible = false;
        this.isInvisible = false;   
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

    public void move(Direction direction, DungeonMap dungeon){
        Position movement = direction.getOffset();
        Position newposition = super.getPosition().translateBy(movement);
        isinBattle = false;

        List<Entity> entities = dungeon.getMap().get(newposition);

        for (Entity entity: entities) {
            String entitytype = entity.getType();
            Position entityPosition = entity.getPosition();

            if (entitytype.equals("door")){
                Door door = (Door) entity;
                int doorkeyid = door.getKeyId();
                Position doorposition = entityPosition;

                if (newposition.equals(doorposition)){
                    Key key = (Key) inventory.findItem("key");
                    int keyid = key.getKeyId();
                    if (keyid == doorkeyid) {
                        door.setOpen(true);
                        dungeon.moveEntity(this.getPosition(), newposition, this);
                        this.setPosition(newposition);
                        inventory.useItem("key");
                    }
                }
            }
            else if (entitytype.equals("boulder")){
                Boulder boulder = (Boulder) entity;
                Position boulderPosition = entityPosition;

                if (newposition.equals(boulderPosition) && boulder.moveDirection(dungeon, direction)) {
                    this.setPosition(newposition);
                }
            }
            else if (entity instanceof CollectableEntity){
                dungeon.moveEntity(this.getPosition(), newposition, this);
                this.setPosition(newposition);
                inventory.pickup(((CollectableEntity) entity), this);

            }

            else if (entity instanceof StaticEntity){
                if (entitytype.equals("wall") || entitytype.equals("zombietoastspawner")){
                    break;
                }
                else if (entitytype.equals("portal")){
                    ((Portal) entity).teleport(dungeon, direction, this);
                    
                }
                else {
                    dungeon.moveEntity(this.getPosition(), newposition, this);
                    this.setPosition(newposition);
                }
            }
            else if (entity instanceof MovingEntity){
                //battle();
                break;
            }
        }

        


    }

}
