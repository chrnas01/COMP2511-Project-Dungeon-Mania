package dungeonmania.entity.staticentity;

import dungeonmania.util.Position;

public class Door extends StaticEntity {

    private int key;
    private boolean isOpen;

    public Door(Position position, int key) {
        super(position);
        this.key = key;
    }


    public boolean isOpenByKey(int key) {
        if (this.key == key) {
            isOpen = true;
        }
        return isOpen;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
