package dungeonmania.collectableEntities;

import dungeonmania.DungeonManiaController;
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
        if(dmc.getTickCounter()<30){
            dmc.rewind(dmc.getTickCounter());
        }else{
            dmc.rewind(30);
        }
    }


}
