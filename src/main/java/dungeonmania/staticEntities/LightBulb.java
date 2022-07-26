package dungeonmania.staticEntities;

import dungeonmania.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LightBulb extends StaticEntity {

    public LightBulb(String id, Position position, String type) {
        super(id, position, type);
    }

    public boolean isLight(Map<Position, List<Entity>> map) {
        List<Position> allPos = map.keySet().stream().collect(Collectors.toList());
        for (Direction direction : Direction.values()) {
            boolean isLight = false;
            Position nextPos = this.getPosition().translateBy(direction);
            while (allPos.contains(nextPos)) {
                if (map.get(nextPos).stream().filter(entity -> entity instanceof Wire).collect(Collectors.toList()).isEmpty()) {
//                    if (map.get(nextPos).stream().filter(entity -> entity instanceof SwitchDoor).collect(Collectors.toList()).isEmpty()) {
//                        isLight = true;
//                    }
                    break;
                } else {
                    nextPos = this.getPosition().translateBy(direction);
                }
            }
            if (isLight) {
                return true;
            }
        }
        return false;
    }
}
