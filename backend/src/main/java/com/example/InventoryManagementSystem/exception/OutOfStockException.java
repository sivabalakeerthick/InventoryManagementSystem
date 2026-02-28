package com.example.InventoryManagementSystem.exception;

public class OutOfStockException extends Exception{
    public OutOfStockException(String message){
        super(message);
    }
}
