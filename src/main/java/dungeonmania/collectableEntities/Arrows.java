package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public class Arrows extends CollectableItem {

    /**
     * Constructor for Arrows
     * @param id
     * @param position
     * @param type
     */
    public Arrows(String id, Position position, String type) {
        super(id, position, type);
    }

    @Override
    public void use() {
        this.getPlayer().getInventory().remove(this);
    }
}
