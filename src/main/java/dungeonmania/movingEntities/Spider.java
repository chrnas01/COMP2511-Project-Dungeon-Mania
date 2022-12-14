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
    public Spider(String id, Position position, String type, double health, int attack) {
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
                this.clockwise = !this.clockwise;
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
                return;
            }
            if (!this.clockwise && !isBoulderInfront(nextAntiCockwisePos, dungeon)) {
                dungeon.moveEntity(currPos, nextAntiCockwisePos, this);
                return;
            }
            if (this.clockwise && !isBoulderInfront(nextClockwisePos, dungeon)) {
                dungeon.moveEntity(currPos, nextClockwisePos, this);
            } 
        }
    }

    /**
     * Getter for path
     * @return a list of positions around spawn position
     */
    public List<Position> getPath() {
        return this.path;
    }

    /**
     * Getter for clockwise
     * @return true if spider is clockwise false otherwise
     */
    public boolean getClockwise() {
        return this.clockwise;
    }

}
