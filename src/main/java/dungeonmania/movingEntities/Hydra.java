package dungeonmania.movingEntities;

import dungeonmania.util.Position;

public class Hydra extends ZombieToast{
    
    private int healthIncreaseAmount;
    private int healthIncreaseRate;
    
    /**
     * Constructor for ZombieToast
     *
     * @param id
     * @param position
     * @param type
     * @param health
     * @param attack
     */
    public Hydra(String id, Position position, String type, int attack, int health, int healthIncreaseAmount, int healthIncreaseRate) {
        super(id, position, type, attack, health);

        this.healthIncreaseAmount = healthIncreaseAmount;
        this.healthIncreaseRate = healthIncreaseRate;
    }

    /**
     * Getter for healthIncreaseAmount
     * @return the amount of health the hydra will increase by if the players attack fails
     */
    public int getHealthIncreaseAmount() {
        return this.healthIncreaseAmount;
    }

    /**
     * Getter for healthIncreaseRate
     * @return Chance the players attack will fail 
     */
    public int getHeathIncreaseRate() {
        return this.healthIncreaseRate;
    }
}
