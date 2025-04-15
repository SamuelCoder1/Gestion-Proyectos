package com.riwi.backend.application.services.impl;

import com.riwi.backend.application.dtos.responses.FileResponse;
import com.riwi.backend.domain.entities.FileEntity;
import com.riwi.backend.domain.entities.Project;
import com.riwi.backend.domain.entities.User;
import com.riwi.backend.infrastructure.persistence.FileRepository;
import com.riwi.backend.infrastructure.persistence.ProjectRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;

@Service
public class FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public List<FileResponse> uploadFiles(Long projectId, List<MultipartFile> files, User user) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Path projectPath = Paths.get(uploadDir, String.valueOf(projectId));

        try {
            Files.createDirectories(projectPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create project folder", e);
        }

        return files.stream().map(file -> {
            try {
                String fileExtension = getFileExtension(file.getOriginalFilename());

                Path fileCategoryPath = getFileCategoryPath(fileExtension, projectPath);
                Files.createDirectories(fileCategoryPath);

                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = fileCategoryPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath);

                FileEntity fileEntity = FileEntity.builder()
                        .name(fileName)
                        .type(file.getContentType())
                        .size(file.getSize())
                        .url(filePath.toString())
                        .project(project)
                        .build();

                fileRepository.save(fileEntity);

                return FileResponse.builder()
                        .id(fileEntity.getId())
                        .name(fileEntity.getName())
                        .type(fileEntity.getType())
                        .size(fileEntity.getSize())
                        .url("/files/download/" + fileEntity.getId())
                        .build();
            } catch (IOException e) {
                throw new RuntimeException("Error storing file", e);
            }
        }).collect(Collectors.toList());
    }

    public ResponseEntity<Resource> downloadFile(Long fileId) {
        FileEntity fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        Path path = Paths.get(fileEntity.getUrl());
        Resource resource = new FileSystemResource(path.toFile());

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + fileEntity.getName() + "\"")
                .header("Content-Type", fileEntity.getType())
                .contentLength(fileEntity.getSize())
                .body(resource);
    }

    @Transactional
    public void deleteFile(Long fileId) {
        FileEntity fileEntity = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        Project project = fileEntity.getProject();
        project.getFiles().remove(fileEntity);
        projectRepository.save(project);

        try {
            Path path = Paths.get(fileEntity.getUrl());
            Files.deleteIfExists(path);

            fileRepository.delete(fileEntity);

            fileRepository.flush();

            System.out.println("Archivo eliminado de la base de datos con ID: " + fileId);
        } catch (IOException e) {
            throw new RuntimeException("Could not delete file", e);
        }
    }

    public List<FileResponse> getFilesByProject(Long projectId) {
        List<FileEntity> files = fileRepository.findByProject_Id(projectId);

        return files.stream()
                .map(file -> FileResponse.builder()
                        .id(file.getId())
                        .name(file.getName())
                        .type(file.getType())
                        .size(file.getSize())
                        .url("/files/download/" + file.getId())
                        .build())
                .collect(Collectors.toList());
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        return dotIndex > 0 ? fileName.substring(dotIndex + 1).toLowerCase() : "";
    }

    private Path getFileCategoryPath(String fileExtension, Path projectPath) {
        String folder;

        if (isImage(fileExtension)) {
            folder = "images";
        } else if (isAudio(fileExtension)) {
            folder = "audios";
        } else if (isDocument(fileExtension)) {
            folder = "documents";
        } else if (isVideo(fileExtension)) {
            folder = "videos";
        } else {
            folder = "others";
        }

        return projectPath.resolve(folder);
    }

    private boolean isImage(String fileExtension) {
        return List.of("jpg", "jpeg", "png", "gif", "bmp").contains(fileExtension);
    }

    private boolean isAudio(String fileExtension) {
        return List.of("mp3", "wav", "ogg", "aac").contains(fileExtension);
    }

    private boolean isDocument(String fileExtension) {
        return List.of("pdf", "doc", "docx", "txt", "xls", "xlsx").contains(fileExtension);
    }

    private boolean isVideo(String fileExtension) {
        return List.of("mp4", "avi", "mov", "mkv").contains(fileExtension);
    }
}
