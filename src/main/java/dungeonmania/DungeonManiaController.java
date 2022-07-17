package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.movingEntities.Mercenary;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.staticEntities.ZombieToastSpawner;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.GoalUtil;
import dungeonmania.util.Position;

public class DungeonManiaController {

    public DungeonMap dungeon;
    public static int uniqueId = 1;

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    /**
     * /dungeons
     */
    public static List<String> dungeons() {
        return FileLoader.listFileNamesInResourceDirectory("dungeons");
    }

    /**
     * /configs
     */
    public static List<String> configs() {
        return FileLoader.listFileNamesInResourceDirectory("configs");
    }

    /**
     * /game/new
     */
    public DungeonResponse newGame(String dungeonName, String configName) throws IllegalArgumentException {
        if (!dungeons().contains(dungeonName) || !configs().contains(configName)) {
            throw new IllegalArgumentException("Inputted names is/are invalid.");
        }

        String dungeonId = Integer.toString(dungeons().indexOf(dungeonName));
        int configId = configs().indexOf(configName);

        this.dungeon = new DungeonMap(dungeonId, dungeonName, configId, configName);
        Map <Position, List<Entity>> dungeonMap = this.dungeon.getMap();

        // Loops through every position in dungeonMap and gathers a list of every entity at every position.
        List <EntityResponse> entities = new ArrayList<EntityResponse>();
        dungeonMap.forEach((pos, entityList) -> {
            entityList.forEach((entity) -> {
                boolean isInteractable = entity instanceof Mercenary || entity instanceof ZombieToastSpawner;
                entities.add(new EntityResponse(entity.getId(), entity.getType(), entity.getPosition(), isInteractable));
            });
        });

        // Player inventory is initially empty
        List <ItemResponse> inventory = new ArrayList<ItemResponse>();

        // Player initially is not in any battles
        List <BattleResponse> battles = new ArrayList<BattleResponse>();

        // Given player inventory is initially empty, player initially has no buildables
        List <String> buildables = new ArrayList<String>();

        String goals = GoalUtil.goalToString(this.dungeon.getGoal(), dungeon);

        return new DungeonResponse(dungeonId, dungeonName, entities, inventory, battles, buildables, goals);
    }

    /**
     * /game/dungeonResponseModel
     */
    public DungeonResponse getDungeonResponseModel() {
        return null;
    }

    /**
     * /game/tick/item
     */
    public DungeonResponse tick(String itemUsedId) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        return null;
    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        return null;
    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return null;
    }
}
