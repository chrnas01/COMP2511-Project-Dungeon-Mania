package dungeonmania.Goals;

import dungeonmania.DungeonMap.DungeonMap;

public class ANDGoal extends ComplexGoal {
    
    public ANDGoal (String goalType) {
        super(goalType);
    }

    @Override
    public boolean goalIsComplete(DungeonMap dungeon) {
        boolean isComplete = true;
        for (Goal goal : this.getSubGoal()) {
            isComplete = isComplete && goal.goalIsComplete(dungeon);
        }
        return isComplete;
    }
}
