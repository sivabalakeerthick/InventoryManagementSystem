package com.example.InventoryManagementSystem.exception;

public class PurchaseOrderNotFoundException extends RuntimeException {
    public PurchaseOrderNotFoundException(String message) {
        super(message);
    }
}
