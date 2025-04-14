package com.riwi.backend.application.services.generic;

public interface Create<Entity, EntityRequest> {
    public Entity create(EntityRequest entityRequest);
}
