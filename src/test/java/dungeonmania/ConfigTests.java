package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonMap.Config;

public class ConfigTests {
    @Test
    @DisplayName("Tests Config class works as intended")
    public void testConfig() {
        Config config = new Config(1, "c_battleTests_basicMercenaryMercenaryDies");

        assertEquals(3, config.ALLY_ATTACK);
        assertEquals(3, config.ALLY_DEFENCE);
        assertEquals(1, config.BOMB_RADIUS);
        assertEquals(1, config.BOW_DURABILITY);
        assertEquals(1, config.BRIBE_AMOUNT);
        assertEquals(1, config.BRIBE_RADIUS);
        assertEquals(1, config.ENEMY_GOAL);
        assertEquals(1, config.INVINCIBILITY_POTION_DURATION);
        assertEquals(1, config.INVISIBILITY_POTION_DURATION);
        assertEquals(5, config.MERCENARY_ATTACK);
        assertEquals(5, config.MERCENARY_HEALTH);
        assertEquals(10, config.PLAYER_ATTACK);
        assertEquals(10, config.PLAYER_HEALTH);
        assertEquals(1, config.SHIELD_DEFENCE);
        assertEquals(1, config.SHIELD_DURABILITY);
        assertEquals(5, config.SPIDER_ATTACK);
        assertEquals(5, config.SPIDER_HEALTH);
        assertEquals(0, config.SPIDER_SPAWN_RATE);
        assertEquals(2, config.SWORD_ATTACK);
        assertEquals(1, config.SWORD_DURABILITY);
        assertEquals(1, config.TREASURE_GOAL);
        assertEquals(5, config.ZOMBIE_ATTACK);
        assertEquals(5, config.ZOMBIE_HEALTH);
        assertEquals(0, config.ZOMBIE_SPAWN_RATE);

        assertEquals(10, config.ASSASSIN_ATTACK);
        assertEquals(1, config.ASSASSIN_BRIBE_AMOUNT);
        assertEquals(0.3, config.ASSASSIN_BRIBE_FAIL_RATE);
        assertEquals(10, config.ASSASSIN_HEALTH);
        assertEquals(5, config.ASSASSIN_RECON_RADIUS);
        assertEquals(10, config.HYDRA_ATTACK);
        assertEquals(10, config.HYDRA_HEALTH);
        assertEquals(1, config.HYDRA_HEALTH_INCREASE_AMOUNT);
        assertEquals(5, config.MIND_CONTROL_DURATION);
        assertEquals(2, config.MIDNIGHT_ARMOUR_ATTACK);
        assertEquals(2, config.MIDNIGHT_ARMOUR_DEFENCE);
    }

    @Test
    @DisplayName("Tests default values work as inteded")
    public void testDefaultConfig() {
        Config config = new Config(1, "blank");

        assertEquals(3, config.ALLY_ATTACK);
        assertEquals(3, config.ALLY_DEFENCE);
        assertEquals(3, config.BOMB_RADIUS);
        assertEquals(3, config.BOW_DURABILITY);
        assertEquals(1, config.BRIBE_AMOUNT);
        assertEquals(1, config.BRIBE_RADIUS);
        assertEquals(2, config.ENEMY_GOAL);
        assertEquals(5, config.INVINCIBILITY_POTION_DURATION);
        assertEquals(5, config.INVISIBILITY_POTION_DURATION);
        assertEquals(5, config.MERCENARY_ATTACK);
        assertEquals(5, config.MERCENARY_HEALTH);
        assertEquals(5, config.PLAYER_ATTACK);
        assertEquals(10, config.PLAYER_HEALTH);
        assertEquals(3, config.SHIELD_DEFENCE);
        assertEquals(3, config.SHIELD_DURABILITY);
        assertEquals(5, config.SPIDER_ATTACK);
        assertEquals(5, config.SPIDER_HEALTH);
        assertEquals(8, config.SPIDER_SPAWN_RATE);
        assertEquals(2, config.SWORD_ATTACK);
        assertEquals(3, config.SWORD_DURABILITY);
        assertEquals(1, config.TREASURE_GOAL);
        assertEquals(5, config.ZOMBIE_ATTACK);
        assertEquals(5, config.ZOMBIE_HEALTH);
        assertEquals(5, config.ZOMBIE_SPAWN_RATE);

        assertEquals(10, config.ASSASSIN_ATTACK);
        assertEquals(1, config.ASSASSIN_BRIBE_AMOUNT);
        assertEquals(0.3, config.ASSASSIN_BRIBE_FAIL_RATE);
        assertEquals(10, config.ASSASSIN_HEALTH);
        assertEquals(5, config.ASSASSIN_RECON_RADIUS);
        assertEquals(10, config.HYDRA_ATTACK);
        assertEquals(10, config.HYDRA_HEALTH);
        assertEquals(1, config.HYDRA_HEALTH_INCREASE_AMOUNT);
        assertEquals(5, config.MIND_CONTROL_DURATION);
        assertEquals(2, config.MIDNIGHT_ARMOUR_ATTACK);
        assertEquals(2, config.MIDNIGHT_ARMOUR_DEFENCE);
    }

}
