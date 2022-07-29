package dungeonmania.battle;

import java.util.List;

import dungeonmania.collectableEntities.*;;

public class Round {
    private double deltaPlayerHealth;
    private double deltaEnemyHealth;
    private List<CollectableEntity> weaponryUsed;

    Round (double deltaPlayerHealth, double deltaEnemyHealth, List<CollectableEntity> weaponryUsed) {
        this.deltaEnemyHealth = deltaEnemyHealth;
        this.deltaPlayerHealth = deltaPlayerHealth;
        this.weaponryUsed = weaponryUsed;
    }

    /**
     * Getter for deltaPlayerHealth
     * @return Change in player health for this round 
     */
    public double getDeltaPlayerHealth() {
        return this.deltaPlayerHealth;
    }

    /**
     * Getter for deltaEnemyHealth
     * @return Change in enemy health for this round 
     */
    public double getDeltaEnemyHealth() {
        return this.deltaEnemyHealth;
    }

    /**
     * Getter for weaponryUsed
     * @return weaponryUsed during the round
     */
    public List<CollectableEntity> getWeaponryUsed() {
        return this.weaponryUsed;
    }


}
