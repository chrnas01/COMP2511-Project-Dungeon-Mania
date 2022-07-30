package dungeonmania.movingEntities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.DungeonMap.DungeonMap;
import dungeonmania.staticEntities.*;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Mercenary extends MovingEntity {
    // Mercenary is initally hostile
    private int bribeAmount;
    private boolean isHostile = true;
    private int controlDuration = 0;

    /**
     * Constructor for Mercenary
     * 
     * @param id
     * @param position
     * @param type
     * @param health
     * @param attack
     * @param bribeAmount
     */
    public Mercenary(String id, Position position, String type, int health, int attack, int bribeAmount) {
        super(id, position, type, health, attack);
        this.bribeAmount = bribeAmount;
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

    /**
     * Getter for controlDuration
     * @return how long this mercenary is under control for 
     */
    public int getControlDuration() {
        return this.controlDuration;
    }

    /**
     * Setter for controlDuration
     * @param duration
     */
    public void setControlDuration(int duration) {
        this.controlDuration = duration;
    }

    /**
     * Getter for bribeAmount
     * @return Amount of gold required to bribe this mercenary
     */
    public int getBribeAmount() {
        return this.bribeAmount;
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
        Direction y = displacement.getY() > 0 ? Direction.DOWN : Direction.UP;
        Direction xOp = displacement.getX() <= 0 ? Direction.RIGHT : Direction.LEFT;
        Direction yOp = displacement.getY() <= 0 ? Direction.DOWN : Direction.UP;

        // Determine heirarchy of directions
        List<Position> hierarchy = new ArrayList<Position>();
        // if x >= y then its more optimal to favour the x direction
        if (Math.abs(displacement.getX()) >= Math.abs(displacement.getY())) {
            hierarchy.add(this.getPosition().translateBy(x));
            hierarchy.add(this.getPosition().translateBy(y));
            hierarchy.add(this.getPosition().translateBy(xOp));
            hierarchy.add(this.getPosition().translateBy(yOp));
        } else {
            hierarchy.add(this.getPosition().translateBy(y));
            hierarchy.add(this.getPosition().translateBy(x));
            hierarchy.add(this.getPosition().translateBy(yOp));
            hierarchy.add(this.getPosition().translateBy(xOp));
        }

        // Makes sure all cardinally adjacent squares exist in the dungeon
        hierarchy.forEach((pos) -> {
            dungeon.addPosition(pos);
        });

        // If player is invincible reverse list of most optimal moves
        if (dungeon.getPlayer().getInvincible()) {
            Collections.reverse(hierarchy);
        }

        for (Position newPos : hierarchy) {
            boolean moveable = dungeon.getMap().get(newPos).stream().filter(
                    (entity) -> entity instanceof Wall || entity instanceof Boulder || entity.getType().equals("door"))
                    .collect(Collectors.toList()).isEmpty();

            if (moveable) {
                dungeon.moveEntity(this.getPosition(), newPos, this);
                break;
            }
        }
    }

    public void moveRandom(DungeonMap dungeon) {
        int random = new Random().nextInt(4);
        Position newPos = this.getCardinallyAdjacentSquares().get(random);

        // Makes sure all cardinally adjacent squares exist in the dungeon
        this.getCardinallyAdjacentSquares().forEach((pos) -> {
            dungeon.addPosition(pos);
        });

        // Can mercenary move to given square
        boolean moveable = dungeon.getMap().get(newPos).stream()
                .filter((entity) -> entity instanceof MovingEntity || entity instanceof Wall
                        || entity instanceof Boulder || entity.getType().equals("door"))
                .collect(Collectors.toList()).isEmpty();

        if (moveable) {
            dungeon.moveEntity(this.getPosition(), newPos, this);
        }
    }

    /**
     * Changes mercenary to ally
     */
    public void bribe() {
        this.setIsHostile(false);
        this.setControlDuration(-1);
    }

    /**
     * Temporarily changes mercenary to ally
     * for duration ticks
     * @param duration 
     */
    public void brainwash(int duration) {
        this.setIsHostile(false);
        this.setControlDuration(duration);
    }
}
