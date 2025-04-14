package com.riwi.backend.application.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileResponse {
    private Long id;
    private String name;
    private String type;
    private Long size;
    private String url;
}
