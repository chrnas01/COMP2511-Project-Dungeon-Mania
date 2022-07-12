package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public class Treasure extends CollectableItem {
    /**
     * Constructor for Treasure
     * @param id
     * @param type
     * @param position
     */

     public Treasure (String id, Position position, String type) {
        super(id, position, type);
    }
}
