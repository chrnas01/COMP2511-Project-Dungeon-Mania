package dungeonmania.movingEntities;

import dungeonmania.Entity;
import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public abstract class MovingEntity extends Entity {
    
    private double health;
    private int maxhealth;
    private int attack;
    
    /**
     * Constructor for MovingEntity
     * @param id
     * @param position
     * @param type
     * @param health
     * @param attack
     */
    public MovingEntity(String id, Position position, String type, int maxhealth, int attack) {
        super(id, position, type);
        this.attack = attack;
        this.maxhealth = maxhealth;
        this.health = maxhealth;
    }

    public int getmaxHealth(){
        return maxhealth;
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
