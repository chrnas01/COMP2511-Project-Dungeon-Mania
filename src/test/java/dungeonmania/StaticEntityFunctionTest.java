package dungeonmania;

import dungeonmania.entity.Entity;
import dungeonmania.entity.staticentity.Boulder;
import dungeonmania.entity.staticentity.Door;
import dungeonmania.entity.staticentity.FloorSwitch;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.Test;
import spark.utils.Assert;

public class StaticEntityFunctionTest {

    @Test
    public void testDoorByErrorKey() {
        Door door = new Door(new Position(1, 1), 5);
        Assert.isTrue(!door.isOpen(), "Door key error.");
        Assert.isTrue(!door.isOpenByKey(4), "Door key error.");
        Assert.isTrue(!door.isOpen(), "Door key error.");
    }

    @Test
    public void testDoorByRightKey() {
        Door door = new Door(new Position(1, 1), 5);
        Assert.isTrue(!door.isOpen(), "Door key error.");
        Assert.isTrue(door.isOpenByKey(5), "Door key error.");
        Assert.isTrue(door.isOpen(), "Door key error.");
    }


    @Test
    public void testBoulderMove() {
        Boulder boulder = new Boulder(new Position(1, 1));
        boulder.moveByDirection(Direction.DOWN);
        Assert.isTrue(boulder.getPosition().equals(new Position(1,2)), "Move error.");
        boulder.moveByDirection(Direction.UP);
        Assert.isTrue(boulder.getPosition().equals(new Position(1,1)), "Move error.");
        boulder.moveByDirection(Direction.RIGHT);
        Assert.isTrue(boulder.getPosition().equals(new Position(2,1)), "Move error.");
        boulder.moveByDirection(Direction.LEFT);
        Assert.isTrue(boulder.getPosition().equals(new Position(1,1)), "Move error.");
    }


    @Test
    public void testFloorSwitchMove() {
        FloorSwitch floorSwitch = new FloorSwitch(new Position(1, 1));
        Assert.isTrue(floorSwitch.isTouchByPosition(new Position(1,1)), "Touch error.");
        Assert.isTrue(!floorSwitch.isTouchByPosition(new Position(1,2)), "Touch error.");
    }
}
