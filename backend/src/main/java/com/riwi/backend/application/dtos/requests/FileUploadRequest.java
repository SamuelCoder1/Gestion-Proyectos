package com.riwi.backend.application.dtos.requests;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
@Builder
public class FileUploadRequest {
    private List<MultipartFile> files;
}
