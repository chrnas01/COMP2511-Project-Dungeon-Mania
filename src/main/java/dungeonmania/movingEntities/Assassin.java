package dungeonmania.movingEntities;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Assassin extends Mercenary{
    
    private double bribeFailRate;
    private int reconRadius;
    
    /**
     * Constructor for Mercenary
     *
     * @param id
     * @param position
     * @param type
     * @param health
     * @param attack
     */
    public Assassin(String id, Position position, String type, int health, int attack, int bribe_amount, double bribeFailRate, int reconRadius) {
        super(id, position, type, health, attack, bribe_amount);
        this.bribeFailRate = bribeFailRate;
        this.reconRadius = reconRadius;
    }
}
