package dungeonmania.collectableEntities;

import dungeonmania.Entity;
import dungeonmania.movingEntities.Player;
import dungeonmania.util.Position;

public abstract class CollectableEntity extends Entity {
    
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

    abstract public void use();

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
