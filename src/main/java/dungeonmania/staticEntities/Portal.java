package dungeonmania.staticEntities;

import dungeonmania.util.Position;

public class Portal extends StaticEntity {

    private String colour_id;
    private Position teleportal;

    /**
     * Constructor for Portal
     * @param id
     * @param position
     * @param type
     * @param colour_id
     */
    public Portal (String id, Position position, String type, String colour_id) {
        super(id, position, type);
        this.colour_id = colour_id;
    }

    public String getColour() {
        return this.colour_id;
    }

}
