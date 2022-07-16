package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public class InvincibilityPotion extends CollectablePotion {

    /**
     * Constructor for InvincibilityPotion
     * @param id
     * @param position
     * @param type
     */
    public InvincibilityPotion(String id, Position position, String type) {
        super(id, position, type);
    }

    @Override
    public void use() {
        // something that makes the player invincible //
        this.getPlayer().getInventory().remove(this);
    }
}
