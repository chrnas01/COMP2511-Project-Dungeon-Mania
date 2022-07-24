package dungeonmania.util;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import dungeonmania.staticEntities.Boulder;
import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.Entity;

public class RandomUtil {
    
    /**
     * Given a dungeon, return a random position that doesnt contain a Boulder
     * @return
     */
    public static Position getRandomPosition(DungeonMap dungeon) {
        Map <Position, List<Entity>> map = dungeon.getMap();
        
        List<Position> keysAsList = new ArrayList<Position>(map.keySet());
        int i = new Random().nextInt(keysAsList.size());
        Position key = keysAsList.get(i);

        boolean positionContainsBoulder = dungeon.getMap().get(key).stream().filter((entity) -> entity instanceof Boulder).collect(Collectors.toList()).isEmpty();

        if (!positionContainsBoulder) {
            getRandomPosition(dungeon);
        }
        
        return key;
    }

    /**
     * Given a dungeon and position in that dungeon, check if that position contains any invalid entities
     * @param dungeon
     * @param pos
     * @param invalidEntities
     * @return if no invalid entities are present the square is spawnable (return true) else return false
     */
    public boolean isSquareSpawnable(DungeonMap dungeon, Position pos, List<Entity> invalidEntities) {
        List<Entity> entityList = dungeon.getMap().get(pos);
        
        for (Entity entity : entityList) {
            for (Entity invEntity : invalidEntities) {
                if (entity.getClass() == invEntity.getClass()) {
                    return false;
                }
            }
        }
        
        return true;
    }
}
