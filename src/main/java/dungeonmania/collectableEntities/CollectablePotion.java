package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public abstract class CollectablePotion extends CollectableEntity {

    /**
     * Constructor for CollectablePotion
     * @param id
     * @param type
     * @param position
     */
    public CollectablePotion(String id, Position position, String type) {
        super(id, position, type);
    }

    abstract public void use();
    
}
