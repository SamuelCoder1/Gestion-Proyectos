package com.riwi.backend.infrastructure.persistence;

import com.riwi.backend.application.dtos.responses.ProjectResponse;
import com.riwi.backend.application.services.generic.ReadById;
import com.riwi.backend.domain.entities.Project;
import com.riwi.backend.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByUser(User user);
}
