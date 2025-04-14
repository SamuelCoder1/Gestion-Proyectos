package com.riwi.backend.application.dtos.responses;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class ProjectResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate deliveryDate;
    private String status;
}

