package dungeonmania.Goals;

import java.util.List;
import java.util.Map;

import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.util.Position;

public class ExitGoal extends Goal {
    
    public ExitGoal (String goalType) {
        super(goalType);
    }

     /**
     * Checks if player and exit have the same position in the dungeon
     * @param  dungeon
     * @return true if player and exit have the same position, otherwise false
     */
    @Override
    public boolean goalIsComplete(DungeonMap dungeon) {
        Map<Position, List<Entity>> dungeonMap = dungeon.getMap();
        
        Position player = null;
        Position exit = null;
        for(List<Entity> entities : dungeonMap.values()) {
            for (Entity entity : entities) {  
                if (entity.getType().equals("player"))
                    player = entity.getPosition();
                else if (entity.getType().equals("exit")) {
                    exit = entity.getPosition();
                }
            }
        }

        return player.equals(exit);
    }
}
