package dungeonmania.Goals;

import dungeonmania.DungeonMap.DungeonMap;

public abstract class Goal {
    private String goalType;

    public Goal (String goalType) {
        this.goalType = goalType;
    }

    /**
     * Getter for goalType
     * @return goalType
     */
    public String getGoalType() {
        return this.goalType;
    }

    /**
     * Given a dungeonMap check if the specified goal is complete 
     * @param map
     * @return true if the goal is complete, false otherwise.
     */
    public abstract boolean goalIsComplete(DungeonMap dungeon);
}



