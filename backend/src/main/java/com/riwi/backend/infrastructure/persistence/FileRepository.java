package com.riwi.backend.infrastructure.persistence;

import com.riwi.backend.domain.entities.FileEntity;
import com.riwi.backend.domain.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByProject(Project project);
    List<FileEntity> findByProject_Id(Long projectId);
}
