package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public abstract class CollectablePotion extends CollectableEntity {

    private int duration;

    /**
     * Constructor for CollectablePotion
     * 
     * @param id
     * @param position
     * @param type
     * @param duration
     */
    public CollectablePotion(String id, Position position, String type, int duration) {
        super(id, position, type);
        this.duration = duration;
    }

    /**
     * Use potion from inventory
     */
    abstract public void use();

    /**
     * Apply potion effects after delayed use.
     */
    abstract public void delayUse();

    /**
     * Getter for duration
     * @return Duration from Config
     */
    public int getDuration() {
        return this.duration;
    }

}
