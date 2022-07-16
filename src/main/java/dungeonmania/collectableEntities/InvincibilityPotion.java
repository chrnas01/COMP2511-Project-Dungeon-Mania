package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public class InvincibilityPotion extends CollectablePotion {

    /**
     * Constructor for InvincibilityPotion
     * @param id
     * @param position
     * @param type
     * @param duration
     */
    public InvincibilityPotion(String id, Position position, String type, int duration) {
        super(id, position, type, duration);
    }

    @Override
    public void use() {
        // change the players invincibility
        this.getPlayer().getInventory().remove(this);
    }
}
