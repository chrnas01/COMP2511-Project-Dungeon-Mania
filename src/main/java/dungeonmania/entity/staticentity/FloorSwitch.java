package dungeonmania.entity.staticentity;

import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntity {
    private boolean isTouch;

    public FloorSwitch(Position position) {
        super(position);
    }

    public boolean isTouch() {
        return isTouch;
    }

    public void setTouch(boolean touch) {
        isTouch = touch;
    }
}
