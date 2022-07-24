package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getGoals;
import static dungeonmania.TestUtils.countEntityOfType;
import static dungeonmania.TestUtils.getValueFromConfigFile;

import java.util.List;

import org.junit.jupiter.api.Test;

import dungeonmania.collectableEntities.Wood;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class CollectableEntitiesTest {

    // @Test
    // public void testCollectableInvalidItem(){
    //     DungeonManiaController dmc = new DungeonManiaController();
    //     DungeonResponse initDungonRes = dmc.newGame("advanced", "simple");
    //     assertThrows(InvalidActionException.class, () -> dmc.tick("InvalidID"));
    // }

    // @Test
    // public void testCollectable(){
    //     DungeonManiaController dmc = new DungeonManiaController();
    //     DungeonResponse initDungonRes = dmc.newGame("advanced", "simple");
    //     List<ItemResponse> inventory;

    //     inventory = dmc.tick(Direction.RIGHT).getInventory();
    //     assertEquals(inventory.size(), 1);
    //     assertEquals(inventory.get(0).getType(), "invincibility_potion");
    // }

    // @Test
    // public void testCollectableUse(){
    //     DungeonManiaController dmc = new DungeonManiaController();
    //     DungeonResponse initDungonRes = dmc.newGame("advanced", "simple");
    //     List<ItemResponse> inventory;

    //     inventory = dmc.tick(Direction.RIGHT).getInventory();
    //     String potionid = inventory.get(0).getId();
    //     initDungonRes = assertDoesNotThrow(() -> dmc.tick(potionid));
    //     assertTrue(inventory.size() == 0);

    // }

    // @Test
    // public void testBuildable(){
    //     DungeonManiaController dmc = new DungeonManiaController();
    //     DungeonResponse initDungonRes = dmc.newGame("advanced", "simple");
    //     List<ItemResponse> inventory;

    //     dmc.tick(Direction.DOWN);
    //     dmc.tick(Direction.DOWN);
    //     dmc.tick(Direction.DOWN);
    //     dmc.tick(Direction.DOWN);
    //     dmc.tick(Direction.DOWN);
    //     dmc.tick(Direction.DOWN);
    //     dmc.tick(Direction.DOWN);
    //     dmc.tick(Direction.DOWN);
    //     dmc.tick(Direction.DOWN);
    //     dmc.tick(Direction.DOWN);
    //     dmc.tick(Direction.DOWN);
    //     dmc.tick(Direction.DOWN);
    //     dmc.tick(Direction.DOWN);
    //     dmc.tick(Direction.RIGHT);
    //     dmc.tick(Direction.RIGHT);
    //     dmc.tick(Direction.RIGHT);
    //     dmc.tick(Direction.RIGHT);
    //     dmc.tick(Direction.RIGHT);
    //     dmc.tick(Direction.RIGHT);
    //     dmc.tick(Direction.RIGHT);
    //     dmc.tick(Direction.RIGHT);
    //     dmc.tick(Direction.RIGHT);
    //     dmc.tick(Direction.RIGHT);
    //     dmc.tick(Direction.RIGHT);
    //     dmc.tick(Direction.RIGHT);
    //     dmc.tick(Direction.LEFT);
    //     dmc.tick(Direction.LEFT);
    //     dmc.tick(Direction.UP);
    //     dmc.tick(Direction.UP);
    //     dmc.tick(Direction.UP);

    //     initDungonRes = assertDoesNotThrow(() -> dmc.build("bow"));
    //     inventory = dmc.tick(Direction.DOWN).getInventory();
    //     assertEquals(inventory.get(3).getType(), "bow");
       
    // }
    @Test
    public void testBuildSceptreWoodKey() throws IllegalArgumentException, InvalidActionException {
        
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_buildTest_sceptre_woodkey", "c_base_config");
        // Test building with no materials first
        // assertThrows(InvalidActionException.class, () -> dmc.build("sceptre"));

        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        
        
        List<ItemResponse> inv = dmc.build("sceptre").getInventory();
        assertEquals(inv.get(0).getType(), "sceptre");
        
    }

    @Test
    public void testBuildSceptreArrowsTreasure() throws IllegalArgumentException, InvalidActionException {
        
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_buildTest_sceptre_arrowstreasure", "c_base_config");
        // Test building with no materials first
        // assertThrows(InvalidActionException.class, () -> dmc.build("sceptre"));

        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        
        List<ItemResponse> inv = dmc.build("sceptre").getInventory();
        assertEquals(inv.get(0).getType(), "sceptre");
        
    }

    @Test
    public void testBuildMidnightArmour() throws IllegalArgumentException, InvalidActionException {
        
        
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_buildTest_midnightarmour", "c_base_config");
        // Test building with no materials first
        // assertThrows(InvalidActionException.class, () -> dmc.build("sceptre"));


        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        
        List<ItemResponse> inv = dmc.build("midnight_armour").getInventory();
        assertEquals(inv.get(0).getType(), "midnight_armour");
        
    }

}
