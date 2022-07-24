package dungeonmania.movingEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Entity;
import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public abstract class MovingEntity extends Entity {
    
    private double health;
    private int attack;
    
    /**
     * Constructor for MovingEntity
     * @param id
     * @param position
     * @param type
     * @param health
     * @param attack
     */
    public MovingEntity(String id, Position position, String type, double health, int attack) {
        super(id, position, type);
        this.health = health;
        this.attack = attack;
    }

    /**
    * Return Adjacent positions in an array list with the following element positions:
    *   0
    * 3 p 1
    *   2 
    * @return
    */
    public List<Position> getCardinallyAdjacentSquares() {
        List<Position> caSquares = new ArrayList<Position>();

        caSquares.add(this.getPosition().translateBy(Direction.UP));
        caSquares.add(this.getPosition().translateBy(Direction.RIGHT));
        caSquares.add(this.getPosition().translateBy(Direction.DOWN));
        caSquares.add(this.getPosition().translateBy(Direction.LEFT));

        return caSquares;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public abstract void move(Direction direction, DungeonMap dungeon);
}
