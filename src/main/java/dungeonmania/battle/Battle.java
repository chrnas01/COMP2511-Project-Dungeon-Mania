package dungeonmania.Battle;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.collectableEntities.CollectableEntity;
import dungeonmania.collectableEntities.*;
import dungeonmania.movingEntities.*;
import dungeonmania.movingEntities.Player;

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
     * Getter for player
     * 
     * @return player in this battle
     */
    public Player getPlayer() {
        return this.player;
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
            rounds.add(new Round(0, this.initialPlayerHealth, deltaEnemyHealth, deltaPlayerHealth,
                    new ArrayList<CollectableEntity>()));

            return player;
        }
        // else if (this.enemy instanceof Hydra) {
        //     this.hydraCombat();
        //     return;
        // }

        while (player.getHealth() > 0 && enemy.getHealth() > 0) {
            List<CollectableEntity> weaponryUsed = new ArrayList<CollectableEntity>();
            this.player.getInventory().forEach((item) -> {
                if (item instanceof Bow || item instanceof Sword || item instanceof Shield) {
                    weaponryUsed.add(item);
                }
            });

            // first round
            if (this.rounds.isEmpty()) {
                double currentEnemyHealth = initialEnemyHealth - (getPlayerAttack() / 5);
                double currentPlayerHealth = initialPlayerHealth - (getEnemyAttack() / 10);
                double deltaEnemyHealth = - Math.round((getPlayerAttack() / 5) * 10.0) / 10.0;
                double deltaPlayerHealth = - Math.round((getEnemyAttack() / 10) * 10.0) / 10.0;

                enemy.setHealth(currentEnemyHealth);
                player.setHealth(currentPlayerHealth);
                this.rounds.add(new Round(currentEnemyHealth, currentPlayerHealth, deltaEnemyHealth, deltaPlayerHealth,
                        weaponryUsed));
            } else {
                // We need to get the results of the last round
                Round lastRound = this.rounds.get(rounds.size() - 1);

                double currentEnemyHealth = lastRound.getCurrentEnemyHealth() - (getPlayerAttack() / 5) ;
                double currentPlayerHealth = lastRound.getCurrentPlayerHealth() - (getEnemyAttack() / 10);
                
                double deltaEnemyHealth = - Math.round((getPlayerAttack() / 5) * 10.0) / 10.0;
                double deltaPlayerHealth = - Math.round((getEnemyAttack() / 10) * 10.0) / 10.0;

                enemy.setHealth(currentEnemyHealth);
                player.setHealth(currentPlayerHealth);

                this.rounds.add(new Round(currentEnemyHealth, currentPlayerHealth, deltaEnemyHealth, deltaPlayerHealth,
                        weaponryUsed));
            }
        }

        // account for durability
        // Weapon used in one round should be weapon used in all rounds so, 
        this.rounds.get(0).getWeaponryUsed().forEach((weapon) -> {
            weapon.use();
        });

        return player.getHealth() <= 0 ? enemy : player;
    }

    public void hydraCombat() {
        System.out.println("Yes");
    }

    private double getEnemyAttack() {
        int shieldDef = 0;
        
        for (CollectableEntity item : player.getInventory()) {
            if (item instanceof Shield) {
                shieldDef = ((Shield) item).getDefence();
            }
        }
        
        return enemy.getAttack() - shieldDef;
    }

    private double getPlayerAttack() {
        // Check if player has bow and sword

        double swordAttack = 0;
        int hasBow = 1;
        
        for (CollectableEntity item : player.getInventory()) {
            if (item instanceof Bow) {
                hasBow = 2;
            } else if (item instanceof Sword) {
                swordAttack = ((Sword) item).getAttack();
            }
        }

        return hasBow * (player.getAttack() + swordAttack);
    }
}
