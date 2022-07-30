package dungeonmania.Battle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dungeonmania.collectableEntities.*;
import dungeonmania.movingEntities.*;

public class Battle {

    private double initialPlayerHealth;
    private double initialEnemyHealth;
    private List<Round> rounds = new ArrayList<Round>();

    private Player player;
    private MovingEntity enemy;

    public Battle(Player player, MovingEntity enemy) {
        this.initialPlayerHealth = player.getHealth();
        this.initialEnemyHealth = enemy.getHealth();

        this.player = player;
        this.enemy = enemy;
    }

    /**
     * Getter for initialPlayerHealth
     * 
     * @return players initial health
     */
    public double getInitialPlayerHealth() {
        return this.initialPlayerHealth;
    }

    /**
     * Getter for initialEnemyHealth
     * 
     * @return players initial health
     */
    public double getInitialEnemyHealth() {
        return this.initialEnemyHealth;
    }

    /**
     * Getter for rounds
     * 
     * @return List of rounds involved in this battle
     */
    public List<Round> getRounds() {
        return this.rounds;
    }

    /**
     * Getter for enemy
     * 
     * @return enemy in this battle
     */
    public MovingEntity getEnemy() {
        return this.enemy;
    }

    /**
     * 
     * @return winner of the battle (player or enemy)
     */
    public MovingEntity combat() {
        // Battles do not occur when a player is under the influence of an invisibility
        // potion.
        if (player.getInvisible()) {
            return null;
        }

        // Any battles that occur when the Player has the effects of the potion end
        // immediately
        // after the first round, with the Player immediately winning.
        else if (player.getInvincible()) {
            // Assumption: Player does not use weapons when they are invincible (Even if
            // they have them)
            double deltaEnemyHealth = -this.initialEnemyHealth;
            double deltaPlayerHealth = 0;
            List<CollectableEntity> weaponryUsed = new ArrayList<CollectableEntity>();
            weaponryUsed.add(player.getPotionQueue().get(0));

            this.rounds.add(new Round(0, this.initialPlayerHealth, deltaEnemyHealth, deltaPlayerHealth, weaponryUsed));

            return player;
        }

        List<CollectableEntity> weaponryUsed = new ArrayList<CollectableEntity>();
        this.player.getInventory().forEach((item) -> {
            if (item instanceof Weapons) {
                weaponryUsed.add(item);
            }
        });
        
        while (player.getHealth() > 0 && enemy.getHealth() > 0) {
            boolean hydraHeals = hydraHeals();
            int hydraHealthIncreaseAmount = hydraHeals ? ((Hydra) enemy).getHealthIncreaseAmount() : 0;
            
            // first round
            if (this.rounds.isEmpty()) {
                double currentEnemyHealth = hydraHeals ? this.initialEnemyHealth + hydraHealthIncreaseAmount :initialEnemyHealth - (getPlayerAttack() / 5);
                double currentPlayerHealth = initialPlayerHealth - (getEnemyAttack() / 10);

                double deltaEnemyHealth = Math.round((currentEnemyHealth - initialEnemyHealth) * 10.0) / 10.0;
                double deltaPlayerHealth = Math.round((currentPlayerHealth - initialPlayerHealth) * 10.0) / 10.0;

                enemy.setHealth(currentEnemyHealth);
                player.setHealth(currentPlayerHealth);
                this.rounds.add(new Round(currentEnemyHealth, currentPlayerHealth, deltaEnemyHealth, deltaPlayerHealth,
                        weaponryUsed));
            } 
            // We need to get the results of the last round
            else {
                Round lastRound = this.rounds.get(rounds.size() - 1);
                double currentEnemyHealth = hydraHeals ? lastRound.getCurrentEnemyHealth() + hydraHealthIncreaseAmount : lastRound.getCurrentEnemyHealth() - (getPlayerAttack() / 5);
                double currentPlayerHealth = lastRound.getCurrentPlayerHealth() - (getEnemyAttack() / 10);

                double deltaEnemyHealth = Math.round((currentEnemyHealth - lastRound.getCurrentEnemyHealth()) * 10.0) / 10.0;
                double deltaPlayerHealth = Math.round((currentPlayerHealth - lastRound.getCurrentPlayerHealth()) * 10.0) / 10.0;

                enemy.setHealth(currentEnemyHealth);
                player.setHealth(currentPlayerHealth);
                this.rounds.add(new Round(currentEnemyHealth, currentPlayerHealth, deltaEnemyHealth, deltaPlayerHealth,
                        weaponryUsed));
            }
        }

        weaponryUsed.forEach((weapon) -> {
            weapon.use();
        });

        return player.getHealth() <= 0 ? enemy : player;
    }

    /**
     * If hydra will heal or take damage based on a given probability from config.
     * @return true if hydra heals false if they take damage.
     */
    public boolean hydraHeals() {
        if (!(enemy instanceof Hydra)) {
            return false;
        }

        double chanceHydraHeals = ((Hydra) enemy).getHeathIncreaseRate();
        if (chanceHydraHeals <= 0) {
            return false;
        }
        else if (chanceHydraHeals >= 1) {
            return true;
        }

        double percentageHydraHeals = Math.round(chanceHydraHeals * 100.0);

        // 0 <= random <= 99
        int random = new Random().nextInt(100);

        return percentageHydraHeals > random;

    }
    
    /**
     * The enemies attack considering if the player has a shield.
     * @return the enemy entities attack
     */
    public double getEnemyAttack() {
        int shieldDef = 0;
        int midnightDefence = 0;
        
        for (CollectableEntity item : player.getInventory()) {
            if (item instanceof Shield) {
                shieldDef = ((Shield) item).getDefence();
            }
            if (item instanceof MidnightArmour) {
                midnightDefence = ((MidnightArmour) item).getDefence();
            }
        }

        return enemy.getAttack() - (shieldDef + midnightDefence);
    }

    /**
     * The players attack considering if the player has a sword or bow.
     * @return the players attack
     */
    public double getPlayerAttack() {
        // Check if player has bow and sword

        double swordAttack = 0;
        double midnightAttack = 0;
        int hasBow = 1;
        
        for (CollectableEntity item : player.getInventory()) {
            if (item instanceof Bow) {
                hasBow = 2;
            } 
            if (item instanceof Sword) {
                swordAttack = ((Sword) item).getAttack();
            }
            if (item instanceof MidnightArmour) {
                midnightAttack = ((MidnightArmour) item).getAttack();
            }
        }

        return hasBow * (player.getAttack() + swordAttack + midnightAttack);
    }
}
