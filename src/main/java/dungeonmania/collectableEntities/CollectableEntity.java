package dungeonmania.collectableEntities;

import dungeonmania.Entity;
import dungeonmania.movingEntities.Player;
import dungeonmania.util.Position;

public abstract class CollectableEntity extends Entity {
    
    // Will need the player field for when collected
    // Should look something like:
    private Player player;

    /**
     * Constructor for CollectableEntity
     * @param id
     * @param type
     * @param position
     */
    public CollectableEntity(String id, Position position, String type) {
        super(id, position, type);
    }

    /**
     * Abstract method 
     */
    // abstract public void use();


    // Getters and Setters
    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
