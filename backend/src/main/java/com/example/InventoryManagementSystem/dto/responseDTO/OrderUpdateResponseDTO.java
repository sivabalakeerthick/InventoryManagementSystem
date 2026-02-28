package com.example.InventoryManagementSystem.dto.responseDTO;

import com.example.InventoryManagementSystem.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateResponseDTO {
    private Long orderId;
    private String customerName;
    private String customerEmail;
    private LocalDate date;
    private OrderStatus orderStatus;
}
