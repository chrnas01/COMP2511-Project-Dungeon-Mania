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

public class MercenaryEnemyState implements MercenaryState {

    private Mercenary merc;

    /**
     * Constructor for MercenaryEnemyState
     * 
     * @param merc
     */
    public MercenaryEnemyState(Mercenary merc) {
        this.merc = merc;
    }

    @Override
    public void move(DungeonMap dungeon) {
        Position playerPos = dungeon.getPlayer().getPosition();
        Position mercPos = this.merc.getPosition();

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
            hierarchy.add(this.merc.getPosition().translateBy(x));
            hierarchy.add(this.merc.getPosition().translateBy(y));
            hierarchy.add(this.merc.getPosition().translateBy(xOp));
            hierarchy.add(this.merc.getPosition().translateBy(yOp));
        } else {
            hierarchy.add(this.merc.getPosition().translateBy(y));
            hierarchy.add(this.merc.getPosition().translateBy(x));
            hierarchy.add(this.merc.getPosition().translateBy(yOp));
            hierarchy.add(this.merc.getPosition().translateBy(xOp));
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
                dungeon.moveEntity(this.merc.getPosition(), newPos, this.merc);
                break;
            }
        }

    }

    @Override
    public void moveRandom(DungeonMap dungeon) {
        int random = new Random().nextInt(4);
        Position newPos = this.merc.getCardinallyAdjacentSquares().get(random);

        // Makes sure all cardinally adjacent squares exist in the dungeon
        this.merc.getCardinallyAdjacentSquares().forEach((pos) -> {
            dungeon.addPosition(pos);
        });

        // Can mercenary move to given square
        boolean moveable = dungeon.getMap().get(newPos).stream()
                .filter((entity) -> entity instanceof MovingEntity || entity instanceof Wall
                        || entity instanceof Boulder || entity.getType().equals("door"))
                .collect(Collectors.toList()).isEmpty();

        if (moveable) {
            dungeon.moveEntity(this.merc.getPosition(), newPos, this.merc);
        }
    }

}
