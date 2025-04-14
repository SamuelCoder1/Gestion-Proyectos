package com.riwi.backend.application.services.impl;

import com.riwi.backend.application.dtos.responses.FileResponse;
import com.riwi.backend.domain.entities.FileEntity;
import com.riwi.backend.domain.entities.Project;
import com.riwi.backend.domain.entities.User;
import com.riwi.backend.infrastructure.persistence.FileRepository;
import com.riwi.backend.infrastructure.persistence.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public List<FileResponse> uploadFiles(Long projectId, List<MultipartFile> files, User user) {
        // Verificar si el proyecto existe
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Procesar cada archivo y guardarlo
        return files.stream()
                .map(file -> {
                    // Guardar el archivo en la base de datos
                    FileEntity fileEntity = FileEntity.builder()
                            .name(file.getOriginalFilename())
                            .type(file.getContentType())
                            .size(file.getSize())
                            .url("/files/download/" + projectId + "/" + file.getOriginalFilename())
                            .project(project)  // Asociamos el archivo con el proyecto
                            .build();

                    // Guardar la entidad en la base de datos
                    fileRepository.save(fileEntity);

                    // Devolver un FileResponse para el archivo subido
                    return FileResponse.builder()
                            .id(fileEntity.getId())
                            .name(fileEntity.getName())
                            .type(fileEntity.getType())
                            .size(fileEntity.getSize())
                            .url(fileEntity.getUrl())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public List<FileResponse> getFilesByProject(Long projectId) {
        // Obtener todos los archivos asociados al proyecto
        List<FileEntity> files = fileRepository.findByProject_Id(projectId);
        return files.stream()
                .map(file -> FileResponse.builder()
                        .id(file.getId())
                        .name(file.getName())
                        .type(file.getType())
                        .size(file.getSize())
                        .url(file.getUrl())
                        .build())
                .collect(Collectors.toList());
    }

    public ResponseEntity<Resource> downloadFile(Long fileId) {
        // Obtener el archivo por ID
        FileEntity fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        Resource file = new FileSystemResource(fileEntity.getUrl());
        return ResponseEntity.ok(file);
    }

    public void deleteFile(Long fileId) {
        // Obtener y eliminar el archivo por ID
        FileEntity fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        fileRepository.delete(fileEntity);
    }
}
