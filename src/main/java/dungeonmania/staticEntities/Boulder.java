package dungeonmania.staticEntities;

import java.util.List;

import dungeonmania.Entity;
import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.movingEntities.MovingEntity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Boulder extends StaticEntity {

    /**
     * Constructor for Boulder
     * @param id
     * @param position
     * @param type
     */
    public Boulder (String id, Position position, String type) {
        super(id, position, type);
    }


    /**
     * Check if boulder can move and move it if possible.
     * @param dungeon
     * @param direction
     * @return boolean
     */
    public boolean moveDirection(DungeonMap dungeon, Direction direction) {
        Position next = this.getPosition().translateBy(direction);
        List<Entity> ent_in_position = dungeon.getMap().get(next);
        if (ent_in_position == null) {
            dungeon.addPosition(next);
            dungeon.moveEntity(this.getPosition(), next, this);
            this.setPosition(next);
            return true;
        }
        for (Entity ent : ent_in_position) {
            if (ent instanceof MovingEntity || (ent instanceof StaticEntity && !(ent instanceof FloorSwitch) && !(ent instanceof Door)) || 
                (ent instanceof Door && !((Door) ent).getOpen())) {
                return false;
            }
        }
        dungeon.moveEntity(this.getPosition(), next, this);
        return true;
    }
    public boolean tryDirection(DungeonMap dungeon, Direction direction) {
        Position next = this.getPosition().translateBy(direction);
        List<Entity> ent_in_position = dungeon.getMap().get(next);
        if (ent_in_position == null) {
            dungeon.addPosition(next);
            dungeon.moveEntity(this.getPosition(), next, this);
            this.setPosition(next);
            return true;
        }
        for (Entity ent : ent_in_position) {
            if (ent instanceof MovingEntity || (ent instanceof StaticEntity && !(ent instanceof FloorSwitch) && !(ent instanceof Door)) ||
                    (ent instanceof Door && !((Door) ent).getOpen())) {
                return false;
            }
        }
        return true;
    }
}
