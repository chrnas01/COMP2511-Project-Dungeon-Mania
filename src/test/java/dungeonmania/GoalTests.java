package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.Goals.*;
import dungeonmania.collectableEntities.Treasure;
import dungeonmania.movingEntities.*;
import dungeonmania.util.Position;

public class GoalTests {

    @Test
    public void testBasicTreasureGoal() {
        DungeonMap dungeon = new DungeonMap("1", "d_complexGoalsTest_andAll", 1, "c_base_config");

        TreasureGoal goal = new TreasureGoal("treasure");
        Treasure treasure1 = new Treasure("30", new Position (0, 0), "treasure");

        // Initally goal is not complete
        assertFalse(goal.goalIsComplete(dungeon));

        // Add treasure to player inventory
        Player player = dungeon.getPlayer();
        player.getInvClass().pickup(treasure1, player);

        assertTrue(goal.goalIsComplete(dungeon));
    }

    @Test
    public void testBasicEnemyGoal() {
        DungeonMap dungeon = new DungeonMap("1", "d_complexGoalsTest_andAll", 1, "c_base_config");
        EnemiesGoals goal = new EnemiesGoals("enemy");

        // Initally goal is not complete
        assertFalse(goal.goalIsComplete(dungeon));

        Entity spider = dungeon.getMap().get(new Position(2, 2)).get(0);
        dungeon.moveEntity(spider.getPosition(), new Position(1, 1), spider);
        dungeon.handleBattle();

        assertTrue(goal.goalIsComplete(dungeon));
    }

    @Test 
    public void testBasicBouldersGoal() {
        DungeonMap dungeon = new DungeonMap("1", "d_complexGoalsTest_andAll", 1, "c_base_config");
        BouldersGoal goal = new BouldersGoal("boulders");

        // Initally goal is not complete
        assertFalse(goal.goalIsComplete(dungeon));

        Entity boulder = dungeon.getMap().get(new Position(3, 1)).get(0);
        dungeon.moveEntity(boulder.getPosition(), new Position(4, 1), boulder);

        assertTrue(goal.goalIsComplete(dungeon));
    }

    @Test
    public void testBasicExitGoal() {
        DungeonMap dungeon = new DungeonMap("1", "d_complexGoalsTest_andAll", 1, "c_base_config");
        ExitGoal goal = new ExitGoal("exit");

        // Initally goal is not complete
        assertFalse(goal.goalIsComplete(dungeon));

        Entity player = dungeon.getPlayer();
        dungeon.moveEntity(player.getPosition(), new Position(3, 3), player);

        assertTrue(goal.goalIsComplete(dungeon));
    }

    @Test 
    public void testComplexANDGoal() {
        DungeonMap dungeon = new DungeonMap("1", "d_complexGoalsTest_andAll", 1, "c_base_config");
        Goal goal = dungeon.getGoal();

        // Initally goal is not complete
        assertFalse(goal.goalIsComplete(dungeon));

        // Player kills enemy
        Entity spider = dungeon.getMap().get(new Position(2, 2)).get(0);
        dungeon.moveEntity(spider.getPosition(), new Position(1, 1), spider);
        dungeon.handleBattle();
        
        // Move player to exit 
        dungeon.moveEntity(dungeon.getPlayer().getPosition(), new Position(3, 3), dungeon.getPlayer());

        // Move boulder on switch
        Entity boulder = dungeon.getMap().get(new Position(3, 1)).get(0);
        dungeon.moveEntity(boulder.getPosition(), new Position(4, 1), boulder);

        // Goal should still be incomplete
        assertFalse(goal.goalIsComplete(dungeon));

        // Player picks up treasure
        Treasure treasure1 = new Treasure("30", new Position (0, 0), "treasure");
        Player player = dungeon.getPlayer();
        player.getInvClass().pickup(treasure1, player);

        assertTrue(goal.goalIsComplete(dungeon));
    }

    @Test
    public void testComplexORGoal() {
        DungeonMap dungeon = new DungeonMap("1", "d_complexGoalsTest_andAll2", 1, "c_base_config");
        Goal goal = dungeon.getGoal();

        // Initally goal is not complete
        assertFalse(goal.goalIsComplete(dungeon));

        // Player kills enemy
        Entity spider = dungeon.getMap().get(new Position(2, 2)).get(0);
        dungeon.moveEntity(spider.getPosition(), new Position(1, 1), spider);
        dungeon.handleBattle();

        // Goal should still be incomplete
        assertFalse(goal.goalIsComplete(dungeon));
        
        // Move player to exit 
        dungeon.moveEntity(dungeon.getPlayer().getPosition(), new Position(3, 3), dungeon.getPlayer());

        assertTrue(goal.goalIsComplete(dungeon));
    }
}
