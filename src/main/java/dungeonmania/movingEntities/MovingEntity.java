package dungeonmania.movingEntities;

import dungeonmania.Entity;
import dungeonmania.util.Position;

public abstract class MovingEntity extends Entity {
    public MovingEntity(String id, Position position, String type) {
        super(id, position, type);
    }

    public void moving(){}
}
