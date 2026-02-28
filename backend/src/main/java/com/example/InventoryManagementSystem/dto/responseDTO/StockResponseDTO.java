package com.example.InventoryManagementSystem.dto.responseDTO;


import com.example.InventoryManagementSystem.enums.StockLevelStatus;

import java.time.LocalDate;

public class StockResponseDTO {

    private int productId;
    private String name;
    private int quantity;
    private StockLevelStatus status;
    private int reOrderLevel;
    private LocalDate lastUpdated;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public StockLevelStatus getStatus() {
        return status;
    }

    public void setStatus(StockLevelStatus status) {
        this.status = status;
    }

    public int getReOrderLevel() {
        return reOrderLevel;
    }

    public void setReOrderLevel(int reOrderLevel) {
        this.reOrderLevel = reOrderLevel;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
