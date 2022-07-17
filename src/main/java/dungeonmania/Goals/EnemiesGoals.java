package dungeonmania.Goals;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.movingEntities.Mercenary;
import dungeonmania.util.Position;

public class EnemiesGoals extends Goal {
    
    public EnemiesGoals (String goalType) {
        super(goalType);
    }

    /**
     * Checks if all enemies have been removed from the dungeon
     * @param  dungeon
     * @return
     */
    @Override
    public boolean goalIsComplete(DungeonMap dungeon) {
        Map<Position, List<Entity>> dungeonMap = dungeon.getMap();
        List <String> enemies = new ArrayList<String>(Arrays.asList("zombie_toast_spawner", "zombie_toast", "spider"));
        
        for(List<Entity> entities : dungeonMap.values()) {
            for (Entity entity : entities) {
                if (enemies.contains(entity.getType())) {
                    return false;
                }
                // If mercenary is not friendly 
                else if (entity.getType().equals("mercenary") && !((Mercenary) entity).getIsHostile()) {
                    return false;
                }
            }
        }
        return true;
    }
}
