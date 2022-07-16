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
import dungeonmania.util.*;

public class DungeonMap {
    private String dungeonId;
    private String dungeonName;
    private Map<Position, List<Entity>> entities = new HashMap<Position, List<Entity>>();
    private String goals;

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

                
                if (!entities.containsKey(position)) {
                    entities.put(position, new ArrayList<Entity>());
                }

                entities.get(position).add(EntityFactory.getEntityObj(id, position, type));
            }

            // for 

            // entities.put(new Position(1, 1), "Something else");
            // System.out.println(entities.get(new Position(1, 1)));
        }
        catch(IOException e) {
            System.exit(0);
        }
    }
}
