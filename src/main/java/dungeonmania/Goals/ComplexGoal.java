package dungeonmania.Goals;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.DungeonMap.DungeonMap;

public abstract class ComplexGoal extends Goal {
    List<Goal> subGoal = new ArrayList<Goal>();
    
    public ComplexGoal (String goalType) {
        super(goalType);
    }

    /**
     * Given a dungeonMap check if the specified goal is complete 
     * @param map
     * @return true if the goal is complete, false otherwise.
     */
    public abstract boolean goalIsComplete(DungeonMap dungeon);

    /**
     * Add sub-goal to list 
     * @param goal
     */
    public void addGoal(Goal goal) {
        this.subGoal.add(goal);
    }

    /**
     * Getter for subGoal
     * @return list of subGoals
     */
    public List<Goal> getSubGoal() {
        return subGoal;
    }

   /**
    * Setter for subGoals
    * @param subGoal 
    */
    public void setSubGoal(List<Goal> subGoal) {
        this.subGoal = subGoal;
    }
}
