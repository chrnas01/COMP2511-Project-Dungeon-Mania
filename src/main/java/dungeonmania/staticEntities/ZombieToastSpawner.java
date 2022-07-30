package dungeonmania.staticEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.DungeonMap.Config;
import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.movingEntities.ZombieToast;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ZombieToastSpawner extends StaticEntity {

    private List<Position> cardinallyAdjacentSquares;
    
    /**
     * Constructor for ZombieToastSpawner
     * @param id
     * @param position
     * @param type
     */
    public ZombieToastSpawner (String id, Position position, String type) {
        super(id, position, type);
    
        this.cardinallyAdjacentSquares = getCardinallyAdjacentSquares();
    }

    /**
    * Return Adjacent positions in an array list with the following element positions:
    *   0
    * 3 p 1
    *   2 
    * @return
    */
    public List<Position> getCardinallyAdjacentSquares() {
        List<Position> caSquares = new ArrayList<Position>();

        caSquares.add(this.getPosition().translateBy(Direction.UP));
        caSquares.add(this.getPosition().translateBy(Direction.RIGHT));
        caSquares.add(this.getPosition().translateBy(Direction.DOWN));
        caSquares.add(this.getPosition().translateBy(Direction.LEFT));

        return caSquares;
    }

    /**
     * Spawn a zombie toast
     * @param dungeon
     */
    public void generateZombieToast(DungeonMap dungeon, int tickCounter) {
        Config config = dungeon.getConfig();
        
        if (config.ZOMBIE_SPAWN_RATE <= 0) {
            return;
        }
        else if (tickCounter % config.ZOMBIE_SPAWN_RATE != 0) {
            return;
        }

        // Makes sure all cardinally adjacent squares exist in the dungeon
        this.cardinallyAdjacentSquares.forEach((pos) -> {
            dungeon.addPosition(pos);
        });

        for (Position pos : this.cardinallyAdjacentSquares) {
            boolean spawnable = dungeon.getMap().get(pos).stream()
                                .filter((entity) -> entity instanceof ZombieToast || entity instanceof Wall || entity instanceof Boulder || entity.getType().equals("door"))
                                .collect(Collectors.toList()).isEmpty();

            if (spawnable) {
                dungeon.addEntity(pos, new ZombieToast("toast" + tickCounter, pos, "zombie_toast", config.ZOMBIE_HEALTH, config.ZOMBIE_ATTACK));
                return;
            }
        }
    }

    /**
     * Destroy the Zombie Toast Spawner
     * @param dungeon
     */
    public void destroyed(DungeonMap dungeon) {
        dungeon.getMap().get(this.getPosition()).remove(this);
    }

}
