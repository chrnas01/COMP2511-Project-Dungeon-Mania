package dungeonmania;


import dungeonmania.entity.Entity;
import dungeonmania.entity.staticentity.*;
import dungeonmania.util.Position;
import org.junit.jupiter.api.Test;
import spark.utils.Assert;

import javax.sound.sampled.Port;


public class StaticEntityInitTest {

    @Test
    public void testWall() {
        Entity entity = new Wall(new Position(1, 1));
        Assert.isTrue(new Position(1,1).equals(entity.getPosition()),"Position change");
        Assert.isTrue(entity instanceof Wall,"Type error.");
        Assert.isTrue(!entity.isInteractable(),"Init error.");
    }

    @Test
    public void testDoor() {
        Entity entity = new Door(new Position(1, 1),5);
        Assert.isTrue(new Position(1,1).equals(entity.getPosition()),"Position change");
        Assert.isTrue(entity instanceof Door,"Type error.");
    }

    @Test
    public void testExit() {
        Entity entity = new Exit(new Position(1, 1));
        Assert.isTrue(new Position(1,1).equals(entity.getPosition()),"Position change");
        Assert.isTrue(entity instanceof Exit,"Type error.");
    }

    @Test
    public void testFloorSwitch() {
        Entity entity = new FloorSwitch(new Position(1, 1));
        Assert.isTrue(new Position(1,1).equals(entity.getPosition()),"Position change");
        Assert.isTrue(entity instanceof FloorSwitch,"Type error.");
    }

    @Test
    public void testPortal() {
        Entity entity = new Portal(new Position(1, 1),"blue");
        Assert.isTrue(new Position(1,1).equals(entity.getPosition()),"Position change");
        Assert.isTrue(entity instanceof Portal,"Type error.");
        Assert.isTrue(((Portal)entity).getColour().equals("blue"),"Colour error.");
    }

    @Test
    public void testBoulder() {
        Entity entity = new Boulder(new Position(1, 1));
        Assert.isTrue(new Position(1,1).equals(entity.getPosition()),"Position change");
        Assert.isTrue(entity instanceof Boulder,"Type error.");
    }

    @Test
    public void testZombieToastSpawner() {
        Entity entity = new ZombieToastSpawner(new Position(1, 1));
        Assert.isTrue(new Position(1,1).equals(entity.getPosition()),"Position change");
        Assert.isTrue(entity instanceof ZombieToastSpawner,"Type error.");
    }
}
