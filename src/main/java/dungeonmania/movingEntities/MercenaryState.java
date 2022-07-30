package dungeonmania.movingEntities;

import dungeonmania.DungeonMap.DungeonMap;

public interface MercenaryState {

    public void move(DungeonMap dungeon);

    public void moveRandom(DungeonMap dungeon);
}
