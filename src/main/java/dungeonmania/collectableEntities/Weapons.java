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
        this.durability = durability;
    }

    /**
     * When weapon is used, durability decreases
     */
    @Override
    public void use() {
        this.durability -= 1;
        if (this.durability == 0) {
            this.getPlayer().getInventory().remove(this);
        }
    }

    /**
     * Getter for durability
     * @return
     */
    public int getDurability() {
        return durability;
    }

    /**
     * Setter for durability
     * @param durability
     */
    public void setDurability(int durability) {
        this.durability = durability;
    }
}
