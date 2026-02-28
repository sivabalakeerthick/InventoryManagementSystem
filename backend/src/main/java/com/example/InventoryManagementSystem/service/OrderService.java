package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.requestDTO.OrderRequestDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.OrderResponseDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.OrderUpdateResponseDTO;
import com.example.InventoryManagementSystem.enums.OrderStatus;
import com.example.InventoryManagementSystem.exception.OrderNotFoundException;
import com.example.InventoryManagementSystem.exception.ProductNotFoundException;
import com.example.InventoryManagementSystem.exception.OutOfStockException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Page<OrderResponseDTO> getAllOrders(OrderStatus orderStatus , String searchQuery ,  Pageable pageable);

    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) throws ProductNotFoundException, OutOfStockException;

    OrderUpdateResponseDTO updateOrder(Long id, OrderStatus orderStatus) throws OutOfStockException, ProductNotFoundException;

    void deleteOrder(Long id);

    OrderResponseDTO getOrderById(Long id) throws OrderNotFoundException;

    default boolean isStatusProgressionValid(OrderStatus current, OrderStatus requested) {
        if (current == OrderStatus.PENDING && requested == OrderStatus.SHIPPED) {
            return true;
        } else if (current == OrderStatus.SHIPPED && requested == OrderStatus.DELIVERED) {
            return true;
        }
        else if(requested == OrderStatus.CANCELLED){
            return true;
        }
        return false;
    }

}
