package com.example.InventoryManagementSystem.dto.requestDTO;

import com.example.InventoryManagementSystem.entity.Category;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequestDTO {
    private String productName;
    //    private Long productId;
    private double unitPrice;
    private String description;
    private Integer reOrderLevel;
    private Category category;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReOrderLevel() {
        return reOrderLevel;
    }

    public void setReOrderLevel(Integer reOrderLevel) {
        this.reOrderLevel = reOrderLevel;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
