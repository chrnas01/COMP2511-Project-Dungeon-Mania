package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public class InvisibilityPotion extends CollectablePotion {

    /**
     * Constructor for InvisibilityPotion
     * 
     * @param id
     * @param position
     * @param type
     * @param duration
     */
    public InvisibilityPotion(String id, Position position, String type, int duration) {
        super(id, position, type, duration);
    }

    @Override
    public void use() {
        this.getPlayer().setPotionTime(this.getDuration());
        this.getPlayer().setInvisible(true);
        this.getPlayer().getInventory().remove(this);
    }

    @Override
    public void delayUse() {
        this.getPlayer().setPotionTime(this.getDuration());
        this.getPlayer().setInvisible(true);
    }
}
