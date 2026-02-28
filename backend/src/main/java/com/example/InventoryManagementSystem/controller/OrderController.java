package com.example.InventoryManagementSystem.controller;

import com.example.InventoryManagementSystem.dto.requestDTO.OrderRequestDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.OrderResponseDTO;
import com.example.InventoryManagementSystem.dto.requestDTO.OrderUpdateRequestDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.OrderUpdateResponseDTO;
import com.example.InventoryManagementSystem.enums.OrderStatus;
import com.example.InventoryManagementSystem.exception.OrderNotFoundException;
import com.example.InventoryManagementSystem.exception.ProductNotFoundException;
import com.example.InventoryManagementSystem.exception.OutOfStockException;
import com.example.InventoryManagementSystem.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponseDTO>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false)OrderStatus orderStatus,
            @RequestParam(required = false)String searchQuery
            ) {
        Pageable pageable = PageRequest.of(page , size);
        return ResponseEntity.ok(orderService.getAllOrders(orderStatus , searchQuery , pageable));
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) throws ProductNotFoundException, OutOfStockException {
        return ResponseEntity.ok(orderService.createOrder(orderRequestDTO));

    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderUpdateResponseDTO> updateOrder(@PathVariable Long id, @RequestBody OrderUpdateRequestDTO request) throws OutOfStockException, ProductNotFoundException {
        return ResponseEntity.ok(orderService.updateOrder(id, request.getOrderStatus()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) throws OrderNotFoundException {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(Map.of("message" ,"Order deleted successfully"));

    }
}