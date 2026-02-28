package com.example.InventoryManagementSystem.dto.responseDTO;

public class StockInfoDTO {

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    private String status;
    private Long count;

    public StockInfoDTO(String status, Long count) {
        this.status = status;
        this.count = count;
    }





}
