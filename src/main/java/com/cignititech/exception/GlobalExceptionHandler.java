package com.cignititech.exception;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle validation errors (400 Bad Request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<Error>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<Error> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new Error(
                        "VALIDATION_ERROR",
                        fieldError.getDefaultMessage(),
                        fieldError.getField()))
                .collect(Collectors.toList());

        ErrorResponse<Error> errorResponse = new ErrorResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Handle resource not found errors (404 Not Found)
    @ExceptionHandler(ConfigDataResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse<Error>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Error error = new Error("RESOURCE_NOT_FOUND", ex.getMessage());
        ErrorResponse<Error> errorResponse = new ErrorResponse<>(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                Collections.singletonList(error));

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Handle other exceptions (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<Error>> handleGenericException(Exception ex) {
        Error error = new Error("INTERNAL_SERVER_ERROR", "An unexpected error occurred", ex.getMessage());
        ErrorResponse<Error> errorResponse = new ErrorResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                Collections.singletonList(error));

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}