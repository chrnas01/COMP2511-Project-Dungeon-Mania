package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.movingEntities.Player;
import dungeonmania.movingEntities.Spider;
import dungeonmania.movingEntities.ZombieToast;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MovingEntitiesTest {

    @Test
    public void testPlayerMoves() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("build_bow", "no_spider_spawning");

        EntityResponse player = TestUtils.getPlayer(res).get();
        Position old = player.getPosition();

        res = dmc.tick(Direction.UP);
        player = TestUtils.getPlayer(res).get();
        Position current = player.getPosition();
        assertNotEquals(old, current);

        res = dmc.tick(Direction.LEFT);
        player = TestUtils.getPlayer(res).get();
        current = player.getPosition();
        assertNotEquals(old, current);

        res = dmc.tick(Direction.DOWN);
        player = TestUtils.getPlayer(res).get();
        current = player.getPosition();
        assertNotEquals(old, current);

        res = dmc.tick(Direction.RIGHT);
        player = TestUtils.getPlayer(res).get();
        current = player.getPosition();
        assertEquals(old, current);
    }

    @Test
    public void testSpiderMovementSimple() {

        DungeonMap dungeon = new DungeonMap("0", "build_bow", 0, "no_spider_spawning");
        Player player = dungeon.getPlayer();
        Position start = player.getPosition().translateBy(Direction.UP);
        Spider spider = new Spider("abc", start, "spider", 10, 1);
        dungeon.addEntity(start, spider);

        spider.move(dungeon);
        assertEquals(start.translateBy(Direction.UP), spider.getPosition());
        spider.move(dungeon);
        assertEquals(start.translateBy(Direction.UP).translateBy(Direction.RIGHT), spider.getPosition());
        spider.move(dungeon);
        assertEquals(start.translateBy(Direction.RIGHT), spider.getPosition());
        spider.move(dungeon);
        spider.move(dungeon);
        assertEquals(start.translateBy(Direction.DOWN), spider.getPosition());
        spider.move(dungeon);
        spider.move(dungeon);
        assertEquals(start.translateBy(Direction.LEFT), spider.getPosition());
        spider.move(dungeon);
        spider.move(dungeon);
        assertEquals(start.translateBy(Direction.UP), spider.getPosition());
    }

    @Test
    public void testSpiderSpawnStuck() {

        DungeonMap dungeon = new DungeonMap("0", "bombs", 0, "no_spider_spawning");
        Player player = dungeon.getPlayer();
        Position start = player.getPosition().translateBy(Direction.RIGHT).translateBy(Direction.DOWN);
        Spider spider = new Spider("abc", start, "spider", 10, 1);
        dungeon.addEntity(start, spider);

        spider.move(dungeon);
        assertEquals(start, spider.getPosition());
        spider.move(dungeon);
        assertEquals(start, spider.getPosition());
    }

    @Test
    public void testSpiderPathStuck() {

        DungeonMap dungeon = new DungeonMap("0", "boulders", 0, "no_spider_spawning");
        Player player = dungeon.getPlayer();

        // Just setting up the boulder positions. Could be better by spawning one in.
        player.move(Direction.RIGHT, dungeon);
        player.move(Direction.RIGHT, dungeon);
        player.move(Direction.DOWN, dungeon);
        player.move(Direction.DOWN, dungeon);
        player.move(Direction.DOWN, dungeon);
        player.move(Direction.LEFT, dungeon);
        player.move(Direction.DOWN, dungeon);
        player.move(Direction.DOWN, dungeon);
        player.move(Direction.RIGHT, dungeon);
        player.move(Direction.UP, dungeon);
        player.move(Direction.UP, dungeon);
        player.move(Direction.RIGHT, dungeon);
        player.move(Direction.UP, dungeon);
        player.move(Direction.UP, dungeon);
        player.move(Direction.LEFT, dungeon);
        player.move(Direction.DOWN, dungeon);
        player.move(Direction.RIGHT, dungeon);
        player.move(Direction.DOWN, dungeon);
        player.move(Direction.LEFT, dungeon);
        player.move(Direction.DOWN, dungeon);
        player.move(Direction.DOWN, dungeon);
        player.move(Direction.RIGHT, dungeon);
        player.move(Direction.UP, dungeon);

        Position start = player.getPosition().translateBy(Direction.LEFT);
        Spider spider = new Spider("abc", start, "spider", 10, 1);
        dungeon.addEntity(start, spider);

        spider.move(dungeon);
        assertEquals(start.translateBy(Direction.UP), spider.getPosition());
        spider.move(dungeon);
        assertEquals(start.translateBy(Direction.UP), spider.getPosition());
    }

    @Test
    public void testSpiderMovementBoulder() {

        DungeonMap dungeon = new DungeonMap("0", "bombs", 0, "no_spider_spawning");
        Player player = dungeon.getPlayer();
        Position start = player.getPosition().translateBy(Direction.DOWN);
        Spider spider = new Spider("abc", start, "spider", 10, 1);
        dungeon.addEntity(start, spider);
        player.move(Direction.UP, dungeon);

        spider.move(dungeon);
        assertEquals(start.translateBy(Direction.UP), spider.getPosition());
        spider.move(dungeon);
        assertEquals(start.translateBy(Direction.UP).translateBy(Direction.LEFT), spider.getPosition());
        spider.move(dungeon);
        assertEquals(start.translateBy(Direction.LEFT), spider.getPosition());
        spider.move(dungeon);
        spider.move(dungeon);
        assertEquals(start.translateBy(Direction.DOWN), spider.getPosition());
    }

    @Test
    public void testSpiderPortal() {
        DungeonMap dungeon = new DungeonMap("0", "portals", 0, "no_spider_spawning");
        Player player = dungeon.getPlayer();
        Position start = player.getPosition().translateBy(Direction.DOWN);
        Spider spider = new Spider("abc", start, "spider", 10, 1);
        dungeon.addEntity(start, spider);
        player.move(Direction.UP, dungeon);

        spider.move(dungeon);
        spider.move(dungeon);
        assertEquals(start.translateBy(Direction.UP).translateBy(Direction.RIGHT), spider.getPosition());
    }

    @Test
    public void testZombieMoves() {
        DungeonMap dungeon = new DungeonMap("0", "bombs", 0, "no_spider_spawning");
        Player player = dungeon.getPlayer();
        Position start = player.getPosition().translateBy(Direction.DOWN).translateBy(Direction.LEFT);
        ZombieToast toast = new ZombieToast("abc", start, "zombie_toast", 10, 1);
        dungeon.addEntity(start, toast);

        toast.move(dungeon);
        assertNotEquals(start, toast.getPosition());
    }

    @Test
    public void testZombieRunsAway() {
        DungeonMap dungeon = new DungeonMap("0", "bombs", 0, "no_spider_spawning");
        Player player = dungeon.getPlayer();
        Position start = player.getPosition().translateBy(Direction.DOWN);
        ZombieToast toast = new ZombieToast("abc", start, "zombie_toast", 10, 1);
        dungeon.addEntity(start, toast);

        toast.moveAwayFromPlayer(dungeon);
        assertNotEquals(start.translateBy(Direction.DOWN), toast.getPosition());
    }

    @Test
    public void testMercenarySimpleMove() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("mercenary", "no_spider_spawning");

        EntityResponse merc = TestUtils.getEntities(res, "mercenary").get(0);
        Position old = merc.getPosition();

        res = dmc.tick(Direction.RIGHT);
        merc = TestUtils.getEntities(res, "mercenary").get(0);
        Position current = merc.getPosition();

        assertEquals(old.translateBy(Direction.LEFT), current);
    }

    @Test
    public void testMercenaryRunsAway() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("advanced", "no_spider_spawning");
        List<ItemResponse> inventory;

        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        inventory = res.getInventory();

        EntityResponse merc = TestUtils.getEntities(res, "mercenary").get(0);
        Position old = merc.getPosition();
        String potionid = inventory.get(0).getId();

        res = assertDoesNotThrow(() -> dmc.tick(potionid));
        merc = TestUtils.getEntities(res, "mercenary").get(0);
        Position current = merc.getPosition();

        assertEquals(old.translateBy(Direction.DOWN), current);
    }

    @Test
    public void testRangeTooFarBribe() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("mercenary", "no_spider_spawning");

        String merc_id = TestUtils.getEntities(res, "mercenary").get(0).getId();

        assertThrows(InvalidActionException.class, () -> dmc.interact(merc_id));
    }

    @Test
    public void testNotEnoughCoins() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("mercenary", "no_spider_spawning");

        String merc_id = TestUtils.getEntities(res, "mercenary").get(0).getId();

        dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        assertThrows(InvalidActionException.class, () -> dmc.interact(merc_id));
    }

    @Test
    public void testBribe() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("mercenary", "bribe_amount_3");

        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        String merc_id = TestUtils.getEntities(res, "mercenary").get(0).getId();

        assertDoesNotThrow(() -> dmc.interact(merc_id));

        res = dmc.tick(Direction.LEFT);
        List<ItemResponse> inventory;
        inventory = dmc.tick(Direction.DOWN).getInventory();

        assertEquals(0, inventory.size());
    }

    @Test
    public void testAllyMovement() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("mercenary", "bribe_amount_3");

        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        Position playerPos = TestUtils.getEntities(res, "player").get(0).getPosition();

        String merc_id = TestUtils.getEntities(res, "mercenary").get(0).getId();

        assertDoesNotThrow(() -> dmc.interact(merc_id));

        res = dmc.tick(Direction.LEFT);
        Position mercPos = TestUtils.getEntities(res, "mercenary").get(0).getPosition();
        assertEquals(mercPos, playerPos);
        playerPos = TestUtils.getEntities(res, "player").get(0).getPosition();

        res = dmc.tick(Direction.DOWN);
        mercPos = TestUtils.getEntities(res, "mercenary").get(0).getPosition();
        assertEquals(mercPos, playerPos);
    }
}
