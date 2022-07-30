package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public class Shield extends Weapons {

    private int defence;

    /**
     * Constructor for Shield
     * @param id
     * @param position
     * @param type
     */
    public Shield(String id, Position position, String type, int durability, int defence) {
        super(id, position, type, durability);
        this.defence = defence;
    }
    
    /**
     * Getter for defence
     * @return defence
     */
    public int getDefence() {
        return this.defence;
    }

}
