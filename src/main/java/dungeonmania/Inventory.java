package dungeonmania;

import dungeonmania.movingEntities.Player;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.DungeonMap.DungeonMap;
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
     * Place and use bomb with given id.
     * 
     * @param id
     * @param dungeon
     */
    public void placeBomb(String id, DungeonMap dungeon) {
        for (CollectableEntity inv_item : inv) {
            if (inv_item.getId().equals(id)) {
                ((Bomb) inv_item).place(dungeon);
                return;
            }
        }
    }

    /**
     * Remove an item from the inventory with the given id.
     * 
     * @param id
     */
    public void removeItem(String id) {
        for (CollectableEntity inv_item : inv) {
            if (inv_item.getId().equals(id)) {
                inv.remove(inv_item);
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
     * Count how many of item type are in inventory
     * 
     * @param type
     * @return number of items of that type
     */
    public int countItem(String type) {
        int counter = 0;
        for (CollectableEntity entity : inv) {
            if (entity.getType().equals(type)) {
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
