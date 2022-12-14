package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public class Wood extends CollectableItem {

    /**
     * Constructor for Wood
     * @param id
     * @param position
     * @param type
     */
    public Wood(String id, Position position, String type) {
        super(id, position, type);
    }

    @Override
    public void use() {
        this.getPlayer().getInventory().remove(this);
    }
}
