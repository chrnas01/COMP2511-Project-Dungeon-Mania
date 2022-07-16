package dungeonmania.DungeonMap;

import java.lang.*;
import org.json.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import dungeonmania.Entity;
import dungeonmania.EntityFactory;
import dungeonmania.Goals.*;
import dungeonmania.Goals.GoalFactory;
import dungeonmania.util.*;

public class DungeonMap {
    private String dungeonId;
    private String dungeonName;
    private Map<Position, List<Entity>> entities = new HashMap<Position, List<Entity>>();
    private Goal goals;

    public DungeonMap(String dungeonId, String dungeonName) {
        this.dungeonId = dungeonId;
        this.dungeonName = dungeonName;

        try {
            String payload = FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json");

            JSONArray entitiesPayload = new JSONObject(payload).getJSONArray("entities");
            JSONObject goalsPayload = new JSONObject(payload).getJSONObject("goal-condition");

            // Loops through all entities in JSON file and adds them to our data structure 
            for (int i = 0; i < entitiesPayload.length(); i++) {
                String id = String.valueOf(i);
                Position position = new Position(entitiesPayload.getJSONObject(i).getInt("x"), entitiesPayload.getJSONObject(i).getInt("y"));
                String type = entitiesPayload.getJSONObject(i).getString("type");
                int key_id = entitiesPayload.getJSONObject(i).has("key") ? entitiesPayload.getJSONObject(i).getInt("key") : -1;
                String colour_id = entitiesPayload.getJSONObject(i).has("colour") ? entitiesPayload.getJSONObject(i).getString("colour") : null;

                if (!entities.containsKey(position)) {
                    entities.put(position, new ArrayList<Entity>());
                }

                entities.get(position).add(EntityFactory.getEntityObj(id, position, type, key_id, colour_id));
                System.out.println(EntityFactory.getEntityObj(id, position, type, key_id, colour_id));
            }

            this.goals = jsonToGoalObject(goalsPayload);
        }
        catch(IOException e) {
            System.exit(0);
        }
    }

    private static Goal jsonToGoalObject (JSONObject payload) {
        if (payload.getString("goal").equals("AND")) {
            ANDGoal goal = (ANDGoal) GoalFactory.getGoal("AND");
            for (int i = 0; i < payload.getJSONArray("subgoals").length(); i++) {
                JSONObject subGoal= payload.getJSONArray("subgoals").getJSONObject(i);
                goal.addGoal(jsonToGoalObject(subGoal));
            }

            return goal;
        } 
        else if (payload.getString("goal").equals("OR")) {
            ORGoal goal = (ORGoal) GoalFactory.getGoal("OR");
            for (int i = 0; i < payload.getJSONArray("subgoals").length(); i++) {
                JSONObject subGoal= payload.getJSONArray("subgoals").getJSONObject(i);
                goal.addGoal(jsonToGoalObject(subGoal));
            }

            return goal;
        } 
        else {
            return GoalFactory.getGoal(payload.getString("goal"));
        }
    }

    public Map<Position, List<Entity>> getMap() {
        return this.entities;
    }

    /**
     * Add a new coordinate to the dungeon map. Only ever called when new coordinate added
     * @param position
     */
    public void addPosition(Position position) {
        this.entities.put(position, new ArrayList<Entity>());
    }

    /**
     * Update the position of an entity
     * @param previous
     * @param next
     * @param entity
     */
    public void moveEntity(Position previous, Position next, Entity entity) {
        this.entities.get(previous).remove(entity);
        this.entities.get(next).add(entity);
    }

    public void removeCollectable(Position position, CollectableEntity entity) {
        this.entities.get(position).remove(entity);
    }
}
