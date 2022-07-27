package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public class MidnightArmour extends Weapons{
    private int defence;

    public MidnightArmour(String id, Position position, String type, int durability, int defence) {
        super(id, position, type, durability);
        this.setDefence(defence);
    }
    public int getDefence() {
        return this.defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }
}
