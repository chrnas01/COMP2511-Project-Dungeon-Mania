package dungeonmania.movingEntities;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import dungeonmania.Entity;
import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.staticEntities.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Mercenary extends MovingEntity {
    // Mercenary is initally hostile
    private int bribe_amount;
    private boolean isHostile = true;

    /**
     * Constructor for Mercenary
     * 
     * @param id
     * @param position
     * @param type
     * @param health
     * @param attack
     * @param bribe_amount
     */
    public Mercenary(String id, Position position, String type, int health, int attack, int bribe_amount) {
        super(id, position, type, health, attack);
        this.bribe_amount = bribe_amount;
    }

    /**
     * Getter for isHostile
     * 
     * @return
     */
    public boolean getIsHostile() {
        return this.isHostile;
    }

    /**
     * Setter for isHostile
     * This variable is updated if the mercernary has been bribed with gold
     * 
     * @param isHostile
     */
    public void setIsHostile(boolean isHostile) {
        this.isHostile = isHostile;
    }

    public int getBribeAmount() {
        return this.bribe_amount;
    }

    @Override
    public void move(Direction direction, DungeonMap dungeon) {
        this.move(dungeon);
    }

    public void move(DungeonMap dungeon) {
        Position playerPos = dungeon.getPlayer().getPosition();
        Position mercPos = this.getPosition();
        
        // the direction from mercenary to player 
        Position displacement = Position.calculatePositionBetween(mercPos, playerPos);
        
        Direction x = displacement.getX() > 0 ? Direction.RIGHT : Direction.LEFT;
        Direction y = displacement.getY() > 0 ? Direction.DOWN  : Direction.UP;
        Direction xOp = displacement.getX() <= 0 ? Direction.RIGHT : Direction.LEFT;
        Direction yOp = displacement.getY() <= 0 ? Direction.DOWN  : Direction.UP;

        // Determine heirarchy of directions 
        List <Position> hierarchy = new ArrayList<Position>();
        // if x >= y then its more optimal to favour the x direction
        if (Math.abs(displacement.getX()) >= Math.abs(displacement.getY())) {
            hierarchy.add(this.getPosition().translateBy(x));
            hierarchy.add(this.getPosition().translateBy(y));
            hierarchy.add(this.getPosition().translateBy(xOp));
            hierarchy.add(this.getPosition().translateBy(yOp));  
        }
        else {
            hierarchy.add(this.getPosition().translateBy(y));
            hierarchy.add(this.getPosition().translateBy(x));
            hierarchy.add(this.getPosition().translateBy(yOp));
            hierarchy.add(this.getPosition().translateBy(xOp));  
        }

        // Makes sure all cardinally adjacent squares exist in the dungeon
        hierarchy.forEach((pos) -> {
                dungeon.addPosition(pos);
        });

        for (Position newPos : hierarchy) {
            boolean moveable = dungeon.getMap().get(newPos).stream().filter((entity) -> entity instanceof Wall || entity instanceof Boulder || entity.getType().equals("door")).collect(Collectors.toList()).isEmpty();

            if (moveable) {
                dungeon.moveEntity(this.getPosition(), newPos, this);
                break;
            }
        }

        // System.out.println(displacement);
        // System.out.println("Mercenary must move " + Math.abs(x) + " units " + (x > 0 ? "right." : "left."));
        // System.out.println("Mercenary must move " + Math.abs(y) + " units " + (y > 0 ? "down." : "up."));

    }

    public void bribe() {
        this.setIsHostile(false);
    }

    public Direction bfs(Position src, Position tar, DungeonMap dungeon) {
        Queue<Position> queue = new LinkedList<Position>();
        HashSet<Position> visited = new HashSet<>();
        HashMap<Position,Position> mapDirection= new HashMap<>();
        HashMap<Position,Integer> mapDistance= new HashMap<>();
        List<Direction> directions =  Arrays.asList(Direction.RIGHT, Direction.UP, Direction.LEFT, Direction.DOWN);
        visited.add(src);
        queue.add(src);
        mapDistance.put(src,0);
        while(! queue.isEmpty()){
            Position now=queue.poll();
            if(mapDistance.get(now)>20){
                return null;
            }
            for(Direction direction:directions) {
                Position next_position = now.translateBy(direction);
                if (visited.contains(next_position))continue;
                visited.add(next_position);
                if(next_position.equals(tar)){
                    Position prev=mapDirection.get(now);
                    while(!prev.equals(src)){
                        now=prev;
                        prev=mapDirection.get(now);
                    }
                    for(Direction d:directions){
                        if(prev.translateBy(d).equals(now)){
                            return d;
                        }
                    }
                }

                List<Entity> entities = dungeon.getMap().get(next_position);
                if (entities == null) {
                    queue.add(next_position);
                    mapDirection.put(next_position,now);
                    mapDistance.put(next_position,mapDistance.get(now)+1);
                    continue;
                }
                for (Entity entity: entities) {
                    if (entity instanceof Wall || entity instanceof ZombieToastSpawner) {break;}
                    if ((entity instanceof Boulder && ((Boulder) entity).tryDirection(dungeon, direction))
                            || (entity instanceof Door && ((Door) entity).getOpen())) {

                        queue.add(next_position);
                        mapDirection.put(next_position,now);
                        mapDistance.put(next_position,mapDistance.get(now)+1);

                        break;
                    } else if (entity instanceof Boulder || entity instanceof Door) {
                        break;
                    } else {

                        queue.add(next_position);
                        mapDirection.put(next_position,now);
                        mapDistance.put(next_position,mapDistance.get(now)+1);

                    }
                }
            }
        }
        
        return null;
    }
}
 


    // public void move(DungeonMap dungeon) {
    //     Player player=null;
    //     Map<Position, List<Entity>> map = dungeon.getMap();
    //     for(Position position:map.keySet()){
    //         List<Entity> entities=map.get(position);
    //         for(Entity entity:entities){
    //             if(entity instanceof Player){
    //                 player= (Player) entity;
    //             }
    //         }

    //     }
    //     if(player==null || player.getInvisible()){
    //         return;
    //     }


    //     Position playerPosition=player.getPosition();
    //     Position old = this.getPosition();
    //     List<Direction> directions =  Arrays.asList(Direction.RIGHT, Direction.UP, Direction.LEFT, Direction.DOWN);
    //     for(Direction d:directions){
    //         if(old.translateBy(d).equals(playerPosition)){
    //             return ;
    //         }
    //     }
    //     Direction next_d=bfs(old,playerPosition,dungeon);
    //     Position next_position = this.getPosition().translateBy(next_d);
    //     dungeon.moveEntity(this.getPosition(), next_position, this);
    //     this.setPosition(next_position);

    // }


