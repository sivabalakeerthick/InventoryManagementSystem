package com.example.InventoryManagementSystem.dto.requestDTO;

import com.example.InventoryManagementSystem.entity.Product;

public class StockCreateRequestDTO {

    private Product product;
    private int quantity;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
