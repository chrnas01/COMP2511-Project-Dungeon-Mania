package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public class Sword extends Weapons {

    private static final double DAMAGE = 1;
    private static final int DURABILITY = 10;

    /**
     * Constructor for Sword
     * @param id
     * @param position
     * @param type
     */
    public Sword(String id, Position position, String type) {
        super(id, position, type);
        this.setDurability(DURABILITY);
    }

    public void use() {
        setDurability(getDurability() - 1);
    }
}

