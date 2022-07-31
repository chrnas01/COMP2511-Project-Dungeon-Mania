package dungeonmania.collectableEntities;

import dungeonmania.DungeonManiaController;
import dungeonmania.util.Position;

public class TimeTurner extends CollectableEntity {

    /**
     * Constructor for CollectableEntity
     *
     * @param id
     * @param position
     * @param type
     */
    public TimeTurner(String id, Position position, String type) {
        super(id, position, type);
    }

    @Override
    public void use() {

    }

    public void travelBefore(DungeonManiaController dmc, int rewindTicks) {
        dmc.rewind(rewindTicks);
    }
}
