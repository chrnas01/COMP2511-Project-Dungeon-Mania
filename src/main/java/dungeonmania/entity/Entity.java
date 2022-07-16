package dungeonmania.entity;

import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

import java.util.Random;

public class Entity {
    String id;
    Position position;
    EntityType entityType;
    boolean isInteractable;

    public Entity(Position position, EntityType entityType) {
        this.position = position;
        this.entityType = entityType;
        this.id = "" + System.currentTimeMillis() + new Random().nextInt(10);
    }

    public String getType() {
        return getClass().getSimpleName();
    }

    public boolean isInteractable() {
        return isInteractable;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public EntityResponse toEntityResponse() {
        return new EntityResponse(id, getType(), position, isInteractable());
    }
}
