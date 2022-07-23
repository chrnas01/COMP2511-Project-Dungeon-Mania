package dungeonmania;

import dungeonmania.movingEntities.Player;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.collectableEntities.*;

public class Inventory {

    private Player player;
    private List<CollectableEntity> inv = new ArrayList<>();

    /**
     * Constructor for Inventory
     * 
     * @param player
     */
    public Inventory(Player player) {
        this.player = player;
    }

    /**
     * Add a collectable entity to the inventory
     * 
     * @param collectable
     * @param player
     */
    public void pickup(CollectableEntity collectable, Player player) {
        this.inv.add(collectable);
        collectable.setPlayer(player);
    }

    /**
     * Get item with given id
     * 
     * @param id
     * @return the collectable entity or null if not in inventory
     */
    public CollectableEntity getItem(String id) {
        for (CollectableEntity inv_item : inv) {
            if (inv_item.getId().equals(id)) {
                return inv_item;
            }
        }
        return null;
    }

    /**
     * Use the item with given id.
     * 
     * @param id
     */
    public void useItem(String id) {
        for (CollectableEntity inv_item : inv) {
            if (inv_item.getId().equals(id)) {
                inv_item.use();
                return;
            }
        }
    }

    /**
     * Find a key with given keyid
     * 
     * @param keyId
     * @return the key or null if not exist
     */
    public CollectableEntity findKey(int keyId) {
        for (CollectableEntity entity : inv) {
            if (entity instanceof Key && ((Key) entity).getKeyId() == keyId) {
                return entity;
            }
        }
        return null;
    }

    /**
     * Count wood in inventory
     * 
     * @return number of wood in inventory
     */
    public int countWood() {
        int counter = 0;
        for (CollectableEntity entity : inv) {
            if (entity instanceof Wood) {
                counter += 1;
            }
        }
        return counter;
    }

    /**
     * Count arrows in inventory
     * 
     * @return number of arrows in inventory
     */
    public int countArrows() {
        int counter = 0;
        for (CollectableEntity entity : inv) {
            if (entity instanceof Arrows) {
                counter += 1;
            }
        }
        return counter;
    }

    /**
     * Count keys in inventory
     * 
     * @return number of keys in inventory
     */
    public int countTreasure() {
        int counter = 0;
        for (CollectableEntity entity : inv) {
            if (entity instanceof Treasure) {
                counter += 1;
            }
        }
        return counter;
    }

    /**
     * Remove materials in building a bow
     */
    public void bowMaterials() {
        for (CollectableEntity inv_item : inv) {
            if (inv_item.getType().equals("wood")) {
                inv_item.use();
                break;
            }
        }
        int arrows = 0;
        while (arrows < 3) {
            for (CollectableEntity inv_item : inv) {
                if (inv_item.getType().equals("arrow")) {
                    inv_item.use();
                    arrows += 1;
                    break;
                }
            }
        }
    }

    /**
     * Remove materials in building a shield
     */
    public void shieldMaterials() {
        int wood = 0;
        while (wood < 2) {
            for (CollectableEntity inv_item : inv) {
                if (inv_item.getType().equals("wood")) {
                    inv_item.use();
                    wood += 1;
                    break;
                }
            }
        }
        for (CollectableEntity inv_item : inv) {
            if (inv_item.getType().equals("key") || inv_item.getType().equals("treasure")) {
                inv_item.use();
                break;
            }
        }
    }

    public List<CollectableEntity> getInventory() {
        return inv;
    }

    public Player getPlayer() {
        return this.player;
    }

}
