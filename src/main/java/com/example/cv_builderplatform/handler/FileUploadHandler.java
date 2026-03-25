package com.example.cv_builderplatform.handler;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadHandler {
    String upload(MultipartFile file, String username) throws IOException;
    void validate(MultipartFile file, long maxBytes);
}
