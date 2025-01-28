package com.cignititech.exception;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse<T> {
    private int status;         // HTTP status code (e.g., 400, 404)
    private String error;       // HTTP status message (e.g., "Bad Request")
    private List<T> errors;     // List of errors

    public ErrorResponse(int status, String error, List<T> errors) {
        this.status = status;
        this.error = error;
        this.errors = errors;
    }
}
