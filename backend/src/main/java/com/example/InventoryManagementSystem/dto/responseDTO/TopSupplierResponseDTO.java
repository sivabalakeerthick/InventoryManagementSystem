package com.example.InventoryManagementSystem.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class TopSupplierResponseDTO {
    private String name;
    private Long totalProductsSupplied;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTotalProductsSupplied() {
        return totalProductsSupplied;
    }

    public void setTotalProductsSupplied(Long totalProductsSupplied) {
        this.totalProductsSupplied = totalProductsSupplied;
    }
}
