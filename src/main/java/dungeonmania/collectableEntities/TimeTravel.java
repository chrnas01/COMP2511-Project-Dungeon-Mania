package dungeonmania.collectableEntities;

import dungeonmania.DungeonManiaController;
import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;

public class TimeTravel extends CollectableEntity {
    /**
     * Constructor for CollectableEntity
     *
     * @param id
     * @param position
     * @param type
     */
    public TimeTravel(String id, Position position, String type) {
        super(id, position, type);
    }

    @Override
    public void use() {

    }

    public void travelBefore(DungeonManiaController dmc) {
//        dmc.loadGame("10s before game");
    }

    public void travelAfter(DungeonManiaController dmc) throws InvalidActionException {
        dmc.tick(this.getPlayer().getId());
    }

}
