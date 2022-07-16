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
    private Position pickup;

    /**
     * Constructor for Bomb
     * @param id
     * @param position
     * @param type
     */
    public Bomb(String id, Position position, String type, int radius) {
        super(id, position, type);
        this.setRadius(radius);
        this.setPlaced(false);
        this.pickup = position;
    }

    @Override
    public void use() {
        this.setPosition(this.getPlayer().getPosition());
        this.getPlayer().getInventory().remove(this);
        this.setPlaced(true);
    }

    /**
     * Bomb explodes once placed on map and activated
     * @param dungeon
     */
    public void explode(DungeonMap dungeon) {

        if (!this.pickup.equals(this.getPosition())) {
            dungeon.getMap().get(this.pickup).remove(this);
            dungeon.getMap().get(this.getPosition()).add(this);
        }

        boolean active = false;
        List<Position> adjacent = this.getPosition().getAdjacentPositions();
        adjacent.add(this.getPosition());
        for (Position position : adjacent) {
            if (!Position.isAdjacent(this.getPosition(), position)) {break;}
            for (Entity ent : dungeon.getMap().get(position)) {
                if (ent instanceof FloorSwitch && ((FloorSwitch) ent).getUnderBoulder()) {
                    active = true;
                    break;
                }
            }
        }
        if (!active) {return;}

        for (Position neighbour : dungeon.getMap().keySet()) {
            Position distance = Position.calculatePositionBetween(this.getPosition(), neighbour);
            if (distance.getX() < this.radius && distance.getY() < this.radius) {
                for (Entity entity : dungeon.getMap().get(neighbour)) {
                    if (!(entity instanceof Player)) {
                        dungeon.getMap().get(neighbour).remove(entity);
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
