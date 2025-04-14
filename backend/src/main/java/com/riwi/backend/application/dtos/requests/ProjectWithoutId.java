package com.riwi.backend.application.dtos.requests;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class ProjectWithoutId {

    private String title;

    private String description;

    private LocalDate deliveryDate;

    private String status;
}
