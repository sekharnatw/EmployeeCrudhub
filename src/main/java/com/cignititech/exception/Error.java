package com.cignititech.exception;

import lombok.Data;

@Data
public class Error {
    private String code;        // Error code (e.g., "VALIDATION_ERROR")
    private String message;     // Error message (e.g., "Invalid input data")
    private String details;     // Additional details (optional)

    public Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Error(String code, String message, String details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }
}
