package com.example.cv_builderplatform.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * Global Exception handling
 * if a user already exists (f.e.)
 */


@ControllerAdvice
public class GlobalExceptionHandler {

    // fängt deine eigene Exception
    @ExceptionHandler(CvNotFoundException.class)
    public ResponseEntity<String> handleCvNotFound(CvNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    // fängt Datenbankfehler bei doppeltem Username
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDuplicate(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Username oder E-Mail bereits vergeben");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body("User nicht gefunden");
    }

    // fängt alles andere – als Sicherheitsnetz
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ein Fehler ist aufgetreten");
    }
}
