package com.example.InventoryManagementSystem.dto.responseDTO;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LowStockResponseDTO {
    private Long productId;
    private String productName;
    private int quantity;
    private int reOrderLevel;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getReOrderLevel() {
        return reOrderLevel;
    }

    public void setReOrderLevel(int reOrderLevel) {
        this.reOrderLevel = reOrderLevel;
    }

    public LowStockResponseDTO(Long productId, String productName, Integer quantity, Integer reOrderLevel) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.reOrderLevel = reOrderLevel;
    }
}
