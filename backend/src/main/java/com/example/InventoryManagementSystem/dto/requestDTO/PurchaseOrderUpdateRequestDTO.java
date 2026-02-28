package com.example.InventoryManagementSystem.dto.requestDTO;

import com.example.InventoryManagementSystem.enums.OrderStatus;

public class PurchaseOrderUpdateRequestDTO {

    private OrderStatus orderStatus;


    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
