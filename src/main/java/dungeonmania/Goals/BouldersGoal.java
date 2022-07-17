package dungeonmania.Goals;

import java.util.List;
import java.util.Map;

import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.staticEntities.FloorSwitch;
import dungeonmania.util.Position;

public class BouldersGoal extends Goal {
    
    public BouldersGoal (String goalType) {
        super("boulders");
    }

    /**
     * Checks if all floor switches have a boulder ontop of them.
     * @param dungeon
     * @return
     */
    @Override
    public boolean goalIsComplete(DungeonMap dungeon) {
        Map<Position, List<Entity>> dungeonMap = dungeon.getMap();

        for(List<Entity> entityList : dungeonMap.values()){
            for (Entity entity : entityList) {
                if (entity instanceof FloorSwitch) {
                    // If any floor switch doesn't have a boulder ontop then goal is not complete.
                    ((FloorSwitch) entity).checkBoulder(dungeon);
                    if (!((FloorSwitch) entity).getUnderBoulder()) {
                        return false;
                    }
                }
            }
        };
        
        return true;
    }
}
