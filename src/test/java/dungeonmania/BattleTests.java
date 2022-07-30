package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.Battle.*;
import dungeonmania.collectableEntities.Bow;
import dungeonmania.collectableEntities.InvincibilityPotion;
import dungeonmania.collectableEntities.MidnightArmour;
import dungeonmania.collectableEntities.Shield;
import dungeonmania.collectableEntities.Sword;
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
        Position pos = new Position(3, 4);
        
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
        Position pos = new Position(7, 1);
        
        ZombieToast zombie = new ZombieToast("1", pos, "zombie_toast", 5, 5);
        Player player = new Player("2", pos, "player", 10, 10);
        player.setInvisible(true);

        Battle battle = new Battle(player, zombie);
        MovingEntity winner = battle.combat();

        assertTrue(winner == null); 
    }

    @Test
    public void testPlayerIsInvincible() {
        Position pos = new Position(1, 3);
        
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

    @Test
    public void testPlayerHasSword() {
        Position pos = new Position(4, 1);
        
        Spider spider = new Spider("1", pos, "spider", 5, 5);
        Player player = new Player("2", pos, "player", 10, 10);
        Sword sword = new Sword("3", pos, "sword", 1, 3);

        // Player equips sword
        player.getInvClass().pickup(sword, player);

        Battle battle = new Battle(player, spider);
        assertTrue(battle.getPlayerAttack() == 13);

        battle.combat();

        // Sword breaks after battle
        assertTrue(player.getInventory().size() == 0);
    }

    @Test
    public void testPlayerHasBow() {
        Position pos = new Position(-1, 1);
        
        Assassin assassin = new Assassin("1", pos, "assassin", 8, 8, 3, 0.5, 3);
        Player player = new Player("2", pos, "player", 5, 5);
        Bow bow = new Bow("3", pos, "bow", 1);

        // Player equips boiw
        player.getInvClass().pickup(bow, player);

        Battle battle = new Battle(player, assassin);
        assertTrue(battle.getPlayerAttack() == 10);

        battle.combat();

        // Bow breaks after battle
        assertTrue(player.getInventory().size() == 0);
    }

    @Test 
    public void testPlayerHasShield() { 
        Position pos = new Position(4, 1);
        
        Spider spider = new Spider("1", pos, "spider", 5, 5);
        Player player = new Player("2", pos, "player", 10, 10);
        Shield shield = new Shield("3", pos, "shield", 1, 3);

        // Player equips sword
        player.getInvClass().pickup(shield, player);

        Battle battle = new Battle(player, spider);
        assertTrue(battle.getPlayerAttack() == 10);
        assertTrue(battle.getEnemyAttack() == 2);

        battle.combat();

        // Shield breaks after battle
        assertTrue(player.getInventory().size() == 0);
    }

    @Test 
    public void testMidnightArmour() {
        Position pos = new Position(-1, 1);
        
        Mercenary mercenary = new Mercenary("1", pos, "mercenary", 8, 8, 8);
        Player player = new Player("2", pos, "player", 5, 5);
        MidnightArmour armour = new MidnightArmour("3", pos, "midnight_armour", 3, 4);

        // Player equips armour
        player.getInvClass().pickup(armour, player);

        Battle battle = new Battle(player, mercenary);
        assertTrue(battle.getPlayerAttack() == 8);
        assertTrue(battle.getEnemyAttack() == 4);

        battle.combat();

        // Armour cant break
        assertTrue(player.getInventory().size() == 1);
    }

    @Test
    public void testNegativeAttack() {
        Position pos = new Position(-1, 1);
        
        Mercenary mercenary = new Mercenary("1", pos, "mercenary", 8, 8, 8);
        Player player = new Player("2", pos, "player", 5, 5);
        MidnightArmour armour = new MidnightArmour("3", pos, "midnight_armour", 3, 9);

        // Player equips armour
        player.getInvClass().pickup(armour, player);

        Battle battle = new Battle(player, mercenary);
        assertTrue(battle.getPlayerAttack() == 8);
        assertTrue(battle.getEnemyAttack() == -1);

        battle.combat();

        // Assumption: Player heals since enemy has negative attack
        Round r1 = battle.getRounds().get(0);
        assertTrue(r1.getDeltaEnemyHealth() < 0);
        assertTrue(r1.getDeltaPlayerHealth() > 0);
        assertTrue(r1.getCurrentPlayerHealth() > battle.getInitialPlayerHealth());

        // Armour cant break
        assertTrue(player.getInventory().size() == 1);
    }


    @Test 
    public void testMultipleWeapons() {
        Position pos = new Position(-1, 1);
        
        ZombieToast zombie = new ZombieToast("1", pos, "zombie_toast", 5, 20);
        Player player = new Player("2", pos, "player", 5, 5);
        
        // Weapons 
        MidnightArmour armour = new MidnightArmour("3", pos, "midnight_armour", 3, 9);
        Bow bow = new Bow("3", pos, "bow", 1);
        Shield shield = new Shield("3", pos, "shield", 3, 3);
        Sword sword = new Sword("3", pos, "sword", 2, 3);

        // Player equips weapons
        player.getInvClass().pickup(armour, player);
        player.getInvClass().pickup(bow, player);
        player.getInvClass().pickup(shield, player);
        player.getInvClass().pickup(sword, player);

        Battle battle = new Battle(player, zombie);
        assertTrue(battle.getPlayerAttack() == 22);
        assertTrue(battle.getEnemyAttack() == 8);

        battle.combat();

        // Only Bow breaks
        assertTrue(player.getInventory().size() == 3);
        assertTrue(sword.getDurability() == 1);
        assertTrue(shield.getDurability() == 2);
    }

    @Test
    public void testWeaponsAreNotUsedWhenInvinc() {
        // Assumption: When the player is invincible even if they have weapons
        // they will not use them in battle

        Position pos = new Position(1, 3);
        
        Spider spider = new Spider("1", pos, "spider", 5, 5);
        Player player = new Player("2", pos, "player", 10, 10);
        Bow bow = new Bow("3", pos, "bow", 1);
        InvincibilityPotion potion = new InvincibilityPotion("4", pos, "invincibility_potion", 3);
        
        // Pick up Weapon 
        player.getInvClass().pickup(bow, player);

        // Add and use potion
        player.addPotionQueue(potion);
        player.setInvincible(true);

        Battle battle = new Battle(player, spider);
        battle.combat();

        assertTrue(player.getInventory().size() == 1);
    }
}
