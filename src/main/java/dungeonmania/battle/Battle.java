package dungeonmania.battle;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.collectableEntities.CollectableEntity;
import dungeonmania.collectableEntities.*;
import dungeonmania.movingEntities.MovingEntity;
import dungeonmania.movingEntities.Player;

public class Battle {

    private double initialPlayerHealth;
    private double initialEnemyHealth;
    private List<Round> rounds = new ArrayList<Round>();
    
    private Player player;
    private MovingEntity enemy;
    private List<CollectableEntity> inv = new ArrayList<>();

    public Battle(Player player, MovingEntity enemy) {
        this.initialPlayerHealth = player.getHealth();
        
        this.player = player;
        this.enemy = enemy;
    }




    /**
     * 
     * @return winner of the battle (player or enemy)
     */
    public MovingEntity combat() {
        inv = player.getInventory();
        double enemyhealth;
        double playerhealth;

        if (player.getInvincible()) {
            // run it once
            for (CollectableEntity item: inv){
                if (item.getType().equals("sword")){
                    item.use();
                }
                if (item.getType().equals("bow")){
                    item.use();
                }
            }
            
            return player;
        }
        else if (player.getInvisible()) {
            return null;
        }

        while (player.getHealth() > 0 && enemy.getHealth() > 0) {
            enemyhealth = healthCalculations(enemy, damageCalculations(player, enemy));
            enemy.setHealth(enemyhealth);

            if (enemy.getHealth() > 0){
                playerhealth = healthCalculations(player, damageCalculations(enemy, player));
                player.setHealth(playerhealth);
            }

        }

        if (player.getHealth() > 0){
            return player;
        }
        else {
            return enemy;
        }

        
    }

    public double damageCalculations(MovingEntity attacker, MovingEntity defender){
        double damage;

        if (attacker.getType().equals("player")){
            Player player = (Player) attacker;
            inv = player.getInventory();
            damage = player.getAttack();
            Sword sword = (Sword) player.getInvClass().getItemtype("sword");
            Bow bow = (Bow) player.getInvClass().getItemtype("bow");
            Shield shield = (Shield) player.getInvClass().getItemtype("shield");

            if (sword != null) {
                damage = damage + sword.getAttack();
                sword.use();
            }
            if (bow != null){
                damage = damage * 2;
                bow.use();
            }
            if (shield != null){
                damage = damage - shield.getDefence();
            }
            
            return damage;
        }
        else {
            return attacker.getAttack();
        }
        
    }
    
    public double healthCalculations(MovingEntity defender, double damage){
        double health;

        if (defender.getType().equals("player")){
            Player player = (Player) defender;
            health = player.getHealth();
            health = health - (damage/10);
            return health;
        }
        else {
            health = defender.getHealth();
            health = health - (damage/5);
            return health;
        }
    }


}
