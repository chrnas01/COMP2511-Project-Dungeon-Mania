package dungeonmania.entity.staticentity;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Boulder extends StaticEntity {
    public Boulder(Position position) {
        super(position);
    }

    public void moveByDirection(Direction direction) {
        setPosition(getPosition().translateBy(direction));
    }
}
