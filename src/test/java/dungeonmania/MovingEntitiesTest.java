package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.movingEntities.Mercenary;
import dungeonmania.movingEntities.Player;
import dungeonmania.movingEntities.Spider;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MovingEntitiesTest {

    public boolean isEntityOnTile(DungeonResponse response, Position pos, String id) {
        for (EntityResponse entity : response.getEntities()) {
            if (entity.getId() == id) {
                return entity.getPosition().equals(pos);
            }
        }
        return false;
    }

    //     Player.move() is a void function hence it doesnt return anything and can't be assigned - this test needs to be refactored
//     @Test
//     public void testBasicPlayerMovements() {
//         Player player = new Player("test", new Position(1,1), "player", 10, 1);
//         // Moving the player:
//         response = player.move(Direction.RIGHT, advanced);
//         assertTrue(isEntityOnTile(response, new Position(2, 1), playerId));
//
//         response = controller.tick(null, Direction.RIGHT);
//         assertTrue(isEntityOnTile(response, new Position(3, 1), playerId));
//
//         response = controller.tick(null, Direction.DOWN);
//         assertTrue(isEntityOnTile(response, new Position(3, 2), playerId));
//
//         response = controller.tick(null, Direction.DOWN);
//         assertTrue(isEntityOnTile(response, new Position(3, 3), playerId));
//
//         response = controller.tick(null, Direction.LEFT);
//         assertTrue(isEntityOnTile(response, new Position(2, 3), playerId));
//
//         response = controller.tick(null, Direction.LEFT);
//         assertTrue(isEntityOnTile(response, new Position(1, 3), playerId));
//
//         response = controller.tick(null, Direction.UP);
//         assertTrue(isEntityOnTile(response, new Position(1, 2), playerId));
//
//         // Back to where I started:
//         response = controller.tick(null, Direction.UP);
//         assertTrue(isEntityOnTile(response, new Position(1, 1), playerId));
//     }
    @Test
    public void testBasicPlayerMovements2(){
        DungeonMap dungeon = new DungeonMap("0", "boulders", 0, "bribe_amount_3");
        Map<Position, List<Entity>> map = dungeon.getMap();
        List<Entity> entities2=map.get(new Position(2,0));
        for (Position key : map.keySet()) {
            List<Entity> entities = dungeon.getMap().get(key);

        }

        dungeon.addPosition(new Position(1,1));

    }
    @Test
    public void testMercenary(){
        DungeonMap dungeon = new DungeonMap("0", "mercenary", 0, "bribe_amount_3");
        Map<Position, List<Entity>> map = dungeon.getMap();
        List<Entity> entities=map.get(new Position(8,1));
        List<Entity> playerEntities=map.get(new Position(1,1));
        Mercenary mercenary=null;
        Player player=null;
        for (Entity entity : entities) {
            if(entity instanceof Mercenary){
                mercenary=(Mercenary) entity;
            }

        }
        for (Entity entity : playerEntities) {
            if(entity instanceof Player){
                player=(Player) entity;
            }

        }
        mercenary.move(dungeon);
        assertTrue(mercenary.getPosition().equals(new Position(7,1)));
        mercenary.move(dungeon);
        assertTrue(mercenary.getPosition().equals(new Position(6,1)));
        mercenary.move(dungeon);
        assertTrue(mercenary.getPosition().equals(new Position(5,1)));
        mercenary.move(dungeon);
        mercenary.move(dungeon);
        mercenary.move(dungeon);
        mercenary.move(dungeon);
        assertTrue(mercenary.getPosition().equals(new Position(2,1)));
        player.move(Direction.UP,dungeon);
        mercenary.move(dungeon);
        assertTrue(mercenary.getPosition().equals(new Position(2,0)));

    }
    @Test
    public void testZombie (){
        DungeonMap dungeon = new DungeonMap("0", "zombies", 0, "bribe_amount_3");
        Map<Position, List<Entity>> map = dungeon.getMap();
        List<Entity> entities=map.get(new Position(8,1));
        List<Entity> playerEntities=map.get(new Position(1,1));


    }
//    @Test
//    public void testSpider (){
//        DungeonMap dungeon = new DungeonMap("0", "zombies", 0, "bribe_amount_3");
//        Map<Position, List<Entity>> map = dungeon.getMap();
//        Spider spider=null;
//        for(Position position:map.keySet()){
//            List<Entity> entities=map.get(position);
//            for(Entity entity:entities){
//                if(entity instanceof Spider){
//                    spider= (Spider) entity;
//                }
//            }
//
//        }
//        spider.move(dungeon);
//        spider.move(dungeon);
//        spider.move(dungeon);
//        spider.move(dungeon);
//        spider.move(dungeon);
//        spider.move(dungeon);
//        spider.move(dungeon);
//        spider.move(dungeon);
//        spider.move(dungeon);
//        spider.move(dungeon);
//        spider.move(dungeon);
//
//
//    }
}