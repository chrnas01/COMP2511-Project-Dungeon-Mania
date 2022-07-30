package dungeonmania;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dungeonmania.Battle.*;
import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.movingEntities.*;
import dungeonmania.collectableEntities.*;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.staticEntities.ZombieToastSpawner;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.GoalUtil;
import dungeonmania.util.Position;

public class DungeonManiaController {

    private DungeonMap dungeon;
    private int tickCounter = 0;
    private Map <String, DungeonMap> savedGames = new HashMap<String, DungeonMap>();

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
        this.tickCounter = 0;

        if (!dungeons().contains(dungeonName) || !configs().contains(configName)) {
            throw new IllegalArgumentException("Inputted names is/are invalid.");
        }

        String dungeonId = Integer.toString(dungeons().indexOf(dungeonName));
        int configId = configs().indexOf(configName);
        this.dungeon = new DungeonMap(dungeonId, dungeonName, configId, configName);

        return getDungeonResponseModel();
    }

    /**
     * /game/dungeonResponseModel
     */
    public DungeonResponse getDungeonResponseModel() {
        Player player = dungeon.getPlayer();

        String dungeonId = dungeon.getDungeonId();
        String dungeonName = dungeon.getDungeonName();
        
        List<EntityResponse> entities = new ArrayList<EntityResponse>();
        this.dungeon.getMap().forEach((pos, entityList) -> {
            entityList.forEach((entity) -> {
                if (entity instanceof Player && ((Player) entity).getHealth() <= 0) {
                    // return is the same as continue in forEach statement
                    return;
                }
                
                boolean isInteractable = entity instanceof Mercenary || entity instanceof ZombieToastSpawner;
                entities.add(
                        new EntityResponse(entity.getId(), entity.getType(), entity.getPosition(), isInteractable));
            });
        });

        List<ItemResponse> inventory = new ArrayList<ItemResponse>();
        for (CollectableEntity entity : player.getInventory()) {
            inventory.add(new ItemResponse(entity.getId(), entity.getType()));
        }

        List<BattleResponse> battles = new ArrayList<BattleResponse>();
        for (Battle battle : dungeon.getBattles()) {
            String enemy = battle.getEnemy().getType();
            double initialPlayerHealth = battle.getInitialPlayerHealth();
            double initialEnemyHealth = battle.getInitialEnemyHealth();

            List<RoundResponse> rounds = new ArrayList<RoundResponse>();
            battle.getRounds().forEach((round) -> {
                double deltaPlayerHealth = round.getDeltaPlayerHealth();
                double deltaEnemyHealth = round.getDeltaEnemyHealth();
                List<ItemResponse> weaponryUsed = new ArrayList<ItemResponse>();
                round.getWeaponryUsed().forEach((weapon) -> {
                    weaponryUsed.add(new ItemResponse(weapon.getId(), weapon.getType()));
                });

                rounds.add(new RoundResponse(deltaPlayerHealth, deltaEnemyHealth, weaponryUsed));
            });

            battles.add(new BattleResponse(enemy, rounds, initialPlayerHealth, initialEnemyHealth));
        }

        List<String> buildables = new ArrayList<String>();
        if (player.canBuildBow()) {
            buildables.add("bow");
        }
        if (player.canBuildShield()) {
            buildables.add("shield");
        }
        if (player.canBuildSpectre()) {
            buildables.add("sceptre");
        }
        if (player.canBuildArmour() && !dungeon.getZombiePresence()) {
            buildables.add("midnight_armour");
        }

        String goals = GoalUtil.goalToString(this.dungeon.getGoal(), dungeon);

        return new DungeonResponse(dungeonId, dungeonName, entities, inventory, battles, buildables, goals);
    }

    /**
     * /game/tick/item
     */
    public DungeonResponse tick(String itemUsedId) throws IllegalArgumentException, InvalidActionException {

        Player player = dungeon.getPlayer();
        player.use(dungeon, itemUsedId);

        dungeon.blowBombs();
        dungeon.moveAllMercenaries();
        dungeon.moveAllSpiders();
        dungeon.moveallZombies();
        dungeon.handleBattle();

        return getDungeonResponseModel();
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        this.tickCounter++;

        // Move player
        Player player = this.dungeon.getPlayer();
        player.move(movementDirection, this.dungeon);

        // Check if battle is applicable
        dungeon.handleBattle();

        // If the player puts the bomb down it blows everything within radius
        // This should happen before players move
        dungeon.blowBombs();

        // Move enemy entities
        dungeon.moveAllMercenaries();
        dungeon.moveAllSpiders();
        dungeon.moveallZombies();

        // Entities move before potions tick (Assumption)
        player.tickPotions();

        // Spawn necessary mobs
        dungeon.spawnSpider(tickCounter);
        dungeon.spawnZombie(tickCounter);

        // Check if battle is applicable
        dungeon.handleBattle();

        return getDungeonResponseModel();
    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        Player player = dungeon.getPlayer();
        player.build(dungeon, buildable);

        return getDungeonResponseModel();
    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        Player player = dungeon.getPlayer();
        Entity interact = null;
        for (List<Entity> entities : dungeon.getMap().values()) {
            for (Entity entity : entities) {
                if (entity.getId().equals(entityId)
                        && (!(entity instanceof Mercenary) && !(entity instanceof ZombieToastSpawner))) {
                    throw new IllegalArgumentException("Entity not interactable");
                } else if (entity.getId().equals(entityId)) {
                    interact = entity;
                    break;
                }
            }
        }
        if (interact == null) {
            throw new IllegalArgumentException("Not a valid Entity Id");
        }

        if (interact instanceof Mercenary && ((Mercenary) interact).getIsHostile()) {
            Mercenary merc = (Mercenary) interact;
            Sceptre scep = (Sceptre) player.getInvClass().getItemtype("sceptre");
            Position dist = Position.calculatePositionBetween(interact.getPosition(), player.getPosition());
            if (Math.abs(dist.getX()) + Math.abs(dist.getY()) > player.getBribeRadius() && !player.getHasSceptre()) {
                throw new InvalidActionException("Player not within bribing radius");
            } else if (Math.abs(dist.getX()) + Math.abs(dist.getY()) > player.getBribeRadius()) {
                player.brainwash(merc, scep.getDuration());
            } else if (player.getInvClass().countItem("treasure") < merc.getBribeAmount() && !player.getHasSceptre()) {
                throw new InvalidActionException("Unable to bribe");
            } else if (player.getInvClass().countItem("treasure") < merc.getBribeAmount()) {
                player.brainwash(merc, scep.getDuration());
            } else {
                player.bribe(merc);
            }
        }

        if (interact instanceof ZombieToastSpawner) {
            if (!Position.isAdjacent(interact.getPosition(), player.getPosition())) {
                throw new InvalidActionException("Not adjacent to spawner");
            }
            ZombieToastSpawner spawner = (ZombieToastSpawner) interact;
            player.destroy(spawner, dungeon);
        }

        return getDungeonResponseModel();
    }

    /**
     * /game/save
     */
    public DungeonResponse saveGame(String name) {
        savedGames.put(name, this.dungeon);
        return getDungeonResponseModel();
    }

    /**
     * /game/load
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        if (!allGames().contains(name)) {
            throw new IllegalArgumentException("Game does not exist!");
        }

        this.dungeon = savedGames.get(name);
        return getDungeonResponseModel();
    }

    /**
     * /games/all
     */
    public List<String> allGames() {
        return new ArrayList<String>(savedGames.keySet());

    }

}
