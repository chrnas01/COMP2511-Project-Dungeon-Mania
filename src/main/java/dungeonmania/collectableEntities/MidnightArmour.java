package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public class MidnightArmour extends Weapons {

    private int attack;
    private int defence;

    /**
     * @param id
     * @param position
     * @param type
     * @param durability
     */
    public MidnightArmour(String id, Position position, String type, int durability) {
        super(id, position, type, durability);
    }

    /**
     * @param id
     * @param position
     * @param type
     * @param attack
     * @param defence
     */
    public MidnightArmour(String id, Position position, String type, int attack, int defence) {
        this(id, position, type, -1);
        this.attack = attack;
        this.defence = defence;
    }

    // Getters for attack and defence
    public int getAttack() {
        return this.attack;
    }

    public int getDefence() {
        return this.defence;
    }

}
