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
}
