package dungeonmania.movingEntities;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import dungeonmania.Entity;
import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.staticEntities.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ZombieToast extends MovingEntity {

    /**
     * Constructor for ZombieToast
     * @param id
     * @param position
     * @param type
     * @param health
     * @param attack
     */
    public ZombieToast(String id, Position position, String type, int health, int attack) {
        super(id, position, type, health, attack);
    }

    /**
     * Generate randome direction for moving
     * @return direction
     */
    public Direction generateDirection() {
        Random rand = new Random();
        int value = rand.nextInt(4);
        List<Direction> directions =  Arrays.asList(Direction.RIGHT, Direction.UP, Direction.LEFT, Direction.DOWN);
        return directions.get(value);
    }

    @Override
    public void move(Direction direction, DungeonMap dungeon) {
       this.move(dungeon);
    }
    public void move(DungeonMap dungeon) {
        Direction direction=generateDirection();
        Position next_position = this.getPosition().translateBy(direction);
        List<Entity> entities = dungeon.getMap().get(next_position);

        if (entities == null) {
            dungeon.addPosition(next_position);
            dungeon.moveEntity(this.getPosition(), next_position, this);
            this.setPosition(next_position);
            return;
        }

        for (Entity entity: entities) {
            if (entity instanceof Wall || entity instanceof ZombieToastSpawner) {return;}
            if ((entity instanceof Boulder && ((Boulder) entity).moveDirection(dungeon, direction))
                    || (entity instanceof Door && ((Door) entity).getOpen())) {
                dungeon.moveEntity(this.getPosition(), next_position, this);
                this.setPosition(next_position);
                return;
            } else if (entity instanceof Boulder || entity instanceof Door) {
                return;
            } else {
                dungeon.moveEntity(this.getPosition(), next_position, this);
                this.setPosition(next_position);
            }
        }
    }
    
}
