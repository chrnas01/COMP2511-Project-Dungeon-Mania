package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public abstract class Weapons extends CollectableEntity {

    private int durability;

    /**
     * Constructor for Weapons
     * @param id
     * @param position
     * @param type
     */
    public Weapons(String id, Position position, String type, int durability) {
        super(id, position, type);
        this.setDurability(durability);
    }

    /**
     * When weapon is used, durability decreases
     */
    public void use() {
        this.setDurability(this.getDurability() - 1);
        if (this.getDurability() == 0) {
            this.getPlayer().getInventory().remove(this);
        }
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }
}
