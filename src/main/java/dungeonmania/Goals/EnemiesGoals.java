package dungeonmania.Goals;

import dungeonmania.DungeonMap.DungeonMap;

public class EnemiesGoals extends Goal {
    
    public EnemiesGoals (String goalType) {
        super(goalType);
    }

    /**
     * Check if enemies goal has been met
     * @param  dungeon
     * @return
     */
    @Override
    public boolean goalIsComplete(DungeonMap dungeon) {
        return dungeon.getEnemiesKilled() >= dungeon.getConfig().ENEMY_GOAL;
    }
}
