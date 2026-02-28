package com.example.InventoryManagementSystem.dto.responseDTO;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

    private int status;
    private String message;
    private String error;

    public ErrorResponse(HttpStatus status, String error, String message) {
        this.status = status.value();
        this.error = error;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
