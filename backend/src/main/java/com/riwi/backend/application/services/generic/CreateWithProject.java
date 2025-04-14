package com.riwi.backend.application.services.generic;

public interface CreateWithProject<EntityResponse, EntityRequest, Project> {
    public EntityResponse createWithProject(EntityRequest request, Project project);
}