package dungeonmania.collectableEntities;

import dungeonmania.Entity;
import dungeonmania.movingEntities.Player;
import dungeonmania.util.Position;

public abstract class CollectableEntity extends Entity {
    
    private Player player;

    /**
     * Constructor for CollectableEntity
     * @param id
     * @param position
     * @param type
     */
    public CollectableEntity(String id, Position position, String type) {
        super(id, position, type);
    }

    /**
     * Use the item
     */
    abstract public void use();

    /**
     * Getter for player
     * @return
     */
    public Player getPlayer() {
        return player;
    }
    
    /**
     * Setter for player
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
