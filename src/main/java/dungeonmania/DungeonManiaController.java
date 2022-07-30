package dungeonmania;

import java.util.ArrayList;
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
    private DungeonResponse response;

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
        Map<Position, List<Entity>> dungeonMap = this.dungeon.getMap();

        // Loops through every position in dungeonMap and gathers a list of every entity
        // at every position.
        List<EntityResponse> entities = new ArrayList<EntityResponse>();
        dungeonMap.forEach((pos, entityList) -> {
            entityList.forEach((entity) -> {
                boolean isInteractable = entity instanceof Mercenary || entity instanceof ZombieToastSpawner;
                entities.add(
                        new EntityResponse(entity.getId(), entity.getType(), entity.getPosition(), isInteractable));
            });
        });

        // Player inventory is initially empty
        List<ItemResponse> inventory = new ArrayList<ItemResponse>();

        // Player initially is not in any battles
        List<BattleResponse> battles = new ArrayList<BattleResponse>();

        // Given player inventory is initially empty, player initially has no buildables
        List<String> buildables = new ArrayList<String>();

        String goals = GoalUtil.goalToString(this.dungeon.getGoal(), dungeon);

        this.response = new DungeonResponse(dungeonId, dungeonName, entities, inventory, battles, buildables,
                goals);
        return this.response;
    }

    /**
     * /game/dungeonResponseModel
     */
    public DungeonResponse getDungeonResponseModel() {
        return this.response;
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

        String dungeonId = dungeon.getDungeonId();
        String dungeonName = dungeon.getDungeonName();

        Map<Position, List<Entity>> dungeonMap = this.dungeon.getMap();
        List<EntityResponse> entities = new ArrayList<EntityResponse>();
        dungeonMap.forEach((pos, entityList) -> {
            entityList.forEach((entity) -> {
                boolean isInteractable = entity instanceof Mercenary || entity instanceof ZombieToastSpawner;
                entities.add(
                        new EntityResponse(entity.getId(), entity.getType(), entity.getPosition(), isInteractable));
            });
        });

        List<ItemResponse> inventory = new ArrayList<ItemResponse>();
        for (CollectableEntity entity : player.getInventory()) {
            inventory.add(new ItemResponse(entity.getId(), entity.getType()));
        }

        // Battles not implemented, so will not be able to add any
        List<BattleResponse> battles = this.response.getBattles();

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

        this.response = new DungeonResponse(dungeonId, dungeonName, entities, inventory, battles, buildables,
                goals);
        return this.response;
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        this.tickCounter++;

        String dungeonId = this.dungeon.getDungeonId();
        String dungeonName = this.dungeon.getDungeonName();

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

        // Check if battle is applicable
        dungeon.handleBattle();

        // Spawn necessary mobs
        dungeon.spawnSpider(tickCounter);
        dungeon.spawnZombie(tickCounter);

        List<EntityResponse> entities = new ArrayList<EntityResponse>();
        this.dungeon.getMap().forEach((pos, entityList) -> {
            entityList.forEach((entity) -> {
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

        this.response = new DungeonResponse(dungeonId, dungeonName, entities, inventory, battles, buildables, goals);
        return this.response;
    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        Player player = dungeon.getPlayer();
        player.build(dungeon, buildable);

        String dungeonId = dungeon.getDungeonId();
        String dungeonName = dungeon.getDungeonName();

        Map<Position, List<Entity>> dungeonMap = this.dungeon.getMap();
        List<EntityResponse> entities = new ArrayList<EntityResponse>();
        dungeonMap.forEach((pos, entityList) -> {
            entityList.forEach((entity) -> {
                boolean isInteractable = entity instanceof Mercenary || entity instanceof ZombieToastSpawner;
                entities.add(
                        new EntityResponse(entity.getId(), entity.getType(), entity.getPosition(), isInteractable));
            });
        });

        List<ItemResponse> inventory = new ArrayList<ItemResponse>();
        for (CollectableEntity entity : player.getInventory()) {
            inventory.add(new ItemResponse(entity.getId(), entity.getType()));
        }

        // Battles not implemented, so will not be able to add any
        List<BattleResponse> battles = this.response.getBattles();

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
        this.response = new DungeonResponse(dungeonId, dungeonName, entities, inventory, battles, buildables, goals);
        return this.response;
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

        String dungeonId = dungeon.getDungeonId();
        String dungeonName = dungeon.getDungeonName();

        Map<Position, List<Entity>> dungeonMap = this.dungeon.getMap();
        List<EntityResponse> entities = new ArrayList<EntityResponse>();
        dungeonMap.forEach((pos, entityList) -> {
            entityList.forEach((entity) -> {
                boolean isInteractable = entity instanceof Mercenary || entity instanceof ZombieToastSpawner;
                entities.add(
                        new EntityResponse(entity.getId(), entity.getType(), entity.getPosition(), isInteractable));
            });
        });

        List<ItemResponse> inventory = new ArrayList<ItemResponse>();
        for (CollectableEntity entity : player.getInventory()) {
            inventory.add(new ItemResponse(entity.getId(), entity.getType()));
        }

        // Battles not implemented, so will not be able to add any
        List<BattleResponse> battles = this.response.getBattles();

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

        this.response = new DungeonResponse(dungeonId, dungeonName, entities, inventory, battles, buildables, goals);
        return this.response;
    }

    /**
     * /game/save
     */
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        System.out.print("SaveGame");
        return null;
    }

    /**
     * /game/load
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        System.out.print("loadGame");
        return null;
    }

    /**
     * /games/all
     */
    public List<String> allGames() {
        System.out.print("allGames");
        return new ArrayList<>();
    }

}
