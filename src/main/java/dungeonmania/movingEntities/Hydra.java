package dungeonmania.movingEntities;

import dungeonmania.util.Position;

public class Hydra extends ZombieToast{
    /**
     * Constructor for ZombieToast
     *
     * @param id
     * @param position
     * @param type
     * @param health
     * @param attack
     */
    public Hydra(String id, Position position, String type, int health, int attack) {
        super(id, position, type, health, attack);
    }
}
