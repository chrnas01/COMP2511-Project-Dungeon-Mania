package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
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
}