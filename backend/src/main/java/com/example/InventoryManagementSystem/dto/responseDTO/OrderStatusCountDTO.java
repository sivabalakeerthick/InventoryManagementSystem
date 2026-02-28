// File: com/example/OrderStatusCountDTO.java
package com.example.InventoryManagementSystem.dto.responseDTO; // Adjust package as per your project structure

import com.example.InventoryManagementSystem.enums.OrderStatus; // Ensure this import matches your OrderStatus enum location

public class OrderStatusCountDTO {
    private String orderstatus;
    private Long count;

    // Constructor to be used by Spring Data JPA @Query
    public OrderStatusCountDTO(String orderstatus, Long count) {
        this.orderstatus = orderstatus;
        this.count = count;
    }

    // Getters and Setters

    public OrderStatus getStatus() {
        return OrderStatus.valueOf(orderstatus);
    }

    public void setStatus(OrderStatus status) {
        this.orderstatus = status.name();
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
