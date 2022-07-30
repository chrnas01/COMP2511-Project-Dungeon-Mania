package dungeonmania.staticEntities;

import java.util.List;

import dungeonmania.Entity;
import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.collectableEntities.CollectableEntity;
import dungeonmania.movingEntities.Mercenary;
import dungeonmania.movingEntities.MovingEntity;
import dungeonmania.movingEntities.Player;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Portal extends StaticEntity {

    private String colourId;

    /**
     * Constructor for Portal
     * @param id
     * @param position
     * @param type
     * @param colourId
     */
    public Portal (String id, Position position, String type, String colourId) {
        super(id, position, type);
        this.colourId = colourId;
    }

    /**
     * Getter for colourId
     * @return colour of portal
     */
    public String getColour() {
        return this.colourId;
    }

    /**
     * Checks if the moving entity can teleport to the corresponding portal
     * @param dungeon
     * @param direction
     * @param entity
     * @return boolean
     */
    public boolean teleport(DungeonMap dungeon, Direction direction, MovingEntity mover) {
        Portal teleportal = null;
        for (List<Entity> entities : dungeon.getMap().values()) {
            for (Entity ent : entities) {
                if (ent instanceof Portal && !ent.getId().equals(this.getId()) && ((Portal) ent).getColour().equals(colourId)) {
                    teleportal = (Portal) ent;
                    break;
                }
            }
        }

        Position next = teleportal.getPosition().translateBy(direction);
        List<Entity> ent_in_position = dungeon.getMap().get(next);
        if (ent_in_position == null) {
            dungeon.addPosition(next);
            mover.setPosition(next);
            return true;
        }
        for (Entity ent : ent_in_position) {
            if (ent instanceof Portal) {
                return ((Portal) ent).teleport(dungeon, direction, mover);
            }
            if (ent instanceof Wall) {return false;}
            if (ent instanceof Boulder && mover instanceof Mercenary) {return false;}
            if ((ent instanceof Boulder && ((Boulder) ent).moveDirection(dungeon, direction))
                || (ent instanceof Door && ((Door) ent).getOpen())) {
                mover.setPosition(next);
                return true;
            }
            if (ent instanceof Boulder) {return false;}
            if (ent instanceof ZombieToastSpawner) {return false;}
            if (ent instanceof Door && mover instanceof Mercenary) {return false;}
            if (ent instanceof Door) {
                CollectableEntity key = ((Player) mover).getInvClass().findKey(((Door) ent).getKeyId());
                if (key == null) {return false;}
                key.use();
                ((Door)ent).setOpen(true);
                mover.setPosition(next);
                return true;
            }
        }
        mover.setPosition(next);
        return true;
    }

}
