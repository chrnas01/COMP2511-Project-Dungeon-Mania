package dungeonmania.staticEntities;

import dungeonmania.util.Position;

public class Wall extends StaticEntity {

    /**
     * Constructor for Wall
     * @param id
     * @param position
     * @param type
     */
    public Wall (String id, Position position, String type) {
        super(id, position, type);
    }
    
}
