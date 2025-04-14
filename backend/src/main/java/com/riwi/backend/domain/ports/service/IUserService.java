package com.riwi.backend.domain.ports.service;

import com.riwi.backend.application.dtos.responses.UserWithoutId;
import com.riwi.backend.application.services.generic.Register;
import com.riwi.backend.domain.entities.User;

public interface IUserService extends
        Register<User, UserWithoutId> {
}
