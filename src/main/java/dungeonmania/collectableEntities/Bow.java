package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public class Bow extends Weapons {

    private final int MULTIPLIER = 2;

    /**
     * Constructor for Bow
     * @param id
     * @param position
     * @param type
     */
    public Bow(String id, Position position, String type, int durability) {
        super(id, position, type, durability);
    }


    public int getMultiplier() {
        return this.MULTIPLIER;
    }

}
