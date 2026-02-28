package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.responseDTO.OrderStatusCountDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.StockInfoDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.TopSupplierResponseDTO;
import com.example.InventoryManagementSystem.entity.Order;
import com.example.InventoryManagementSystem.dto.responseDTO.ProductQuantityResponseDTO;
import com.example.InventoryManagementSystem.entity.PurchaseOrder;
import com.example.InventoryManagementSystem.dto.requestDTO.ReportAndAnalysisDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReportAndAnalysisService {
    List<Order> findTopExpensiveOrders(LocalDate startDate , LocalDate endDate);

     Double findTotalAmount(LocalDate startDate , LocalDate endDate);

    Long findTotalOrders(LocalDate startDate , LocalDate endDate);

    List<PurchaseOrder> findTopExpensivePurchaseOrders(LocalDate startDate , LocalDate endDate);

    Double findTotalPurchaseAmount(LocalDate startDate , LocalDate endDate);

    Long findTotalPurchaseOrders(LocalDate startDate , LocalDate endDate);

    List<ProductQuantityResponseDTO> findOrderProductQuantity(LocalDate startDate , LocalDate endDate);

    List<ProductQuantityResponseDTO> findPurchaseProductQuantity(LocalDate startDate , LocalDate endDate);

    Long findTotalPurchaseQuantity(LocalDate startDate , LocalDate endDate);

    Long findTotalOrderQuantity(LocalDate startDate , LocalDate endDate);

    List<TopSupplierResponseDTO> findTopSuppliers(LocalDate startDate , LocalDate endDate);

    List<OrderStatusCountDTO> countOrdersByStatus();

    Double getTotalStockValue();

    List<OrderStatusCountDTO> countPurchaseOrdersByStatus();

    long getTotalsuppliers();
}
