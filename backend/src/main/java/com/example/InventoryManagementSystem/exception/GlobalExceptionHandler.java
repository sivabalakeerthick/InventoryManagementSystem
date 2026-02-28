package com.example.InventoryManagementSystem.exception;

import com.example.InventoryManagementSystem.dto.responseDTO.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoryAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleCategoryAlreadyExists(CategoryAlreadyExistException categoryAlreadyExistException) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT,
                "Conflict",
                categoryAlreadyExistException.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFound(OrderNotFoundException orderNotFoundException) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND,
                "NotFound",
                orderNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ProductAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleProductAlreadyExists(ProductAlreadyExistException productAlreadyExistException) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT,
                "Conflict",
                productAlreadyExistException.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(PurchaseOrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePurchaseOrderNotFound(PurchaseOrderNotFoundException purchaseOrderNotFoundException) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND,
                "NotFound",
                purchaseOrderNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(SupplierNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSupplierNotFound(SupplierNotFoundException supplierNotFoundException) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND,
                "NotFound",
                supplierNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExist(UserAlreadyExistException userAlreadyExistException) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT,
                "Conflict",
                userAlreadyExistException.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException userNotFoundException) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND,
                "NotFound",
                userNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());

        List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> Map.of("field", error.getField(), "message", error.getDefaultMessage()))
                .collect(Collectors.toList());

        response.put("errors", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<String> handleOutOfStockException(OutOfStockException outOfStockException) {
        return new ResponseEntity<>(outOfStockException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> productNotFoundException(ProductNotFoundException productNotFoundException) {
        return new ResponseEntity<>(productNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException){
        return new ResponseEntity<>(illegalArgumentException.getMessage() , HttpStatus.BAD_REQUEST);
    }
}