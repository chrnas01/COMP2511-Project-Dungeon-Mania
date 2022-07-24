package dungeonmania.movingEntities;

import java.util.List;

import dungeonmania.Entity;
import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.staticEntities.Boulder;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Spider extends MovingEntity {

    private boolean clockwise;
    private List<Position> path;

    /**
     * Constructor for Spider
     * 
     * @param id
     * @param position
     * @param type
     * @param health
     * @param attack
     */
    public Spider(String id, Position position, String type, int health, int attack) {
        super(id, position, type, health, attack);
        this.clockwise = true;
        this.path = position.getAdjacentPositions();
    }

    /**
     * Checks if boulder is in the given position in the given dungeon
     * 
     * @param dungeon
     * @return Returns true if there is a boulder there, false otherwise
     */
    public boolean isBoulderInfront(Position next, DungeonMap dungeon) {
        dungeon.addPosition(next);
        List<Entity> map = dungeon.getMap().get(next);

        for (Entity entity : map) {
            if (entity instanceof Boulder) {
                this.setClockwise(!this.getClockwise());
                return true;
            }
        }

        return false;
    }

    @Override
    public void move(Direction direction, DungeonMap dungeon) {
        this.move(dungeon);
    }

    public void move(DungeonMap dungeon) {
        Position currPos = this.getPosition();

        // If spiders currPos is not included in path then it just spawned.
        // If there is no boulder infront move up, else it is "stuck" in its spawn position
        if (!this.path.contains(currPos) && !isBoulderInfront(currPos.translateBy(Direction.UP), dungeon)) { 
            dungeon.moveEntity(currPos, currPos.translateBy(Direction.UP), this);
        }
        // Spider is already moving on path
        else if (this.path.contains(currPos)) {
            int currIndex = this.path.indexOf(currPos);
            Position nextClockwisePos = currIndex == 7 ? path.get(0) : path.get(currIndex + 1);
            Position nextAntiCockwisePos = currIndex == 0 ? path.get(7) : path.get(currIndex - 1);

            if (this.clockwise && !isBoulderInfront(nextClockwisePos, dungeon)) {
                dungeon.moveEntity(currPos, nextClockwisePos, this);
            } 
            else if (!this.clockwise && !isBoulderInfront(nextAntiCockwisePos, dungeon)) {
                dungeon.moveEntity(currPos, nextAntiCockwisePos, this);
            }
        }
    }

    public List<Position> getPath() {
        return this.path;
    }

    public boolean getClockwise() {
        return this.clockwise;
    }

    public void setClockwise(boolean clockwise) {
        this.clockwise = clockwise;
    }
}
