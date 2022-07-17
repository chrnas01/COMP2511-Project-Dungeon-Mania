package dungeonmania.Goals;

import java.util.List;
import java.util.Map;

import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.util.Position;

public class TreasureGoal extends Goal {
    
    public TreasureGoal (String goalType) {
        super(goalType);
    }

    /**
     * Check if all treasures have been collected
     * @param dungeon
     * @return
     */
    @Override
    public boolean goalIsComplete(DungeonMap dungeon) {
        Map<Position, List<Entity>> dungeonMap = dungeon.getMap();
        
        for (List<Entity> entities : dungeonMap.values()) {
            for (Entity entity : entities) {
                if (entity.getType().equals("treasure")) {
                        return false;
                    }
            }
        }
        return true;
    }
}
