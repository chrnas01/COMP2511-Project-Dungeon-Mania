package dungeonmania.movingEntities;

import java.util.Random;

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

    /**
     * Getter for reckonRadius
     * @return reckonRadius
     */
    public int getReckonRadius() {
        return this.reconRadius;
    }

    @Override
    public void bribe() {
        // Bribe will never fail
        if (this.bribeFailRate <= 0) {
            super.bribe();
            return;
        }

        double percentageBribeFailRate = Math.round(bribeFailRate * 100.0);

        // 0 <= random <= 99
        int random = new Random().nextInt(100);

        if (percentageBribeFailRate < random) {
            super.bribe();
        }

    }
}
