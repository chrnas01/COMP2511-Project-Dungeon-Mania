package dungeonmania.staticEntities;

import dungeonmania.Entity;
import dungeonmania.util.Position;

public class StaticEntity extends Entity {

    /**
     * Constructor for StaticEntity
     * @param id
     * @param position
     * @param type
     */
    public StaticEntity(String id, Position position, String type) {
        super(id, position, type);
    }
    
}
