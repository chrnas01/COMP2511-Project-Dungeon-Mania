package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public abstract class CollectableItem extends CollectableEntity {

    /**
     * Constructor for CollectableItem
     * @param id
     * @param position
     * @param type
     */
    public CollectableItem(String id, Position position, String type) {
        super(id, position, type);
    }

    abstract public void use();

}