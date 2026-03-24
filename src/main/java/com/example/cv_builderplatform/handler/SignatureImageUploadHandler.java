package com.example.cv_builderplatform.handler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SignatureImageUploadHandler extends AbstractFileUploadHandler {

    @Override
    public void validate(MultipartFile file) {
        validateFileSize(file, 2*1024*1024); // max 2 MB
        String type = file.getContentType();
        if (type == null || (!type.equals("image/png") && !type.equals("image/jpeg"))) {
            throw new IllegalArgumentException("Nur PNG und JPEG erlaubt");
        }
    }

    @Override
    protected String store(MultipartFile file, String path) throws IOException {
        // Datei lokal speichern
        Path target = Paths.get(path);
        Files.createDirectories(target.getParent());
        Files.write(target, file.getBytes());
        return path;
    }

}
