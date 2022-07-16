package dungeonmania.movingEntities;

import dungeonmania.Entity;
import dungeonmania.Inventory;
import dungeonmania.util.Position;

public class Player extends MovingEntity {

    private Inventory inventory = new Inventory(this);

    public Player(String id, Position position, String type) {
        super(id, position, type);
    }
}
