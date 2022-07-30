package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.movingEntities.ZombieToast;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.staticEntities.ZombieToastSpawner;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class StaticEntitiesTest {

    @Test
    public void testWallBlock() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initRes = dmc.newGame("2_doors", "no_spider_spawning");

        EntityResponse player = TestUtils.getPlayer(initRes).get();
        Position old = player.getPosition();

        initRes = dmc.tick(Direction.UP);
        player = TestUtils.getPlayer(initRes).get();
        Position current = player.getPosition();

        assertEquals(old, current);
    }

    @Test
    public void testClosedDoor() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("2_doors", "no_spider_spawning");

        dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        EntityResponse player = TestUtils.getPlayer(res).get();
        Position old = player.getPosition();

        res = dmc.tick(Direction.RIGHT);
        player = TestUtils.getPlayer(res).get();
        Position current = player.getPosition();

        assertEquals(old, current);
    }

    @Test
    public void testOpenDoor() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("2_doors", "no_spider_spawning");

        dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        EntityResponse player = TestUtils.getPlayer(res).get();
        Position old = player.getPosition();

        res = dmc.tick(Direction.RIGHT);
        player = TestUtils.getPlayer(res).get();
        Position current = player.getPosition();

        assertNotEquals(old, current);

        res = dmc.tick(Direction.UP);
        player = TestUtils.getPlayer(res).get();
        old = player.getPosition();
        res = dmc.tick(Direction.DOWN);
        player = TestUtils.getPlayer(res).get();
        current = player.getPosition();

        assertNotEquals(old, current);
    }

    @Test
    public void testCanPushBoulder() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("boulders", "no_spider_spawning");

        EntityResponse player = TestUtils.getPlayer(res).get();
        Position old = player.getPosition();

        res = dmc.tick(Direction.RIGHT);
        player = TestUtils.getPlayer(res).get();
        Position current = player.getPosition();

        assertNotEquals(old, current);
    }

    @Test
    public void testCannotPushTwoBoulder() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("boulders", "no_spider_spawning");
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.DOWN);
        EntityResponse player = TestUtils.getPlayer(res).get();
        Position old = player.getPosition();

        res = dmc.tick(Direction.RIGHT);
        player = TestUtils.getPlayer(res).get();
        Position current = player.getPosition();

        assertEquals(old, current);
    }

    @Test
    public void testCannotPushBoulderWall() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("boulders", "no_spider_spawning");
        dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        EntityResponse player = TestUtils.getPlayer(res).get();
        Position old = player.getPosition();

        res = dmc.tick(Direction.RIGHT);
        player = TestUtils.getPlayer(res).get();
        Position current = player.getPosition();

        assertEquals(old, current);
    }

    @Test
    public void testCannotPushBoulderClosedDoorOrSpider() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("boulder_door", "no_spider_spawning");
        EntityResponse player = TestUtils.getPlayer(res).get();
        Position old = player.getPosition();

        res = dmc.tick(Direction.RIGHT);
        player = TestUtils.getPlayer(res).get();
        Position current = player.getPosition();
        assertEquals(old, current);

        dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        player = TestUtils.getPlayer(res).get();
        old = player.getPosition();

        res = dmc.tick(Direction.DOWN);
        player = TestUtils.getPlayer(res).get();
        current = player.getPosition();
        assertEquals(old, current);
    }

    @Test
    public void testCanPushBoulderOntoFloorSwitch() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("bombs", "no_spider_spawning");

        EntityResponse player = TestUtils.getPlayer(res).get();
        Position old = player.getPosition();

        res = dmc.tick(Direction.RIGHT);
        player = TestUtils.getPlayer(res).get();
        Position current = player.getPosition();

        assertNotEquals(old, current);
    }

    @Test
    public void testPortalMove() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("portals", "no_spider_spawning");

        EntityResponse player = TestUtils.getPlayer(res).get();
        Position start = player.getPosition();
        Position wrong = start.translateBy(Direction.RIGHT);

        res = dmc.tick(Direction.RIGHT);
        player = TestUtils.getPlayer(res).get();
        Position current = player.getPosition();

        assertNotEquals(wrong, current);
    }

    @Test
    public void testMulitplePortal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("portals_advanced", "no_spider_spawning");

        EntityResponse player = TestUtils.getPlayer(res).get();
        Position start = player.getPosition();
        Position expected = start.translateBy(Direction.RIGHT).translateBy(Direction.RIGHT);
        expected = expected.translateBy(Direction.RIGHT).translateBy(Direction.DOWN);

        res = dmc.tick(Direction.RIGHT);
        player = TestUtils.getPlayer(res).get();
        Position current = player.getPosition();

        assertEquals(expected, current);
    }

    @Test
    public void testPortalBlockedWall() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("portal_blocked", "M3_config");

        EntityResponse player = TestUtils.getPlayer(res).get();
        Position start = player.getPosition();

        res = dmc.tick(Direction.RIGHT);
        player = TestUtils.getPlayer(res).get();
        Position current = player.getPosition();

        assertEquals(current, start);
    }

    @Test
    public void testPortalBoulderAtExit() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("portal_blocked", "M3_config");

        res = dmc.tick(Direction.LEFT);
        EntityResponse player = TestUtils.getPlayer(res).get();
        Position start = player.getPosition();

        res = dmc.tick(Direction.LEFT);
        player = TestUtils.getPlayer(res).get();
        Position current = player.getPosition();

        assertNotEquals(current, start);
    }

    @Test
    public void testPortalBouldersBlock() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("portal_blocked", "M3_config");

        dmc.tick(Direction.LEFT);
        dmc.tick(Direction.UP);
        res = dmc.tick(Direction.LEFT);
        EntityResponse player = TestUtils.getPlayer(res).get();
        Position start = player.getPosition();

        res = dmc.tick(Direction.DOWN);
        player = TestUtils.getPlayer(res).get();
        Position current = player.getPosition();

        assertEquals(current, start);
    }

    @Test
    public void testPortalDoorBlock() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("portal_blocked", "M3_config");

        dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        EntityResponse player = TestUtils.getPlayer(res).get();
        Position start = player.getPosition();

        res = dmc.tick(Direction.DOWN);
        player = TestUtils.getPlayer(res).get();
        Position current = player.getPosition();

        assertEquals(current, start);
    }

    @Test
    public void testPortalDoorKey() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("portal_blocked", "M3_config");

        dmc.tick(Direction.LEFT);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        EntityResponse player = TestUtils.getPlayer(res).get();
        Position start = player.getPosition();

        res = dmc.tick(Direction.DOWN);
        player = TestUtils.getPlayer(res).get();
        Position current = player.getPosition();

        assertNotEquals(current, start);
    }

    @Test
    public void testPortalDoorSun() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("portal_blocked", "M3_config");

        dmc.tick(Direction.UP);
        dmc.tick(Direction.UP);
        dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        EntityResponse player = TestUtils.getPlayer(res).get();
        Position start = player.getPosition();

        res = dmc.tick(Direction.DOWN);
        player = TestUtils.getPlayer(res).get();
        Position current = player.getPosition();

        assertNotEquals(current, start);

        dmc.tick(Direction.UP);
        res = dmc.tick(Direction.DOWN);
        player = TestUtils.getPlayer(res).get();
        start = player.getPosition();
        assertEquals(current, start);
    }

    @Test
    public void testIllegalArgumentInteract() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("zombies", "no_zombie_spawning");
        assertThrows(IllegalArgumentException.class, () -> dmc.interact("ILLEGALARGUMENT"));

        EntityResponse player = TestUtils.getPlayer(res).get();
        String player_id = player.getId();
        assertThrows(IllegalArgumentException.class, () -> dmc.interact(player_id));

    }

    @Test
    public void testTooFarFromSpawner() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("zombies", "no_zombie_spawning");
        res = dmc.tick(Direction.LEFT);

        List<EntityResponse> spawners = TestUtils.getEntities(res, "zombie_toast_spawner");
        String spawner_id = spawners.get(0).getId();

        assertThrows(InvalidActionException.class, () -> dmc.interact(spawner_id));
    }

    @Test
    public void testNoWeaponSpawner() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("zombies", "no_zombie_spawning");

        List<EntityResponse> spawners = TestUtils.getEntities(res, "zombie_toast_spawner");
        String spawner_id = spawners.get(0).getId();

        assertThrows(InvalidActionException.class, () -> dmc.interact(spawner_id));
    }

    @Test
    public void testZombieSpawnerDestroy() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("zombies", "no_zombie_spawning");

        dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.UP);

        List<EntityResponse> spawners = TestUtils.getEntities(res, "zombie_toast_spawner");
        String spawner_id = spawners.get(0).getId();

        res = assertDoesNotThrow(() -> dmc.interact(spawner_id));

        spawners = TestUtils.getEntities(res, "zombie_toast_spawner");
        assertEquals(0, spawners.size());
    }

    @Test
    public void testZombieSpawner() {
        DungeonMap dungeon = new DungeonMap("1", "advanced_zombie", 1, "c_base_config");

        Entity spawner = dungeon.getMap().get(new Position(1, 14)).get(0);

        ((ZombieToastSpawner) spawner).generateZombieToast(dungeon, 10);
        ((ZombieToastSpawner) spawner).generateZombieToast(dungeon, 20);
        ((ZombieToastSpawner) spawner).generateZombieToast(dungeon, 30);
        ((ZombieToastSpawner) spawner).generateZombieToast(dungeon, 40);
        ((ZombieToastSpawner) spawner).generateZombieToast(dungeon, 50);
        ((ZombieToastSpawner) spawner).generateZombieToast(dungeon, 51);

        List<Position> cAdjacent = ((ZombieToastSpawner) spawner).getCardinallyAdjacentSquares();

        assertTrue(dungeon.getMap().get(cAdjacent.get(0)).get(0) instanceof ZombieToast
                && dungeon.getMap().get(cAdjacent.get(2)).size() == 1);
        assertTrue(dungeon.getMap().get(cAdjacent.get(1)).get(0) instanceof ZombieToast);
        assertFalse(dungeon.getMap().get(cAdjacent.get(2)).get(0) instanceof ZombieToast
                && dungeon.getMap().get(cAdjacent.get(2)).size() > 1);
        assertFalse(dungeon.getMap().get(cAdjacent.get(2)).get(0) instanceof ZombieToast
                && dungeon.getMap().get(cAdjacent.get(2)).size() > 1);
    }
}
