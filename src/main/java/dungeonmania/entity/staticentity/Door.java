package dungeonmania.entity.staticentity;

import dungeonmania.util.Position;

public class Door extends StaticEntity{

    private boolean isOpen;

    public Door(Position position) {
        super(position);
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
