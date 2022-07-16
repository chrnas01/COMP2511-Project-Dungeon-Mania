package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public class Key extends CollectableItem {

    private int key_id;

    /**
     * Constructor for Key
     * @param id
     * @param position
     * @param type
     * @param key_id
     */
    public Key(String id, Position position, String type, int key_id) {
        super(id, position, type);
        this.key_id = key_id;
    }

    @Override
    public void use() {
        this.getPlayer().getInventory().remove(this);
    }

    @Override
    public String toString() {
        return super.toString() + ", key_id: " + key_id;
    }

    public int getKeyId() {
        return this.key_id;
    }
    
}
