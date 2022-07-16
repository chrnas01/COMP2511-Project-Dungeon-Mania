package dungeonmania.DungeonMap;

import java.io.IOException;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import org.json.JSONObject;

import dungeonmania.util.*;

public final class Config {
    public final String CONFIG_NAME;

    public final int ALLY_ATTACK;
    public final int ALLY_DEFENCE;
    public final int BOMB_RADIUS;
    public final int BOW_DURABILITY;
    public final int BRIBE_AMOUNT;
    public final int BRIBE_RADIUS;
    public final int ENEMY_GOAL;
    public final int INVINCIBILITY_POTION_DURATION;
    public final int INVISIBILITY_POTION_DURATION;
    public final int MERCENARY_ATTACK;
    public final int MERCENARY_HEALTH;
    public final int PLAYER_ATTACK;
    public final int PLAYER_HEALTH;
    public final int SHIELD_DEFENCE;
    public final int SHIELD_DURABILITY;
    public final int SPIDER_ATTACK;
    public final int SPIDER_HEALTH;
    public final int SPIDER_SPAWN_RATE;
    public final int SWORD_ATTACK;
    public final int SWORD_DURABILITY;
    public final int TREASURE_GOAL;
    public final int ZOMBIE_ATTACK;
    public final int ZOMBIE_HEALTH;
    public final int ZOMBIE_SPAWN_RATE;

    public Config(int configId, String configName) {
        CONFIG_NAME = configName;
        JSONObject payload = filetoJSONObject(configName);

        ALLY_ATTACK = payload.getInt("ally_attack");
        ALLY_DEFENCE = payload.getInt("ally_defence");
        BOMB_RADIUS = payload.getInt("bomb_radius");
        BOW_DURABILITY = payload.getInt("bow_durability");
        BRIBE_AMOUNT = payload.getInt("bribe_amount");
        BRIBE_RADIUS = payload.getInt("bribe_radius");
        ENEMY_GOAL = payload.getInt("enemy_goal");
        INVINCIBILITY_POTION_DURATION = payload.getInt("invincibility_potion_duration");
        INVISIBILITY_POTION_DURATION = payload.getInt("invisibility_potion_duration");
        MERCENARY_ATTACK = payload.getInt("mercenary_attack");
        MERCENARY_HEALTH = payload.getInt("mercenary_health");
        PLAYER_ATTACK = payload.getInt("player_attack");
        PLAYER_HEALTH = payload.getInt("player_health");
        SHIELD_DEFENCE = payload.getInt("shield_defence");
        SHIELD_DURABILITY = payload.getInt("shield_durability");
        SPIDER_ATTACK = payload.getInt("spider_attack");
        SPIDER_HEALTH = payload.getInt("spider_health");
        SPIDER_SPAWN_RATE = payload.getInt("spider_spawn_rate");
        SWORD_ATTACK = payload.getInt("sword_attack");
        SWORD_DURABILITY = payload.getInt("sword_durability");
        TREASURE_GOAL = payload.getInt("treasure_goal");
        ZOMBIE_ATTACK = payload.getInt("zombie_attack");
        ZOMBIE_HEALTH = payload.getInt("zombie_health");
        ZOMBIE_SPAWN_RATE = payload.getInt("zombie_spawn_rate");
    }

    private JSONObject filetoJSONObject(String configName) {
        try {
            JSONObject payload = new JSONObject(FileLoader.loadResourceFile("/configs/" + configName + ".json"));
            return payload;
        }
        catch(IOException e) {
            JSONObject defaultPayload = new JSONObject();
            defaultPayload.put("ally_attack", 3);
            defaultPayload.put("ally_defence", 3);
            defaultPayload.put("bomb_radius", 1);
            defaultPayload.put("bow_durability", 1);
            defaultPayload.put("bribe_amount", 1);
            defaultPayload.put("bribe_radius", 1);
            defaultPayload.put("enemy_goal", 1);
            defaultPayload.put("invincibility_potion_duration", 1);
            defaultPayload.put("invisibility_potion_duration", 1);
            defaultPayload.put("mercenary_attack", 5);
            defaultPayload.put("mercenary_health", 5);
            defaultPayload.put("player_attack", 10);
            defaultPayload.put("player_health", 10);
            defaultPayload.put("shield_defence", 1);
            defaultPayload.put("shield_durability", 1);
            defaultPayload.put("spider_attack", 5);
            defaultPayload.put("spider_health", 5);
            defaultPayload.put("spider_spawn_rate", 0);
            defaultPayload.put("sword_attack", 2);
            defaultPayload.put("sword_durability", 1);
            defaultPayload.put("treasure_goal", 1);
            defaultPayload.put("zombie_attack", 5);
            defaultPayload.put("zombie_health", 5);
            defaultPayload.put("zombie_spawn_rate", 0);
            
            return defaultPayload;

        }
    }
}
   