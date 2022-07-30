package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class CollectableEntitiesTest {

    @Test
    public void testInvalidUse() {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("advanced", "simple");
        assertThrows(InvalidActionException.class, () -> dmc.tick("InvalidID"));
    }

    @Test
    public void testIllegalUse() {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("2_doors", "no_spider_spawning");
        List<ItemResponse> inventory;

        inventory = dmc.tick(Direction.DOWN).getInventory();
        assertEquals(inventory.size(), 1);
        String key_id = inventory.get(0).getId();
        assertThrows(IllegalArgumentException.class, () -> dmc.tick(key_id));
    }

    @Test
    public void testIllegalBuild() {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("2_doors", "no_spider_spawning");

        assertThrows(IllegalArgumentException.class, () -> dmc.build("IllegalBuild"));
    }

    @Test
    public void testInvalidBuild() {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("2_doors", "no_spider_spawning");

        assertThrows(InvalidActionException.class, () -> dmc.build("bow"));
        assertThrows(InvalidActionException.class, () -> dmc.build("shield"));

        assertThrows(InvalidActionException.class, () -> dmc.build("sceptre"));
        assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));
    }

    @Test
    public void testKeyOpensDoor() {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("2_doors", "no_spider_spawning");
        List<ItemResponse> inventory;

        inventory = dmc.tick(Direction.DOWN).getInventory();
        assertEquals(inventory.size(), 1);
        dmc.tick(Direction.RIGHT);
        inventory = dmc.tick(Direction.RIGHT).getInventory();
        assertEquals(inventory.size(), 0);
    }

    @Test
    public void testBuildBow() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("build_bow", "no_spider_spawning");
        List<ItemResponse> inventory;

        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        inventory = dmc.tick(Direction.RIGHT).getInventory();
        assertEquals(inventory.size(), 4);
        assertEquals(inventory.get(0).getType(), "wood");
        assertEquals(inventory.get(1).getType(), "arrow");

        initDungonRes = assertDoesNotThrow(() -> dmc.build("bow"));
        inventory = initDungonRes.getInventory();
        assertEquals(inventory.size(), 1);
        assertEquals(inventory.get(0).getType(), "bow");
    }

    @Test
    public void testBuildShield() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("build_shield", "no_spider_spawning");
        List<ItemResponse> inventory;

        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        inventory = dmc.tick(Direction.RIGHT).getInventory();
        assertEquals(inventory.size(), 3);
        assertEquals(inventory.get(0).getType(), "wood");
        assertEquals(inventory.get(2).getType(), "key");

        initDungonRes = assertDoesNotThrow(() -> dmc.build("shield"));
        inventory = initDungonRes.getInventory();
        assertEquals(inventory.size(), 1);
        assertEquals(inventory.get(0).getType(), "shield");
    }

    @Test
    public void testBuildShieldSun() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("sun_shield_test", "no_spider_spawning");
        List<ItemResponse> inventory;

        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        inventory = dmc.tick(Direction.RIGHT).getInventory();
        assertEquals(inventory.size(), 3);
        assertEquals(inventory.get(0).getType(), "wood");
        assertEquals(inventory.get(2).getType(), "sun_stone");

        initDungonRes = assertDoesNotThrow(() -> dmc.build("shield"));
        inventory = initDungonRes.getInventory();
        assertEquals(inventory.size(), 2);
        assertEquals(inventory.get(1).getType(), "shield");
    }

    @Test
    public void testPotionUse() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse dungeonRes = dmc.newGame("advanced", "no_spider_spawning");
        List<ItemResponse> inventory;

        inventory = dmc.tick(Direction.RIGHT).getInventory();
        String potionid = inventory.get(0).getId();
        dungeonRes = assertDoesNotThrow(() -> dmc.tick(potionid));
        inventory = dungeonRes.getInventory();
        assertTrue(inventory.size() == 0);
        inventory = dmc.tick(Direction.RIGHT).getInventory();
        assertTrue(inventory.size() == 1);

        String secondid = inventory.get(0).getId();
        dungeonRes = assertDoesNotThrow(() -> dmc.tick(secondid));
        inventory = dungeonRes.getInventory();
        assertTrue(inventory.size() == 0);
    }

    @Test
    public void testPotionQueues() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse dungeonRes = dmc.newGame("advanced", "no_spider_spawning");
        List<ItemResponse> inventory;

        dmc.tick(Direction.RIGHT);
        inventory = dmc.tick(Direction.RIGHT).getInventory();
        String potionid = inventory.get(0).getId();
        String secondid = inventory.get(1).getId();
        dungeonRes = assertDoesNotThrow(() -> dmc.tick(potionid));
        inventory = dungeonRes.getInventory();
        assertTrue(inventory.size() == 1);
        dungeonRes = assertDoesNotThrow(() -> dmc.tick(secondid));
        inventory = dungeonRes.getInventory();
        assertTrue(inventory.size() == 0);
    }

    @Test
    public void testPlaceBomb() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse dungeonRes = dmc.newGame("bombs", "no_spider_spawning");
        List<ItemResponse> inventory;

        dmc.tick(Direction.DOWN);
        inventory = dmc.tick(Direction.RIGHT).getInventory();
        assertTrue(inventory.size() == 1);
        dmc.tick(Direction.RIGHT);

        String bombId = inventory.get(0).getId();
        dungeonRes = assertDoesNotThrow(() -> dmc.tick(bombId));
        inventory = dungeonRes.getInventory();
        assertTrue(inventory.size() == 0);

        inventory = dmc.tick(Direction.RIGHT).getInventory();
        assertTrue(inventory.size() == 1);
    }

    @Test
    public void testBombExplodes() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse dungeonRes = dmc.newGame("bombs", "no_spider_spawning");
        List<ItemResponse> inventory;

        dmc.tick(Direction.RIGHT);
        inventory = dmc.tick(Direction.DOWN).getInventory();
        assertTrue(inventory.size() == 1);
        dmc.tick(Direction.RIGHT);

        String bombId = inventory.get(0).getId();
        dungeonRes = assertDoesNotThrow(() -> dmc.tick(bombId));
        inventory = dungeonRes.getInventory();
        assertTrue(inventory.size() == 0);

        inventory = dmc.tick(Direction.RIGHT).getInventory();
        assertTrue(inventory.size() == 0);
    }

    @Test
    public void testBuildSceptreWoodKey() {

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_buildTest_sceptre_woodkey", "M3_config");
        List<ItemResponse> inventory;

        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        inventory = res.getInventory();
        assertEquals(3, inventory.size());

        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        inventory = res.getInventory();
        assertEquals(inventory.size(), 1);
        assertEquals(inventory.get(0).getType(), "sceptre");
    }

    @Test
    public void testBuildSceptreArrowsTreasure() {

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_buildTest_sceptre_arrowstreasure", "M3_config");
        List<ItemResponse> inventory;

        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        inventory = res.getInventory();
        assertEquals(4, inventory.size());

        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        inventory = res.getInventory();
        assertEquals(inventory.size(), 1);
        assertEquals(inventory.get(0).getType(), "sceptre");

    }

    @Test
    public void testBuildMidnightArmour() {

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_buildTest_midnightarmour", "M3_config");
        List<ItemResponse> inventory;

        dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        inventory = res.getInventory();
        assertEquals(2, inventory.size());

        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        inventory = res.getInventory();
        assertEquals(inventory.size(), 1);
        assertEquals(inventory.get(0).getType(), "midnight_armour");
    }

    @Test
    public void testSunStoneOpensDoors() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("sun_door_test", "M3_config");
        List<ItemResponse> inventory;

        res = dmc.tick(Direction.RIGHT);
        inventory = res.getInventory();
        assertEquals(1, inventory.size());

        res = dmc.tick(Direction.DOWN);
        Position playerPos = TestUtils.getEntities(res, "player").get(0).getPosition();

        res = dmc.tick(Direction.DOWN);
        Position playerNewPos = TestUtils.getEntities(res, "player").get(0).getPosition();
        assertNotEquals(playerNewPos, playerPos);

        inventory = res.getInventory();
        assertEquals(inventory.size(), 1);
    }
}
