package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public abstract class Weapons extends CollectableEntity {

    private int durability;

    public Weapons(String id, Position position, String type) {
        super(id, position, type);
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }
}
