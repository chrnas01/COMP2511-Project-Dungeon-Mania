package dungeonmania.Battle;

import java.util.List;

import dungeonmania.collectableEntities.*;;

public class Round {
    private double currentEnemyHealth;
    private double currentPlayerHealth;
    private double deltaEnemyHealth;
    private double deltaPlayerHealth;
    private List<CollectableEntity> weaponryUsed;

    Round (double currentEnemyHealth, double currentPlayerHealth, double deltaEnemyHealth, double deltaPlayerHealth, List<CollectableEntity> weaponryUsed) {
        this.currentEnemyHealth = currentEnemyHealth;
        this.currentPlayerHealth = currentPlayerHealth;
        this.deltaEnemyHealth = deltaEnemyHealth;
        this.deltaPlayerHealth = deltaPlayerHealth;
        this.weaponryUsed = weaponryUsed;
    }

    /**
     * Getter for currentEnemyPlayer
     * @return health of enemy for this round
     */
    public double getCurrentEnemyHealth() {
        return this.currentEnemyHealth;
    }

    /**
     * Getter for currentPlayerHealth
     * @return health of player for this round 
     */
    public double getCurrentPlayerHealth() {
        return this.currentPlayerHealth;
    }

        /**
     * Getter for deltaEnemyHealth
     * @return Change in enemy health for this round 
     */
    public double getDeltaEnemyHealth() {
        return this.deltaEnemyHealth;
    }

    /**
     * Getter for deltaPlayerHealth
     * @return Change in player health for this round 
     */
    public double getDeltaPlayerHealth() {
        return this.deltaPlayerHealth;
    }


    /**
     * Getter for weaponryUsed
     * @return weaponryUsed during the round
     */
    public List<CollectableEntity> getWeaponryUsed() {
        return this.weaponryUsed;
    }

    @Override
    public String toString() {
        return currentEnemyHealth + " " + currentPlayerHealth; 
    }


}
