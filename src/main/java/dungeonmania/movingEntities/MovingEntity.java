package dungeonmania.movingEntities;

import dungeonmania.Entity;
import dungeonmania.util.Position;

public abstract class MovingEntity extends Entity {
    private double health;
    private double attack;
    

    public MovingEntity(String id, Position position, String type, double health, double attack) {
        super(id, position, type);
        this.health = health;
        this.attack = attack;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public void moving(){}
}
