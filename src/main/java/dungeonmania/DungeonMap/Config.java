package dungeonmania.DungeonMap;

import java.io.IOException;

import org.json.JSONObject;

import dungeonmania.util.*;

public final class Config {
    public final String CONFIG_NAME;
    public final int CONFIG_ID;

    // M2 Fields
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

    // M3 Fields
    public final int ASSASSIN_ATTACK;
    public final int ASSASSIN_BRIBE_AMOUNT;     
    public final double ASSASSIN_BRIBE_FAIL_RATE;           // Probability bribing assain fails and the assassin remains hostile
    public final int ASSASSIN_HEALTH;
    public final int ASSASSIN_RECON_RADIUS;
    public final int HYDRA_ATTACK;
    public final int HYDRA_HEALTH;
    public final double HYDRA_HEALTH_INCREASE_RATE;         // Probability hydra heals instead of taking damage
    public final int HYDRA_HEALTH_INCREASE_AMOUNT;
    public final int MIND_CONTROL_DURATION;
    public final int MIDNIGHT_ARMOUR_ATTACK;
    public final int MIDNIGHT_ARMOUR_DEFENCE;

    public Config(int configId, String configName) {
        CONFIG_NAME = configName;
        CONFIG_ID = configId;
        JSONObject payload = filetoJSONObject(configName);

        ALLY_ATTACK = payload.optInt("ally_attack", 3);
        ALLY_DEFENCE = payload.optInt("ally_defence", 3);
        ASSASSIN_ATTACK = payload.optInt("assassin_attack", 10);
        ASSASSIN_BRIBE_AMOUNT = payload.optInt("assassin_bribe_amount", 1);
        ASSASSIN_BRIBE_FAIL_RATE = payload.optDouble("assassin_bribe_fail_rate", 0.3);
        ASSASSIN_HEALTH = payload.optInt("assassin_health", 10);
        ASSASSIN_RECON_RADIUS = payload.optInt("assassin_recon_radius", 5);
        BOMB_RADIUS = payload.optInt("bomb_radius", 3);
        BOW_DURABILITY = payload.optInt("bow_durability", 3);
        BRIBE_AMOUNT = payload.optInt("bribe_amount", 1);
        BRIBE_RADIUS = payload.optInt("bribe_radius", 1);
        ENEMY_GOAL = payload.optInt("enemy_goal", 2);
        HYDRA_ATTACK = payload.optInt("hydra_attack", 10);
        HYDRA_HEALTH = payload.optInt("hydra_health", 10);
        HYDRA_HEALTH_INCREASE_RATE = payload.optDouble("hydra_health_increase_rate", 0.5);
        HYDRA_HEALTH_INCREASE_AMOUNT = payload.optInt("hydra_health_increase_amount", 1);
        INVINCIBILITY_POTION_DURATION = payload.optInt("invincibility_potion_duration", 5);
        INVISIBILITY_POTION_DURATION = payload.optInt("invisibility_potion_duration", 5);
        MERCENARY_ATTACK = payload.optInt("mercenary_attack", 5);
        MERCENARY_HEALTH = payload.optInt("mercenary_health", 5);
        MIDNIGHT_ARMOUR_ATTACK = payload.optInt("midnight_armour_attack", 2);
        MIDNIGHT_ARMOUR_DEFENCE = payload.optInt("midnight_armour_defence", 2);
        MIND_CONTROL_DURATION = payload.optInt("mind_control_duration", 5);
        PLAYER_ATTACK = payload.optInt("player_attack", 5);
        PLAYER_HEALTH = payload.optInt("player_health", 10);
        SHIELD_DEFENCE = payload.optInt("shield_defence", 3);
        SHIELD_DURABILITY = payload.optInt("shield_durability", 3);
        SPIDER_ATTACK = payload.optInt("spider_attack", 5);
        SPIDER_HEALTH = payload.optInt("spider_health", 5);
        SPIDER_SPAWN_RATE = payload.optInt("spider_spawn_rate", 8);
        SWORD_ATTACK = payload.optInt("sword_attack", 2);
        SWORD_DURABILITY = payload.optInt("sword_durability", 3);
        TREASURE_GOAL = payload.optInt("treasure_goal", 1);
        ZOMBIE_ATTACK = payload.optInt("zombie_attack", 5);
        ZOMBIE_HEALTH = payload.optInt("zombie_health", 5);
        ZOMBIE_SPAWN_RATE = payload.optInt("zombie_spawn_rate", 5);
    }

    /**
     * Given a JSON file return the contents, if an IO Error is thrown invoke default parameters
     * @param configName
     * @return JSONObject
     */
    private JSONObject filetoJSONObject(String configName) {
        try {
            JSONObject payload = new JSONObject(FileLoader.loadResourceFile("/configs/" + configName + ".json"));
            return payload;
        }
        catch(IOException e) {
            JSONObject defaultPayload = new JSONObject();
            return defaultPayload;

        }
    }
}
   