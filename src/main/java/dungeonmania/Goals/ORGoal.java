package dungeonmania.Goals;

import dungeonmania.DungeonMap.DungeonMap;

public class ORGoal extends ComplexGoal {
    
    public ORGoal (String goalType) {
        super(goalType);
    }
    
    @Override
    public boolean goalIsComplete(DungeonMap dungeon) {
        boolean isComplete = false;
        for (Goal goal : this.getSubGoal()) {
            isComplete = isComplete || goal.goalIsComplete(dungeon);
        }
        return isComplete;
    }
}
