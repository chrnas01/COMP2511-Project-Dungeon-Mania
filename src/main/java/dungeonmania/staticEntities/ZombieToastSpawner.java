package dungeonmania.staticEntities;

import dungeonmania.DungeonMap.Config;
import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.movingEntities.ZombieToast;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ZombieToastSpawner extends StaticEntity {

    private int tick = 0;
    private int rate;

    /**
     * Constructor for ZombieToastSpawner
     * @param id
     * @param position
     * @param type
     */
    public ZombieToastSpawner (String id, Position position, String type) {
        super(id, position, type);
    }

    /**
     * Spawn a zombie toast
     * @param dungeon
     */
    public void generateZombieToast(DungeonMap dungeon) {
        Config con = dungeon.getConfig();
        this.rate = con.ZOMBIE_SPAWN_RATE;

        if (this.tick % this.rate != 0) {return;}
        ZombieToast zombie = new ZombieToast("toast"+Integer.toString(this.tick), this.getPosition(), "zombie_toast",
                                             con.ZOMBIE_HEALTH, con.ZOMBIE_ATTACK);
        zombie.move(Direction.UP, dungeon);
        if (zombie.getPosition().equals(this.getPosition())) {
            zombie.move(Direction.RIGHT, dungeon);
        }
        if (zombie.getPosition().equals(this.getPosition())) {
            zombie.move(Direction.DOWN, dungeon);
        }
        if (zombie.getPosition().equals(this.getPosition())) {
            zombie.move(Direction.LEFT, dungeon);
        }
    }

    /**
     * Destroy the Zombie Toast Spawner
     * @param dungeon
     */
    public void destroyed(DungeonMap dungeon) {
        dungeon.getMap().get(this.getPosition()).remove(this);
    }

    /**
     * Increase ticks that spawner has been around
     */
    public void increaseTick () {
        this.tick += 1;
    }
}
