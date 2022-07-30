package dungeonmania.movingEntities;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Assassin extends Mercenary{
    
    private double bribeFailRate;
    private int reconRadius;
    
    /**
     * Constructor for Mercenary
     *
     * @param id
     * @param position
     * @param type
     * @param health
     * @param attack
     */
    public Assassin(String id, Position position, String type, int health, int attack, int bribe_amount, double bribeFailRate, int reconRadius) {
        super(id, position, type, health, attack, bribe_amount);
        this.bribeFailRate = bribeFailRate;
        this.reconRadius = reconRadius;
    }

    @Override
    public void move(DungeonMap dungeon) {
        Player player=null;
        Map<Position, List<Entity>> map = dungeon.getMap();
        for(Position position:map.keySet()){
            List<Entity> entities=map.get(position);
            for(Entity entity:entities){
                if(entity instanceof Player){
                    player= (Player) entity;
                }
            }

        }
        if(player==null){
            return;
        }
        Position playerPosition=player.getPosition();
        Position old = this.getPosition();
        Position diff = Position.calculatePositionBetween(playerPosition,old);
        if(Math.abs(diff.getY())+Math.abs(diff.getX())>reconRadius){
            return;
        }
        List<Direction> directions =  Arrays.asList(Direction.RIGHT, Direction.UP, Direction.LEFT, Direction.DOWN);
        for(Direction d:directions){
            if(old.translateBy(d).equals(playerPosition)){
                return ;
            }
        }
        Direction next_d=bfs(old,playerPosition,dungeon);
        Position next_position = this.getPosition().translateBy(next_d);
        dungeon.moveEntity(this.getPosition(), next_position, this);
        this.setPosition(next_position);

    }
}
