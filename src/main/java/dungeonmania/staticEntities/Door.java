package dungeonmania.staticEntities;

import dungeonmania.util.Position;

public class Door extends StaticEntity{

    private boolean isOpen;

    public Door (String id, Position position, String type) {
        super(id, position, type);
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
