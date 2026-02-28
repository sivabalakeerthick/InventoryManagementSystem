package com.example.InventoryManagementSystem.dto.responseDTO;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor

public class ProductQuantityResponseDTO {
    private String productName;
    private Long totalQuantity;

    public ProductQuantityResponseDTO(String productName, BigDecimal totalQuantity) {
        this.productName = productName;
        this.totalQuantity = totalQuantity.longValue(); // Convert safely
    }

    public String getProductName() {
        return productName;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }
}
