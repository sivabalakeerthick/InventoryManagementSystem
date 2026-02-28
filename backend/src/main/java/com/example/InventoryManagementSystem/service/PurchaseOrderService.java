package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.requestDTO.PurchaseOrderRequestDTO;
import com.example.InventoryManagementSystem.dto.requestDTO.PurchaseOrderUpdateRequestDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.PurchaseOrderResponseDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.PurchaseOrderResponseUpdateDTO;
import com.example.InventoryManagementSystem.enums.OrderStatus;
import com.example.InventoryManagementSystem.exception.ProductNotFoundException;
import com.example.InventoryManagementSystem.exception.OutOfStockException;
import org.springframework.data.domain.Page; // Import Spring Data Page

import java.util.List;

public interface PurchaseOrderService {

    PurchaseOrderResponseDTO addPurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO) throws ProductNotFoundException, OutOfStockException;

    PurchaseOrderResponseUpdateDTO updatePurchaseOrder(Long id, PurchaseOrderUpdateRequestDTO purchaseOrderUpdateRequestDTO) throws OutOfStockException, ProductNotFoundException;

    Page<PurchaseOrderResponseDTO> getAllPurchaseOrders(int page, int size, String sortBy, String sortOrder, OrderStatus orderStatus , String searchQuery);

    PurchaseOrderResponseDTO getPurchaseOrderById(Long id);

    void deletePurchaseOrder(Long id);

    default boolean isStatusProgressionValid(OrderStatus current, OrderStatus requested) {
        if (current == OrderStatus.PENDING && requested == OrderStatus.SHIPPED) {
            return true;
        } else if (current == OrderStatus.SHIPPED && requested == OrderStatus.DELIVERED) {
            return true;
        }
        else if(requested == OrderStatus.CANCELLED){
            return true;
        }
        else if (current == OrderStatus.CANCELLED) {
            return false;
        }
        return false;
    }
}