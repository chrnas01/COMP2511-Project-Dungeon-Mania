package dungeonmania.entity.staticentity;

import dungeonmania.util.Position;

public class Portal extends StaticEntity {
    private String colour;

    public Portal(Position position, String colour) {
        super(position);
        this.colour = colour;
    }

    public String getColour() {
        return colour;
    }
}
