package dungeonmania.collectableEntities;

import dungeonmania.util.Position;

public class Key extends CollectableItem {

    private int keyId;

    /**
     * Constructor for Key
     * 
     * @param id
     * @param position
     * @param type
     * @param keyId
     */
    public Key(String id, Position position, String type, int keyId) {
        super(id, position, type);
        this.keyId = keyId;
    }

    @Override
    public void use() {
        this.getPlayer().getInventory().remove(this);
        this.getPlayer().setHasKey(false);
    }

    /**
     * Getter for keyId
     * @return keyId
     */
    public int getKeyId() {
        return this.keyId;
    }

}
