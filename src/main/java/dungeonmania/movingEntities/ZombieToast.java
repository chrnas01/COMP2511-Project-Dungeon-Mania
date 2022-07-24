package dungeonmania.movingEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.Entity;
import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.staticEntities.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class ZombieToast extends MovingEntity {

    private List<Position> cardinallyAdjacentSquares;
   
    /**
     * Constructor for ZombieToast
     * @param id
     * @param position
     * @param type
     * @param health
     * @param attack
     */
    public ZombieToast(String id, Position position, String type, double health, int attack) {
        super(id, position, type, health, attack);

        this.cardinallyAdjacentSquares = getCardinallyAdjacentSquares();
    }

    /**
    * Return Adjacent positions in an array list with the following element positions:
    *   0
    * 3 p 1
    *   2 
    * @return
    */
    public List<Position> getCardinallyAdjacentSquares() {
        List<Position> caSquares = new ArrayList<Position>();

        caSquares.add(this.getPosition().translateBy(Direction.UP));
        caSquares.add(this.getPosition().translateBy(Direction.RIGHT));
        caSquares.add(this.getPosition().translateBy(Direction.DOWN));
        caSquares.add(this.getPosition().translateBy(Direction.LEFT));

        return caSquares;
    }

    @Override
    public void move(Direction direction, DungeonMap dungeon) {
        this.move(dungeon);
    }
    
    /**
     * Moves the zombieToast in a random direction. 
     * If the random direction is not moveable then the zombie stands still for that tick.
     * @param dungeon
     */
    public void move(DungeonMap dungeon) {
        int random = new Random().nextInt(4);
        Position newPos = this.getCardinallyAdjacentSquares().get(random);
        
        // Makes sure all cardinally adjacent squares exist in the dungeon
        this.getCardinallyAdjacentSquares().forEach((pos) -> {
            dungeon.addPosition(pos);
        });
        
        // Can zombie move to given square 
        boolean moveable = dungeon.getMap().get(newPos).stream().filter((entity) -> entity instanceof MovingEntity || entity instanceof Wall || entity instanceof Boulder || entity.getType().equals("door")).collect(Collectors.toList()).isEmpty();

        if (moveable) {
            dungeon.moveEntity(this.getPosition(), newPos, this);
        }
    }

    /**
     * If the player has Invincibility Potion active then zombies should run away from player.
     * @param dungeonMap
     */
    public void moveAwayFromPlayer(DungeonMap dungeon) {
        Position playerPos = dungeon.getPlayer().getPosition();
        Position zombiePos = this.getPosition();
        
        // the direction from mercenary to player 
        Position displacement = Position.calculatePositionBetween(zombiePos, playerPos);
        
        Direction x = displacement.getX() < 0 ? Direction.RIGHT : Direction.LEFT;
        Direction y = displacement.getY() < 0 ? Direction.DOWN  : Direction.UP;
        Direction xOp = displacement.getX() > 0 ? Direction.RIGHT : Direction.LEFT;
        Direction yOp = displacement.getY() > 0 ? Direction.DOWN  : Direction.UP;

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
             
        }

        // Makes sure all cardinally adjacent squares exist in the dungeon
        this.getCardinallyAdjacentSquares().forEach((pos) -> {
            dungeon.addPosition(pos);
        });

        for (Position newPos : hierarchy) {
            boolean moveable = dungeon.getMap().get(newPos).stream().filter((entity) -> entity instanceof Wall || entity instanceof Boulder || entity.getType().equals("door")).collect(Collectors.toList()).isEmpty();

            if (moveable) {
                dungeon.moveEntity(this.getPosition(), newPos, this);
                break;
            }
        }
    }
}
