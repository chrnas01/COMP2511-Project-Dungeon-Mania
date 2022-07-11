package dungeonmania;

import dungeonmania.util.Position;

public abstract class Entity {

    private String id;
    private Position position;
    private String type;            // this is for the JSON entries
    
    public Entity(String id, Position position, String type) {
        this.id = id;
        this.position = position;
        this.type = type;
    }

    // Will need methods to convert to JSON

    
    // Will also need to add additional methods that we think are useful


    // Getters and Setters
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Position getPos() {
        return position;
    }

    public void setPos(Position position) {
        this.position = position;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
