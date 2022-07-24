package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getGoals;
import static dungeonmania.TestUtils.countEntityOfType;
import static dungeonmania.TestUtils.getValueFromConfigFile;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonMap.Config;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

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
    }
}
