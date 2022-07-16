package dungeonmania.movingEntities;

import java.util.List;

import dungeonmania.Entity;
import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.staticEntities.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Mercenary extends MovingEntity {

    /**
     * Constructor for Mercenary
     * @param id
     * @param position
     * @param type
     * @param health
     * @param attack
     */
    public Mercenary(String id, Position position, String type, int health, int attack) {
        super(id, position, type, health, attack);
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
                return;
            } else if (entity instanceof Boulder || entity instanceof Door) {
                return;
            } else if (entity instanceof Portal && ((Portal) entity).teleport(dungeon, direction, this)) {
                dungeon.moveEntity(old, this.getPosition(), this);
            } else if (entity instanceof Portal) {
                return;
            } else {
                dungeon.moveEntity(old, next_position, this);
                this.setPosition(next_position);
            }
        }
    }
}
