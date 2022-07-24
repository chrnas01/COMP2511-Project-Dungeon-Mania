package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.movingEntities.Player;
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

    // Player.move() is a void function hence it doesnt return anything and can't be assigned - this test needs to be refactored
//     @Test
//     public void testBasicPlayerMovements() {
//         Player player = new Player("test", new Position(1,1), "player", 10, 1);
//         // Moving the player:
//         response = player.move(Direction.RIGHT, advanced);
//         assertTrue(isEntityOnTile(response, new Position(2, 1), playerId));

//         response = controller.tick(null, Direction.RIGHT);
//         assertTrue(isEntityOnTile(response, new Position(3, 1), playerId));
        
//         response = controller.tick(null, Direction.DOWN);
//         assertTrue(isEntityOnTile(response, new Position(3, 2), playerId));

//         response = controller.tick(null, Direction.DOWN);
//         assertTrue(isEntityOnTile(response, new Position(3, 3), playerId));

//         response = controller.tick(null, Direction.LEFT);
//         assertTrue(isEntityOnTile(response, new Position(2, 3), playerId));

//         response = controller.tick(null, Direction.LEFT);
//         assertTrue(isEntityOnTile(response, new Position(1, 3), playerId));

//         response = controller.tick(null, Direction.UP);
//         assertTrue(isEntityOnTile(response, new Position(1, 2), playerId));

//         // Back to where I started:
//         response = controller.tick(null, Direction.UP);
//         assertTrue(isEntityOnTile(response, new Position(1, 1), playerId));
//     }
    // @Test
    // @DisplayName("Test basic battle calculations - mercenary - player loses")
    // public void testHealthBelowZeroMercenary() {
    //    DungeonManiaController controller = new DungeonManiaController();
    //    DungeonResponse postBattleResponse = genericMercenarySequence(controller, "c_battleTests_basicMercenaryPlayerDies");
    //    BattleResponse battle = postBattleResponse.getBattles().get(0);
    //    assertBattleCalculations("mercenary", battle, false, "c_battleTests_basicMercenaryPlayerDies");
    // }
}