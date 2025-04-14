package com.riwi.backend.domain.ports.service;

import com.riwi.backend.application.dtos.responses.ProjectResponse;
import com.riwi.backend.domain.entities.Project;
import com.riwi.backend.application.dtos.requests.ProjectWithoutId;
import com.riwi.backend.application.services.generic.*;
import com.riwi.backend.domain.entities.User;

public interface IProjectService extends
        CreateWithUser<ProjectResponse, ProjectWithoutId, User>,
        ReadAll<ProjectResponse>,
        ReadById<ProjectResponse, Long>,
        Update<Long, ProjectResponse, ProjectWithoutId>,
        Delete<Long> {

}
