package dungeonmania.staticEntities;

import dungeonmania.util.Position;

public class FloorSwitch extends StaticEntity {
    private boolean isTouch;

    public FloorSwitch (String id, Position position, String type) {
        super(id, position, type);
    }

    public boolean isTouch() {
        return isTouch;
    }

    public void setTouch(boolean touch) {
        isTouch = touch;
    }
}
