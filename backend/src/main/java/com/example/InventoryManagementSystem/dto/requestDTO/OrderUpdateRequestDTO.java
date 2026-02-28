package com.example.InventoryManagementSystem.dto.requestDTO;

import com.example.InventoryManagementSystem.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class OrderUpdateRequestDTO {

    // private  Long orderId;

    private OrderStatus orderStatus;


    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }


}
