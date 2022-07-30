package dungeonmania;

import dungeonmania.collectableEntities.*;
import dungeonmania.DungeonMap.Config;
import dungeonmania.movingEntities.*;
import dungeonmania.staticEntities.*;

import dungeonmania.util.Position;

public class EntityFactory {

    public static Entity getEntityObj(int configId, String configName, String id, Position position, String type,
            int key_id, String colour_id) {
        Config config = new Config(configId, configName);

        switch (type) {
            // Static Entities
            case "wall":
                return new Wall(id, position, type);
            case "exit":
                return new Exit(id, position, type);
            case "boulder":
                return new Boulder(id, position, type);
            case "switch":
                return new FloorSwitch(id, position, type);
            case "door":
                return new Door(id, position, type, key_id);
            case "portal":
                return new Portal(id, position, type, colour_id);
            case "zombie_toast_spawner":
                return new ZombieToastSpawner(id, position, type);

            // Moving Entities
            case "spider":
                return new Spider(id, position, type, config.SPIDER_HEALTH, config.SPIDER_ATTACK);
            case "zombie_toast":
                return new ZombieToast(id, position, type, config.ZOMBIE_HEALTH, config.ZOMBIE_ATTACK);
            case "mercenary":
                return new Mercenary(id, position, type, config.MERCENARY_HEALTH, config.MERCENARY_ATTACK,
                        config.BRIBE_AMOUNT);
            case "player":
                return new Player(id, position, type, config.PLAYER_HEALTH, config.PLAYER_ATTACK);

            // Bosses M3
            case "assassin":
                return new Assassin(id, position, type, config.ASSASSIN_HEALTH, config.ASSASSIN_ATTACK, config.ASSASSIN_BRIBE_AMOUNT, 
                                    config.ASSASSIN_BRIBE_FAIL_RATE, config.ASSASSIN_RECON_RADIUS);
            case "hydra":
                return new Hydra(id, position, type, config.HYDRA_ATTACK, config.HYDRA_HEALTH, 
                                    config.HYDRA_HEALTH_INCREASE_AMOUNT, config.HYDRA_HEALTH_INCREASE_RATE);

            // Collectable + Buildable Entities
            case "treasure":
                return new Treasure(id, position, type);
            case "key":
                return new Key(id, position, type, key_id);
            case "invincibility_potion":
                return new InvincibilityPotion(id, position, type, config.INVINCIBILITY_POTION_DURATION);
            case "invisibility_potion":
                return new InvisibilityPotion(id, position, type, config.INVISIBILITY_POTION_DURATION);
            case "wood":
                return new Wood(id, position, type);
            case "arrow":
                return new Arrows(id, position, type);
            case "bomb":
                return new Bomb(id, position, type, config.BOMB_RADIUS);
            case "sword":
                return new Sword(id, position, type, config.SWORD_DURABILITY, config.SWORD_ATTACK);
            case "bow":
                return new Bow(id, position, type, config.BOW_DURABILITY);
            case "shield":
                return new Shield(id, position, type, config.SHIELD_DURABILITY, config.SHIELD_DEFENCE);

            default:
                return null;
        }
    }
}
