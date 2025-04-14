package com.riwi.backend.domain.ports.service;

import com.riwi.backend.application.dtos.responses.FileResponse;
import com.riwi.backend.application.dtos.requests.FileUploadRequest;
import com.riwi.backend.application.services.generic.*;
import com.riwi.backend.domain.entities.FileEntity;
import com.riwi.backend.domain.entities.Project;

import java.util.List;

public interface IFileService extends
        CreateWithProject<List<FileResponse>, FileUploadRequest, Project>,
        Delete<Long> {

    List<FileResponse> findByProjectId(Long projectId);
}
