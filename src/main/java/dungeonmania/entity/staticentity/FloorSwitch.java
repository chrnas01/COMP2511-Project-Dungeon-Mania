package dungeonmania.entity.staticentity;

import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntity {

    public FloorSwitch(Position position) {
        super(position);
    }

    public boolean isTouchByPosition(Position otherPosition) {
        return otherPosition.equals(getPosition());
    }

}
