package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public class Shield extends Weapons {

    private static final double DAMAGE_REDUCTION = 0.6;
    private static final int DURABILITY = 10;

    /**
     * Constructor for Shield
     * @param id
     * @param position
     * @param type
     */
    public Shield(String id, Position position, String type) {
        super(id, position, type);
        this.setDurability(DURABILITY);
    }
    
    public void use() {
        setDurability(getDurability() - 1);
    }
}
