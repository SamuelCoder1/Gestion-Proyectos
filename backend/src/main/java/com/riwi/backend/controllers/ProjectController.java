package com.riwi.backend.controllers;

import com.riwi.backend.application.dtos.requests.ProjectWithoutId;
import com.riwi.backend.application.dtos.responses.ProjectResponse;
import com.riwi.backend.domain.entities.User;
import com.riwi.backend.application.services.impl.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@CrossOrigin("*")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Operation(summary = "Create a new project", description = "Creates a new project for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid project data")
    })
    @PostMapping("create")
    public ResponseEntity<ProjectResponse> create(@RequestBody ProjectWithoutId request,
                                                  @AuthenticationPrincipal User user) {
        ProjectResponse createdProject = projectService.createWithUser(request, user);
        return ResponseEntity.ok(createdProject);
    }

    @Operation(summary = "Get all projects", description = "Retrieve all projects from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects retrieved successfully")
    })
    @GetMapping("getAll")
    public ResponseEntity<List<ProjectResponse>> getAll() {
        return ResponseEntity.ok(projectService.readAll());
    }

    @Operation(summary = "Get a project by ID", description = "Retrieve a specific project by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project found"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    @GetMapping("getById/{id}")
    public ResponseEntity<ProjectResponse> getById(@PathVariable Long id) {
        return projectService.readById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update a project", description = "Update an existing project by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project updated successfully"),
            @ApiResponse(responseCode = "404", description = "Project not found"),
            @ApiResponse(responseCode = "400", description = "Invalid project data")
    })
    @PutMapping("update/{id}")
    public ResponseEntity<ProjectResponse> update(@PathVariable Long id,
                                                  @RequestBody ProjectWithoutId request) {
        ProjectResponse updatedProject = projectService.update(id, request);
        return ResponseEntity.ok(updatedProject);
    }

    @Operation(summary = "Delete a project", description = "Delete a project by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Project deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
