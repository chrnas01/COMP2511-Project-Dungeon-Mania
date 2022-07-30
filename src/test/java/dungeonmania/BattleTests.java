package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.Battle.*;
import dungeonmania.movingEntities.*;
import dungeonmania.movingEntities.Player;
import dungeonmania.util.Position;


public class BattleTests {

    @Test
    public void testHydraHeals() {
        Position pos = new Position(1, 1);
        
        Hydra hydra = new Hydra("1", pos, "hydra", 10, 10, 2, 1);
        Player player = new Player("2", pos, "player", 10, 10);

        Battle battle = new Battle(player, hydra);
        battle.combat();

        Round r1 = battle.getRounds().get(0);
        assertTrue(r1.getDeltaEnemyHealth() == 2);
        assertTrue(r1.getDeltaPlayerHealth() < 0);

        Round r2 = battle.getRounds().get(1);
        assertTrue(r2.getDeltaEnemyHealth() == 2);
        assertTrue(r2.getDeltaPlayerHealth() < 0);
        
    }

    @Test
    public void testHydraDoesNotHeal() {
        Position pos = new Position(1, 1);
        
        Hydra hydra = new Hydra("1", pos, "hydra", 10, 10, 1, 0);
        Player player = new Player("2", pos, "player", 10, 10);

        Battle battle = new Battle(player, hydra);
        battle.combat();

        Round r1 = battle.getRounds().get(0);
        assertTrue(r1.getDeltaEnemyHealth() < 0);
        assertTrue(r1.getDeltaPlayerHealth() < 0);

        Round r2 = battle.getRounds().get(1);
        assertTrue(r2.getDeltaEnemyHealth() < 0);
        assertTrue(r2.getDeltaPlayerHealth() < 0);
        
    }

    @Test
    public void testHydraIncreaseAmountZero() {
        Position pos = new Position(1, 1);
        
        Hydra hydra = new Hydra("1", pos, "hydra", 10, 10, 0, 1);
        Player player = new Player("2", pos, "player", 10, 10);

        Battle battle = new Battle(player, hydra);
        battle.combat();

        Round r1 = battle.getRounds().get(0);
        assertTrue(r1.getDeltaEnemyHealth() == 0);
        assertTrue(r1.getDeltaPlayerHealth() < 0);

        Round r2 = battle.getRounds().get(1);
        assertTrue(r2.getDeltaEnemyHealth() == 0);
        assertTrue(r2.getDeltaPlayerHealth() < 0);
        
    }


}
