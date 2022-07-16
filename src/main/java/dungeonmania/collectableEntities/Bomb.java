package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public class Bomb extends Weapons {

    /**
     * Constructor for Bomb
     * @param id
     * @param type
     * @param position
     */
    public Bomb(String id, Position position, String type) {
        super(id, position, type);
    }

    @Override
    public void use() {
        this.getPlayer().getInventory().remove(this);
    }
}
