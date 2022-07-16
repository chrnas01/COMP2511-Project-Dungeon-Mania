package dungeonmania.movingEntities;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Spider extends MovingEntity{
    Position init_position;
    Position position;
    public Spider(String id, Position position, String type) {
        super(id, position, type);
        init_position=new Position(position.getX(), position.getY(), position.getLayer());
        this.position=position;
    }
    public void moving(){
        if(position.equals(init_position)){
            this.setPos(position.translateBy(Direction.UP));
        }
    }

}
