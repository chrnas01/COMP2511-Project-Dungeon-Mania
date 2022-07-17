package dungeonmania.battle;

import dungeonmania.movingEntities.MovingEntity;
import dungeonmania.movingEntities.Player;

public class Battle {

    private Player player;
    private MovingEntity enemy;

    public Battle(Player player, MovingEntity enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    public MovingEntity combat(Player player, MovingEntity enemy) {
        if (player.getInvincible()) {
            // run it once
            return player;
        }

        
    }



}
