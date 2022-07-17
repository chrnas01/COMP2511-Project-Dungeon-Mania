package dungeonmania.Goals;

import java.util.List;
import java.util.Map;

import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.util.Position;

public abstract class Goal {
    private String goalType;

    public Goal (String goalType) {
        this.goalType = goalType;
    }

    /**
     * Getter for goalType
     * @return goalType
     */
    String getGoalType() {
        return this.goalType;
    }

    /**
     * Given a dungeonMap check if the specified goal is complete 
     * @param map
     * @return true if the goal is complete, false otherwise.
     */
    public abstract boolean goalIsComplete(DungeonMap dungeon);

    @Override
    public String toString() {
        return "";
        // if (goal.getGoalName().equals("AND") && !goal.isGoalComplete(map)) {
        //     return "(" + String.join(" AND ", getIncompleteChildrenGoals(goal, map)) + ")";
        // } else if (goal.getGoalName().equals("OR") && !goal.isGoalComplete(map)) {
        //     return "(" + String.join(" OR ", getIncompleteChildrenGoals(goal, map)) + ")";
        // } else if (!goal.isGoalComplete(map)) {
        //     return ":" + goal.getGoalName();
        // } else {
        //     return "";
        // }
    }
}



