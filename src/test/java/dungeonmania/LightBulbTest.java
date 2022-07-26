package dungeonmania;

import dungeonmania.staticEntities.LightBulb;
import dungeonmania.staticEntities.Wire;
import dungeonmania.util.Position;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LightBulbTest {

    @Test
    public void testLightBulbNormal() {
        LightBulb lightBulb = new LightBulb("100",new Position(5, 5),"LightBulb");
        assertEquals(new Position(5, 5), lightBulb.getPosition());
        assertEquals(false, lightBulb.isLight(new HashMap<>()));
    }

    @Test
    public void testLightBulbLight() {
        LightBulb lightBulb = new LightBulb("100",new Position(5, 5),"LightBulb");
        Map<Position, List<Entity>> map = new HashMap<>();
//        map.put(new Position(5,4),new SwitchDoor(new Position(5,4)));
        assertEquals(new Position(5, 5), lightBulb.getPosition());
        assertEquals(true, lightBulb.isLight(new HashMap<>()));
    }


    @Test
    public void testLightBulbLightHasWireUp() {
        LightBulb lightBulb = new LightBulb("100",new Position(5, 5),"LightBulb");
        Map<Position, List<Entity>> map = new HashMap<>();
        Position up1 = new Position(5,4);
        map.put(up1, Arrays.asList(new Wire("101",up1,"Wire")));
        Position up2 = new Position(5,3);
        map.put(up2, Arrays.asList(new Wire("102",up2,"Wire")));
        Position up3 = new Position(5,2);
//        map.put(up3, Arrays.asList(new SwitchDoor(up3)));
        assertEquals(new Position(5, 5), lightBulb.getPosition());
        assertEquals(true, lightBulb.isLight(new HashMap<>()));
    }

    @Test
    public void testLightBulbLightHasWireDown() {
        LightBulb lightBulb = new LightBulb("100",new Position(5, 5),"LightBulb");
        Map<Position, List<Entity>> map = new HashMap<>();
        Position down1 = new Position(5,4);
        map.put(down1, Arrays.asList(new Wire("101",down1,"Wire")));
        Position down2 = new Position(5,3);
        map.put(down2, Arrays.asList(new Wire("102",down2,"Wire")));
        Position down3 = new Position(5,2);
//        map.put(down3, Arrays.asList(new SwitchDoor(down3)));
        assertEquals(new Position(5, 5), lightBulb.getPosition());
        assertEquals(true, lightBulb.isLight(new HashMap<>()));
    }


    @Test
    public void testLightBulbLightHasWireRight() {
        LightBulb lightBulb = new LightBulb("100",new Position(5, 5),"LightBulb");
        Map<Position, List<Entity>> map = new HashMap<>();
        Position right1 = new Position(5,4);
        map.put(right1, Arrays.asList(new Wire("101",right1,"Wire")));
        Position right2 = new Position(5,3);
        map.put(right2, Arrays.asList(new Wire("102",right2,"Wire")));
        Position right3 = new Position(5,2);
//        map.put(right3, Arrays.asList(new SwitchDoor(right3)));
        assertEquals(new Position(5, 5), lightBulb.getPosition());
        assertEquals(true, lightBulb.isLight(new HashMap<>()));
    }

    @Test
    public void testLightBulbLightHasWireLeft() {
        LightBulb lightBulb = new LightBulb("100",new Position(5, 5),"LightBulb");
        Map<Position, List<Entity>> map = new HashMap<>();
        Position left1 = new Position(4,5);
        map.put(left1, Arrays.asList(new Wire("101",left1,"Wire")));
        Position left2 = new Position(3,5);
        map.put(left2, Arrays.asList(new Wire("102",left2,"Wire")));
        Position left3 = new Position(2,5);
//        map.put(left3, Arrays.asList(new SwitchDoor(left3)));
        assertEquals(new Position(5, 5), lightBulb.getPosition());
        assertEquals(true, lightBulb.isLight(new HashMap<>()));
    }
}
