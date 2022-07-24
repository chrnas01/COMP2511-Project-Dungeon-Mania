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
    
    public void move(DungeonMap dungeon) {
        int random = new Random().nextInt(4);
        Position newPos = this.getCardinallyAdjacentSquares().get(random);
        
        // Makes sure all cardinally adjacent squares exist in the dungeon
        this.getCardinallyAdjacentSquares().forEach((pos) -> {
            dungeon.addPosition(pos);
        });
        
        // Can zombie move to given square 
        boolean moveable = dungeon.getMap().get(newPos).stream().filter((entity) -> entity instanceof MovingEntity || entity instanceof Wall || entity instanceof Boulder || entity.getType().equals("door")).collect(Collectors.toList()).isEmpty();

        // Checks if zombie can move to another square
        // boolean canMoveElsewhere = false;
        // for (Position pos : this.cardinallyAdjacentSquares) {
        //     boolean canMove = dungeon.getMap().get(pos).stream().filter((entity) -> entity instanceof MovingEntity || entity instanceof Wall || entity instanceof Boulder || entity.getType().equals("door")).collect(Collectors.toList()).isEmpty();

        //     if (canMove) {
        //         canMoveElsewhere = true;
        //     }
        // }

        if (moveable) {
            dungeon.moveEntity(this.getPosition(), newPos, this);
        }
        // else if (canMoveElsewhere) { 
        //     this.move(dungeon);
        // }
    }
}
