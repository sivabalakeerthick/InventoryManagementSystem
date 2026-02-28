package com.example.InventoryManagementSystem.controller;

import com.example.InventoryManagementSystem.dto.requestDTO.PurchaseOrderUpdateRequestDTO;
import com.example.InventoryManagementSystem.dto.requestDTO.PurchaseOrderRequestDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.PurchaseOrderResponseDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.PurchaseOrderResponseUpdateDTO;
import com.example.InventoryManagementSystem.enums.OrderStatus;
import com.example.InventoryManagementSystem.exception.OutOfStockException;
import com.example.InventoryManagementSystem.exception.ProductNotFoundException;
import com.example.InventoryManagementSystem.exception.PurchaseOrderNotFoundException;
import com.example.InventoryManagementSystem.service.impl.PurchaseOrderServiceImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/purchaseOrder")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderServiceImpl purchaseOrderService;

    @Autowired
    private ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderController.class);

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Purchase Order service is working!");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<PurchaseOrderResponseDTO>> getAllPurchaseOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "purchaseOrderId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(required = false) OrderStatus orderStatus,
            @RequestParam(required = false) String searchQuery
    ) {
        logger.info("Fetching purchase orders with page: {}, size: {}, sortBy: {}, sortOrder: {}, searchId: {}", page, size, sortBy, sortOrder, searchQuery);
        return ResponseEntity.ok(purchaseOrderService.getAllPurchaseOrders(page, size, sortBy, sortOrder, orderStatus,  searchQuery));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PurchaseOrderResponseDTO> createPurchaseOrder(@RequestBody PurchaseOrderRequestDTO purchaseOrderRequestDTO) throws PurchaseOrderNotFoundException, ProductNotFoundException, OutOfStockException {
        logger.info("Creating a new purchase order: {}", purchaseOrderRequestDTO);
        return ResponseEntity.ok(purchaseOrderService.addPurchaseOrder(purchaseOrderRequestDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrderResponseUpdateDTO> updatePurchaseOrder(@PathVariable Long id, @RequestBody PurchaseOrderUpdateRequestDTO purchaseOrderUpdateRequestDTO) throws OutOfStockException, ProductNotFoundException {
        return ResponseEntity.ok(purchaseOrderService.updatePurchaseOrder(id, purchaseOrderUpdateRequestDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderResponseDTO> getPurchaseOrderById(@PathVariable Long id) throws PurchaseOrderNotFoundException {
        return ResponseEntity.ok(purchaseOrderService.getPurchaseOrderById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePurchaseOrder(@PathVariable Long id) {
        purchaseOrderService.deletePurchaseOrder(id);
        return ResponseEntity.ok("Purchase Order deleted successfully");
    }
}