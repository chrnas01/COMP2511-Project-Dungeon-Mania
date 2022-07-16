package dungeonmania.staticEntities;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Boulder extends StaticEntity {

    /**
     * Constructor for Boulder
     * @param id
     * @param position
     * @param type
     */
    public Boulder (String id, Position position, String type) {
        super(id, position, type);
    }

    public void moveByDirection(Direction direction) {
        setPosition(getPosition().translateBy(direction));
    }
}
