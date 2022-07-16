package dungeonmania.staticEntities;

import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.util.Position;

public class ZombieToastSpawner extends StaticEntity {

    // Needs to be able to interact with interface

    /**
     * Constructor for ZombieToastSpawner
     * @param id
     * @param position
     * @param type
     */
    public ZombieToastSpawner (String id, Position position, String type) {
        super(id, position, type);
    }

//    public ZombieToast generateZombieToast(Position zombieToastPosition){
//        return new ZombieToast(zombieToastPosition);
//    }

    /**
     * Destroy the Zombie Toast Spawner
     * @param dungeon
     */
    public void destroyed(DungeonMap dungeon) {
        dungeon.getMap().get(this.getPosition()).remove(this);
    }
}
