package com.riwi.backend.application.dtos.mappers;

import com.riwi.backend.application.dtos.responses.ProjectResponse;
import com.riwi.backend.domain.entities.Project;

public class ProjectMapper {

    public static ProjectResponse toResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .deliveryDate(project.getDeliveryDate())
                .status(project.getStatus())
                .build();
    }
}

