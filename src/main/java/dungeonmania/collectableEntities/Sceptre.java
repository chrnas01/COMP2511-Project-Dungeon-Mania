package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public class Sceptre extends CollectableEntity {

    private int duration;

    public Sceptre(String id, Position position, String type, int duration) {
        super(id, position, type);
        this.duration = duration;
    }

    @Override
    public void use() {
        return;
    }

    /**
     * Getter for duration
     * @return duration
     */
    public int getDuration() {
        return this.duration;
    }
}
