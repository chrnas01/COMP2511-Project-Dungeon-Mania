package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public class Sword extends Weapons {

    private int attack;

    /**
     * Constructor for Sword
     * @param id
     * @param position
     * @param type
     */
    public Sword(String id, Position position, String type, int durability, int attack) {
        super(id, position, type, durability);
        this.setAttack(attack);
    }

    
    public int getAttack() {
        return this.attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }
}

