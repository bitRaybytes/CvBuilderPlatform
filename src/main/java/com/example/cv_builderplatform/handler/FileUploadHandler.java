package com.example.cv_builderplatform.handler;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/** NOTE
 * 
 * Files are only stored as string from filepath. 
 * PdfExport has to read the local FS to access image.
 * 
 * I can set the return type String to byte[] @upload to store the actual image.
 * 
 * Possible options are: 
 * Store as DB BLOB with `bytea` out of byte[]-Array – no FS needed, 
 * or cloud storages like S3 / Azure Blob
 */

public interface FileUploadHandler {
    String upload(MultipartFile file, String username) throws IOException;
    void validate(MultipartFile file, long maxBytes);
}
