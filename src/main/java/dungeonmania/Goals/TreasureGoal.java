package dungeonmania.Goals;

import dungeonmania.DungeonMap.DungeonMap;

public class TreasureGoal extends Goal {

    public TreasureGoal(String goalType) {
        super(goalType);
    }

    /**
     * Check if all treasures have been collected
     * 
     * @param dungeon
     * @return
     */
    @Override
    public boolean goalIsComplete(DungeonMap dungeon) {
        int treasureGoal = dungeon.getConfig().TREASURE_GOAL;
        int treasureCollected = dungeon.getPlayer().getInvClass().countItem("treasure");
        int sunstoneCollected = dungeon.getPlayer().getInvClass().countItem("sun_stone");

        return (treasureCollected + sunstoneCollected) >= treasureGoal;
        
    }
}
