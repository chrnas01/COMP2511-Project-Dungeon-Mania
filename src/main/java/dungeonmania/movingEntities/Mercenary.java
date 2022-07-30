package dungeonmania.movingEntities;

import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Mercenary extends MovingEntity {

    private int bribeAmount;
    private int controlDuration = 0;
    private MercenaryState mercState;
    private MercenaryAllyState mercAllyState;
    private MercenaryEnemyState mercEnemyState;

    /**
     * Constructor for Mercenary
     * 
     * @param id
     * @param position
     * @param type
     * @param health
     * @param attack
     * @param bribeAmount
     */
    public Mercenary(String id, Position position, String type, int health, int attack, int bribeAmount) {
        super(id, position, type, health, attack);
        this.bribeAmount = bribeAmount;
        this.mercAllyState = new MercenaryAllyState(this);
        this.mercEnemyState = new MercenaryEnemyState(this);
        this.mercState = this.mercEnemyState;
    }

    /**
     * Getter for isHostile
     * 
     * @return
     */
    public boolean getIsHostile() {
        return this.mercState instanceof MercenaryEnemyState;
    }

    /**
     * Getter for controlDuration
     * 
     * @return how long this mercenary is under control for
     */
    public int getControlDuration() {
        return this.controlDuration;
    }

    /**
     * Setter for controlDuration
     * @param duration
     */
    public void setControlDuration(int duration) {
        this.controlDuration = duration;
    }

    /**
     * Getter for bribeAmount
     * @return Amount of gold required to bribe this mercenary
     */
    public int getBribeAmount() {
        return this.bribeAmount;
    }

    @Override
    public void move(Direction direction, DungeonMap dungeon) {
        this.move(dungeon);
    }

    public void move(DungeonMap dungeon) {
        this.mercState.move(dungeon);
    }

    /**
     * Move the mercenary in random manner for when player is invisible
     * 
     * @param dungeon
     */
    public void moveRandom(DungeonMap dungeon) {
        this.mercState.moveRandom(dungeon);
    }

    /**
     * Changes mercenary to ally
     */
    public void bribe() {
        this.mercState = this.mercAllyState;
        this.setControlDuration(-1);
    }

    /**
     * Temporarily changes mercenary to ally
     * for duration ticks
     * @param duration 
     */
    public void brainwash(int duration) {
        this.mercState = this.mercAllyState;
        this.setControlDuration(duration);
    }

    /**
     * Change mercenary state to enemy state.
     */
    public void becomeHostile() {
        this.mercState = this.mercEnemyState;
    }

}
