package dungeonmania;

import dungeonmania.util.Position;

public abstract class Entity {

    private String id;
    private Position position;
    private String type;
    
    /**
     * Constructor for Entity
     * @param id
     * @param position
     * @param type
     */
    public Entity(String id, Position position, String type) {
        this.id = id;
        this.position = position;
        this.type = type;
    }

    /**
     * Getter for id 
     * @return id
     */
    public String getId() {
        return this.id;
    }

    /**
     * Setter for id
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter for position
     * @return position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Setter for position
     * @param position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Getter for type
     * @return type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Setter for type
     */
    public void setType(String type) {
        this.type = type;
    }

}
