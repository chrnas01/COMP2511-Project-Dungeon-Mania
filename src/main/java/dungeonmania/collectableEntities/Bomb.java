package dungeonmania.collectableEntities;

import java.util.List;

import dungeonmania.Entity;
import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.movingEntities.Player;
import dungeonmania.staticEntities.FloorSwitch;
import dungeonmania.util.Position;

public class Bomb extends CollectableItem {

    private int radius;
    private boolean placed;

    /**
     * Constructor for Bomb
     * 
     * @param id
     * @param position
     * @param type
     */
    public Bomb(String id, Position position, String type, int radius) {
        super(id, position, type);
        this.setRadius(radius);
        this.setPlaced(false);
    }

    @Override
    public void use() {
        this.setPosition(this.getPlayer().getPosition());
        this.getPlayer().getInventory().remove(this);
        this.setPlaced(true);
    }

    /**
     * Place bomb onto the map
     * 
     * @param dungeon
     */
    public void place(DungeonMap dungeon) {
        this.use();
        dungeon.addEntity(this.getPosition(), this);
    }

    /**
     * Bomb explodes once placed on map and activated
     * 
     * @param dungeon
     */
    public void explode(DungeonMap dungeon) {

        boolean active = false;
        List<Position> adjacent = this.getPosition().getAdjacentPositions();
        for (Position position : adjacent) {
            dungeon.addPosition(position);
            if (!Position.isAdjacent(this.getPosition(), position)) {
                continue;
            }
            for (Entity ent : dungeon.getMap().get(position)) {
                if (ent instanceof FloorSwitch && ((FloorSwitch) ent).getUnderBoulder()) {
                    active = true;
                    break;
                }
            }
        }
        if (!active) {
            return;
        }

        for (Position neighbour : dungeon.getMap().keySet()) {
            Position distance = Position.calculatePositionBetween(this.getPosition(), neighbour);
            if (Math.abs(distance.getX()) <= this.radius && Math.abs(distance.getY()) <= this.radius) {
                boolean blow = true;
                while (blow) {
                    blow = false;
                    for (Entity entity : dungeon.getMap().get(neighbour)) {
                        if (!(entity instanceof Player)) {
                            dungeon.getMap().get(neighbour).remove(entity);
                            blow = true;
                            break;
                        }
                    }
                }
            }
        }
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public boolean getPlaced() {
        return this.placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }
}
