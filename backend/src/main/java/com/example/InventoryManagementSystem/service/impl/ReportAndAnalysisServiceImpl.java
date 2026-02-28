package com.example.InventoryManagementSystem.service.impl;

import com.example.InventoryManagementSystem.dto.responseDTO.OrderStatusCountDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.TopSupplierResponseDTO;
import com.example.InventoryManagementSystem.entity.Order;
import com.example.InventoryManagementSystem.dto.responseDTO.ProductQuantityResponseDTO;
import com.example.InventoryManagementSystem.entity.PurchaseOrder;
import com.example.InventoryManagementSystem.repository.OrderRepository;
import com.example.InventoryManagementSystem.repository.PurchaseOrderRepository;
import com.example.InventoryManagementSystem.repository.StockRepository;
import com.example.InventoryManagementSystem.repository.SupplierRepository;
import com.example.InventoryManagementSystem.service.ReportAndAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportAndAnalysisServiceImpl implements ReportAndAnalysisService {


    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private SupplierRepository supplierRepository;


    @Override
    public List<Order> findTopExpensiveOrders(LocalDate startDate , LocalDate endDate) {
        System.out.println("at service impl");
        return orderRepository.findTopExpensiveOrders(startDate,endDate);
    }

    @Override
    public Double findTotalAmount(LocalDate startDate , LocalDate endDate) {
        return orderRepository.findTotalAmount(startDate,endDate);
    }
    @Override
    public Long findTotalOrders(LocalDate startDate , LocalDate endDate) {
        return orderRepository.findTotalOrders(startDate,endDate);
    }

    @Override
    public List<PurchaseOrder> findTopExpensivePurchaseOrders(LocalDate startDate , LocalDate endDate) {
        return purchaseOrderRepository.findTopExpensivePurchaseOrders(startDate,endDate);
    }

    @Override
    public Double findTotalPurchaseAmount(LocalDate startDate , LocalDate endDate) {
        return purchaseOrderRepository.findTotalPurchaseAmount(startDate,endDate);
    }

    @Override
    public Long findTotalPurchaseOrders(LocalDate startDate , LocalDate endDate) {
        return purchaseOrderRepository.findTotalPurchaseOrders(startDate,endDate);
    }

    @Override
    public List<ProductQuantityResponseDTO> findOrderProductQuantity(LocalDate startDate , LocalDate endDate) {
        return orderRepository.findOrderProductQuantity(startDate,endDate);
    }

    @Override
    public List<ProductQuantityResponseDTO> findPurchaseProductQuantity(LocalDate startDate , LocalDate endDate) {
        return purchaseOrderRepository.findPurchaseProductQuantity(startDate,endDate);
    }

    @Override
    public Long findTotalPurchaseQuantity(LocalDate startDate , LocalDate endDate) {
        return purchaseOrderRepository.findTotalPurchaseQuantity(startDate,endDate);
    }


    @Override
    public Long findTotalOrderQuantity(LocalDate startDate , LocalDate endDate) {
        return orderRepository.findTotalOrderQuantity(startDate,endDate);
    }

    @Override
    public List<TopSupplierResponseDTO> findTopSuppliers(LocalDate startDate , LocalDate endDate) {
        return purchaseOrderRepository.findTopSuppliers(startDate,endDate);
    }

    @Override
    public List<OrderStatusCountDTO> countOrdersByStatus(){
        return orderRepository.countOrdersByStatus();
    }

    @Override
    public List<OrderStatusCountDTO> countPurchaseOrdersByStatus(){
        return purchaseOrderRepository.countPurchaseOrdersByStatus();
    }

    @Override
    public Double getTotalStockValue(){
        return stockRepository.getTotalStockValue();
    }

    @Override
    public long getTotalsuppliers(){
        return supplierRepository.count();
    }


}
