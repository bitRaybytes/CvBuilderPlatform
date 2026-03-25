package com.example.cv_builderplatform.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

public abstract class AbstractFileUploadHandler implements FileUploadHandler {

    @Value("${app.upload.path:uploads}") // Fallback :uploads
    private String basePath;

    protected String buildFilePath(String username, String filename) {
        return basePath + "/" + username + "/" + filename;
    }

    protected void validateFileSize(MultipartFile file, long maxBytes) {
        if (file.getSize() > maxBytes) {
            throw new IllegalArgumentException("Datei zu gross");
        }
    }

    protected String sanitizeFilename(String filename) {
        return filename.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    // Template Method – der Ablauf ist fest, die Schritte sind variabel
    @Override
    public String upload(MultipartFile file, String username) throws IOException {
        validate(file, file.getSize());                              // Subklasse validiert
        String path = buildFilePath(username,
            sanitizeFilename(file.getOriginalFilename()));
        return store(file, path);                    // Subklasse speichert
    }

    // Abstrakte Methode – muss in Subklassen implementiert werden
    protected abstract String store(MultipartFile file, String path) throws IOException;
}