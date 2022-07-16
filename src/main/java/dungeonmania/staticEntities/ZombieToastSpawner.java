package dungeonmania.staticEntities;

import dungeonmania.util.Position;

public class ZombieToastSpawner extends StaticEntity {

    private int ticker = 0;

    /**
     * Constructor for ZombieToastSpawner
     * @param id
     * @param position
     * @param type
     */
    public ZombieToastSpawner (String id, Position position, String type) {
        super(id, position, type);
    }

    public void tick() {

    }

//    public ZombieToast generateZombieToast(Position zombieToastPosition){
//        return new ZombieToast(zombieToastPosition);
//    }

    public void destroyed() {
        // Position location = this.getPosition();
    }
}
