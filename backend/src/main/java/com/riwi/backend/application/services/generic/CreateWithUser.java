package com.riwi.backend.application.services.generic;

public interface CreateWithUser<Entity, EntityRequest, User> {
    public Entity createWithUser(EntityRequest entityRequest, User user);
}
