package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public abstract class CollectablePotion extends CollectableEntity {

    private int duration;

    /**
     * Constructor for CollectablePotion
     * @param id
     * @param position
     * @param type
     * @param duration
     */
    public CollectablePotion(String id, Position position, String type, int duration) {
        super(id, position, type);
        this.duration = duration;
    }

    abstract public void use();

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    
}
