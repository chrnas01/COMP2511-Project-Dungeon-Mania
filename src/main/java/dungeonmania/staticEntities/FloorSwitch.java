package dungeonmania.staticEntities;

import java.util.List;

import dungeonmania.Entity;
import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntity {

    private boolean underBoulder;

    /**
     * Constructor for FloorSwitch
     * @param id
     * @param position
     * @param type
     */
    public FloorSwitch (String id, Position position, String type) {
        super(id, position, type);
    }

    public boolean getUnderBoulder() {
        return this.underBoulder;
    }


    /**
     * Check if there is a boulder on top. Update values accordingly.
     * @param dungeon
     */
    public void checkBoulder(DungeonMap dungeon) {
        List<Entity> ent_in_position = dungeon.getMap().get(this.getPosition());
        if (ent_in_position.isEmpty()) {
            this.underBoulder = false; return;
        }
        for (Entity ent : ent_in_position) {
            if (ent instanceof Boulder) {
                this.underBoulder = true; return;
            }
        }
    }

}
