package dungeonmania.Goals;

import java.util.ArrayList;
import java.util.List;

public class ComplexGoal extends Goal {
    List<Goal> subGoal = new ArrayList<Goal>();
    
    public ComplexGoal (String goalType) {
        super(goalType);
    }

    public void addGoal(Goal goal) {
        this.subGoal.add(goal);
    }

    public List<Goal> getSubGoal() {
        return subGoal;
    }

    public void setSubGoal(List<Goal> subGoal) {
        this.subGoal = subGoal;
    }
}
