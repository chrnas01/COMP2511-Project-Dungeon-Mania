package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.Battle.*;
import dungeonmania.collectableEntities.InvincibilityPotion;
import dungeonmania.movingEntities.*;
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

    @Test
    public void testPlayerisInvisible() {
        Position pos = new Position(1, 1);
        
        ZombieToast zombie = new ZombieToast("1", pos, "zombie_toast", 5, 5);
        Player player = new Player("2", pos, "player", 10, 10);
        player.setInvisible(true);

        Battle battle = new Battle(player, zombie);
        MovingEntity winner = battle.combat();

        assertTrue(winner == null); 
    }

    @Test
    public void testPlayerIsInvincible() {
        Position pos = new Position(1, 1);
        
        Spider spider = new Spider("1", pos, "spider", 5, 5);
        Player player = new Player("2", pos, "player", 10, 10);
        InvincibilityPotion potion = new InvincibilityPotion("3", pos, "invincibility_potion", 3);
        
        // Add and use potion
        player.addPotionQueue(potion);
        player.setInvincible(true);

        Battle battle = new Battle(player, spider);
        MovingEntity winner = battle.combat();

        // There should only be one round 
        Round r1 = battle.getRounds().get(0);
        assertTrue(r1.getCurrentEnemyHealth() == 0);
        assertTrue(r1.getCurrentPlayerHealth() == battle.getInitialPlayerHealth());
        
        assertTrue(r1.getDeltaEnemyHealth() == -battle.getInitialEnemyHealth());
        assertTrue(r1.getDeltaPlayerHealth() == 0);

        assertTrue(winner == player);
        

        // Round 2 doesnt exist
        assertThrows(IndexOutOfBoundsException.class, () -> battle.getRounds().get(1)); 
    }
}
