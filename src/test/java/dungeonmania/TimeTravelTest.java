package dungeonmania;

import dungeonmania.collectableEntities.TimeTravel;
import dungeonmania.collectableEntities.TimeTurner;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.sql.Time;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeTravelTest {
    public void testTimeRunner() {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("advanced", "simple");
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.DOWN);
        TimeTurner timeTurner = new TimeTurner(String.valueOf(5), new Position(1, 1), "TimeTurner");
        timeTurner.travelBefore(dmc, 5);
        assertEquals(1, dmc.getTickCounter());
    }

    public void testTimeTravel() {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("advanced", "simple");
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.DOWN);
        TimeTravel timeTravel = new TimeTravel(String.valueOf(5), new Position(1, 1), "TimeTurner");
        timeTravel.travelBefore(dmc);
        assertEquals(0, dmc.getTickCounter());
    }

    public void testTimeTravelOver30() {
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("advanced", "simple");
        for (int i = 0; i < 50; i++) {
            dmc.tick(Direction.DOWN);
        }
        TimeTravel timeTravel = new TimeTravel(String.valueOf(5), new Position(1, 1), "TimeTurner");
        timeTravel.travelBefore(dmc);
        assertEquals(50 - 30, dmc.getTickCounter());
    }
}
