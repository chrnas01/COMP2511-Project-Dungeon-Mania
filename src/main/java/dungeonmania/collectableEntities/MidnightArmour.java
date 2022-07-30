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

    /**
     * Getter for attack
     * @return attack
     */
    public int getAttack() {
        return this.attack;
    }

    /**
     * Getter for defence
     * @return defence
     */
    public int getDefence() {
        return this.defence;
    }

}
