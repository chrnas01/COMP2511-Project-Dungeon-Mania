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
    private int index;

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
        this.index = 9;
        this.path = position.getAdjacentPositions();
    }

    /**
     * Check if can move to position
     * 
     * @param dungeon
     * @return true if possible, false otherwise
     */
    public boolean checkInfront(Position next, DungeonMap dungeon) {
        List<Entity> entities = dungeon.getMap().get(next);
        if (entities == null) {
            dungeon.addPosition(next);
            return true;
        }
        for (Entity entity : entities) {
            if (entity instanceof Boulder) {
                this.setClockwise(!this.getClockwise());
                return false;
            }
        }
        return true;
    }

    @Override
    public void move(Direction direction, DungeonMap dungeon) {
        this.move(dungeon);
    }

    public void move(DungeonMap dungeon) {

        for (Entity entity : dungeon.getMap().get(this.getPosition())) {
            if (entity instanceof Boulder) {
                return;
            }
        }
        Position next_pos;
        int next_index;
        Position prev_pos = null;
        int prev_index = 0;
        if (this.getIndex() == 9) {
            next_pos = this.getPath().get(1);
            next_index = 1;
        } else if (this.getClockwise()) {
            next_index = (this.getIndex() + 1) % 8;
            next_pos = this.getPath().get(next_index);
            prev_index = (this.getIndex() + 7) % 8;
            prev_pos = this.getPath().get(next_index);
        } else {
            next_index = (this.getIndex() + 7) % 8;
            next_pos = this.getPath().get(next_index);
            prev_index = (this.getIndex() + 1) % 8;
            prev_pos = this.getPath().get(next_index);
        }

        if (this.checkInfront(next_pos, dungeon)) {
            dungeon.moveEntity(this.getPosition(), next_pos, this);
            this.setPosition(next_pos);
            this.setIndex(next_index);
            return;
        }
        if (prev_pos == null) {
            return;
        }
        if (this.checkInfront(prev_pos, dungeon)) {
            dungeon.moveEntity(this.getPosition(), prev_pos, this);
            this.setPosition(prev_pos);
            this.setIndex(prev_index);
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

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
