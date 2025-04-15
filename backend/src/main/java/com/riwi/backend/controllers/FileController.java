package com.riwi.backend.controllers;

import com.riwi.backend.application.dtos.responses.FileResponse;
import com.riwi.backend.application.services.impl.FileService;
import com.riwi.backend.domain.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/files")
@CrossOrigin("*")
public class FileController {

    @Autowired
    private FileService fileService;

    @Operation(summary = "Upload files to a project", description = "Upload one or more files to the specified project.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Files uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid file data")
    })
    @PostMapping("/upload/{projectId}")
    public ResponseEntity<List<FileResponse>> uploadFiles(@PathVariable Long projectId,
                                                          @RequestParam("files") List<MultipartFile> files,
                                                          @AuthenticationPrincipal User user) {
        List<FileResponse> uploadedFiles = fileService.uploadFiles(projectId, files, user);
        return ResponseEntity.ok(uploadedFiles);
    }



    @Operation(summary = "Get files from a project", description = "Retrieve all files associated with a specific project.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Files retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<FileResponse>> getFilesByProject(@PathVariable Long projectId) {
        List<FileResponse> files = fileService.getFilesByProject(projectId);
        return files.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(files);
    }

    @Operation(summary = "Download a file", description = "Download a file by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File downloaded successfully"),
            @ApiResponse(responseCode = "404", description = "File not found")
    })
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        return fileService.downloadFile(fileId);
    }

    @Operation(summary = "Delete a file", description = "Delete a specific file by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "File deleted successfully"),
            @ApiResponse(responseCode = "404", description = "File not found")
    })
    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long fileId) {
        fileService.deleteFile(fileId);
        return ResponseEntity.noContent().build();
    }
}
