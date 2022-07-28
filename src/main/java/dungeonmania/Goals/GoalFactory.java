package dungeonmania.Goals;

public class GoalFactory {
    public static Goal getGoal(String goalType) {
		switch (goalType) {
			case "exit":
				return new ExitGoal(goalType);
			case "boulders":
				return new BouldersGoal(goalType);
			case "enemies":
				return new EnemiesGoals(goalType);
			case "treasure":
				return new TreasureGoal(goalType);
			case "AND":
				return new ANDGoal(goalType);
			case "OR":
				return new ORGoal(goalType);
			default:
				return null;
		}
	}
}
