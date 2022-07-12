package dungeonmania.entity.staticentity;

import dungeonmania.entity.Entity;
import dungeonmania.entity.EntityType;
import dungeonmania.util.Position;

public class StaticEntity extends Entity {
    public StaticEntity(Position position){
        super(position,EntityType.STATIC);
    }
}
