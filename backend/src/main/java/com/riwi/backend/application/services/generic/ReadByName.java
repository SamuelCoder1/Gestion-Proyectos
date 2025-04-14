package com.riwi.backend.application.services.generic;

public interface ReadByName<Entity, NAME>{
    public Entity readByName(NAME name);
}
