package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public class Treasure extends CollectableItem {

    /**
     * Constructor for Treasure
     * @param id
     * @param position
     * @param type
     */
     public Treasure (String id, Position position, String type) {
        super(id, position, type);
    }

    @Override
    public void use() {
        this.getPlayer().getInventory().remove(this);
    }
}
