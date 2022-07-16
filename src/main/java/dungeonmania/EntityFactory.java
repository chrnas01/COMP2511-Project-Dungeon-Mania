package dungeonmania;

import dungeonmania.staticEntities.*;
import dungeonmania.movingEntities.*;
import dungeonmania.collectableEntities.*;

import dungeonmania.util.Position;

public class EntityFactory {
    
    public static Entity getEntityObj(String id, Position position, String type) {
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
                return new Door(id, position, type);
            case "portal": 
                return new Portal(id, position, type);
            case "zombie_toast_spawner":
                return new ZombieToastSpawner(id, position, type);
            
            // Moving Entities
            case "spider": 
                return new Spider(id, position, type);
            case "zombie_toast":
                return new Zombie_Toast(id, position, type);
            case "mercenary":
                return new Mercenary(id, position, type);
            
            // Collectable Entities 
            case "tresure":
                return new Treasure(id, position, type);
            case "key":
                return new Key(id, position, type);
            case "invincibility_potion":
                return new InvincibilityPotion(id, position, type);
            case "invisibility_potion":
                return new InvisibilityPotion(id, position, type);
            case "wood":
                return new Wood(id, position, type);
            case "arrows":
                return new Arrows(id, position, type);
            case "bomb":
                return new Bomb(id, position, type);
            case "sword":
                return new Sword(id, position, type);
            
            default: 
                return null;
        }
    }
}
