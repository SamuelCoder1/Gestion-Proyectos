package com.riwi.backend.application.services.impl;

import com.riwi.backend.application.dtos.mappers.ProjectMapper;
import com.riwi.backend.application.dtos.requests.ProjectWithoutId;
import com.riwi.backend.application.dtos.responses.ProjectResponse;
import com.riwi.backend.domain.entities.Project;
import com.riwi.backend.domain.entities.User;
import com.riwi.backend.domain.ports.service.IProjectService;
import com.riwi.backend.infrastructure.persistence.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService implements IProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public ProjectResponse createWithUser(ProjectWithoutId dto, User user) {
        Project project = Project.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .deliveryDate(dto.getDeliveryDate())
                .status(dto.getStatus())
                .user(user)
                .build();

        Project savedProject = projectRepository.save(project);

        return ProjectResponse.builder()
                .id(savedProject.getId())
                .title(savedProject.getTitle())
                .description(savedProject.getDescription())
                .deliveryDate(savedProject.getDeliveryDate())
                .status(savedProject.getStatus())
                .build();
    }

    @Override
    public List<ProjectResponse> readAll() {
        return projectRepository.findAll()
                .stream()
                .map(ProjectMapper::toResponse)
                .toList();
    }

    @Override
    public Optional<ProjectResponse> readById(Long id) {
        return projectRepository.findById(id)
                .map(ProjectMapper::toResponse);
    }

    @Override
    public ProjectResponse update(Long id, ProjectWithoutId dto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setTitle(dto.getTitle());
        project.setDescription(dto.getDescription());
        project.setDeliveryDate(dto.getDeliveryDate());
        project.setStatus(dto.getStatus());

        Project updated = projectRepository.save(project);

        return ProjectMapper.toResponse(updated);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        //Esto es para eliminar la relacion del usuario al proyecto despues de ejecutar el metodo
        User user = project.getUser();
        user.getProjects().remove(project);

        projectRepository.delete(project);
    }

    public List<Project> readAllByUser(User user) {
        return projectRepository.findByUser(user);
    }
}
