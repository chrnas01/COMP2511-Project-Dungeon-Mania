package dungeonmania.staticEntities;

import dungeonmania.util.Position;

public class Door extends StaticEntity {

    private int keyId;
    private boolean open;

    /**
     * Constructor for Door
     * 
     * @param id
     * @param position
     * @param type
     * @param keyId
     */
    public Door(String id, Position position, String type, int keyId) {
        super(id, position, type);
        this.keyId = keyId;
    }

    /**
     * Getter for keyId
     * @return keyId
     */
    public int getKeyId() {
        return this.keyId;
    }

    /**
     * Getter for getOpen
     * @return true if door open false otherwise
     */
    public boolean getOpen() {
        return this.open;
    }

    /**
     * Setter for getOpen
     * @param open - true if opening door
     */
    public void setOpen(boolean open) {
        this.open = open;
        this.setType("door_open");
    }
}
