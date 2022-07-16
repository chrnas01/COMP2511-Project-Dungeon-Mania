package dungeonmania.staticEntities;

import dungeonmania.util.Position;

public class Door extends StaticEntity {

    private int key_id;
    private boolean open;

    /**
     * Constructor for Door
     * @param id
     * @param position
     * @param type
     * @param key_id
     */
    public Door (String id, Position position, String type, int key_id) {
        super(id, position, type);
        this.key_id = key_id;
    }

    @Override
    public String toString() {
        return super.toString() + ", key_id: " + key_id;
    }

    public int getKeyId() {
        return this.key_id;
    }

    public boolean getOpen() {
        return this.open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
