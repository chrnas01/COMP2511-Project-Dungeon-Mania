package dungeonmania.util;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.Goals.*;

public class GoalUtil {
     /**
     * Prints a given goal as per the specification
     * @param goal 
     * @param dungeon
     * @return 
     */
    public static String goalToString(Goal goal, DungeonMap dungeon) {
        if (goal.getGoalType().equals("AND") && !goal.goalIsComplete(dungeon)) {
            return "(" + String.join(" AND ", getIncompleteSubGoals(goal, dungeon)) + ")";
        } else if (goal.getGoalType().equals("OR") && !goal.goalIsComplete(dungeon)) {
            return "(" + String.join(" OR ", getIncompleteSubGoals(goal, dungeon)) + ")";
        } else if (!goal.goalIsComplete(dungeon)) {
            return ":" + goal.getGoalType();
        } else {
            return "";
        }
    }

    /**
     * Gather all incomplete subgoals 
     * @param goal 
     * @param dungeon
     * @return 
     */
    private static List<String> getIncompleteSubGoals(Goal goal, DungeonMap dungeon) {
        List<String> currentGoals = new ArrayList<String>();
        for (Goal childGoal : ((ComplexGoal)goal).getSubGoal()) {
            if (!childGoal.goalIsComplete(dungeon)) {
                currentGoals.add(goalToString(childGoal, dungeon));
            }
        }
        return currentGoals;
    }
}
