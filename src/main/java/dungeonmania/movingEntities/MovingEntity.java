package dungeonmania.movingEntities;

import dungeonmania.Entity;
import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public abstract class MovingEntity extends Entity {
    
    private int health;
    private int attack;
    
    /**
     * Constructor for MovingEntity
     * @param id
     * @param position
     * @param type
     * @param health
     * @param attack
     */
    public MovingEntity(String id, Position position, String type, int health, int attack) {
        super(id, position, type);
        this.health = health;
        this.attack = attack;
    }


    public double getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public abstract void move(Direction direction, DungeonMap dungeon);
}
