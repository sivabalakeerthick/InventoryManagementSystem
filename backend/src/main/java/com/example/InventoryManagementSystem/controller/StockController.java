package com.example.InventoryManagementSystem.controller;

import com.example.InventoryManagementSystem.dto.requestDTO.StockUpdateRequestDTO;
import com.example.InventoryManagementSystem.dto.requestDTO.StockCreateRequestDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.StockInfoDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.StockResponseDTO;
import com.example.InventoryManagementSystem.enums.StockLevelStatus;
import com.example.InventoryManagementSystem.exception.OutOfStockException;
import com.example.InventoryManagementSystem.exception.ProductNotFoundException;
import com.example.InventoryManagementSystem.service.impl.StockServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import com.example.InventoryManagementSystem.dto.responseDTO.LowStockResponseDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stock")
public class StockController {
    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private StockServiceImpl stockServiceImpl;

    @GetMapping
    public ResponseEntity<Page<StockResponseDTO>> getAllStocks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String searchQuery,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false)StockLevelStatus stockLevelStatus,
            @RequestParam(defaultValue = "quantity") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder
            ) {
        Pageable pageable = PageRequest.of(page , size , Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        logger.info("Fetching all stocks");
        return ResponseEntity.ok(stockServiceImpl.getAllStock(categoryName , searchQuery , stockLevelStatus , pageable));
    }

    @GetMapping("/all")
    public ResponseEntity<List<StockResponseDTO>> getAllStockEntries(){
        return ResponseEntity.ok(stockServiceImpl.getAllStockEntries());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<StockResponseDTO> addStock(@RequestBody StockCreateRequestDTO stockCreateRequestDTO) throws ProductNotFoundException {
        return ResponseEntity.ok(stockServiceImpl.addStock(stockCreateRequestDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<StockResponseDTO> getStockById(@PathVariable Long id) throws ProductNotFoundException {
        logger.info("Getting stock by product Id: {} ", id);
        return ResponseEntity.ok(stockServiceImpl.getStockById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<StockResponseDTO> updateStockLevel(@PathVariable Long id, @RequestBody StockUpdateRequestDTO stockUpdateRequestDTO) throws OutOfStockException, ProductNotFoundException {
        StockResponseDTO updatedStock = stockServiceImpl.updateStockLevel(id, stockUpdateRequestDTO);
        return ResponseEntity.ok(updatedStock);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/lowStockProducts")
    public ResponseEntity<List<LowStockResponseDTO>> getLowStockProducts() {
        return ResponseEntity.ok(stockServiceImpl.getLowStockProducts());
    }


    @GetMapping("/totalProducts")
    public ResponseEntity<?> getTotalProductsCount(){
        return ResponseEntity.ok(Map.of("totalProducts" , stockServiceImpl.getTotalProductsCount()));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getProductsCountByStatus")
    public ResponseEntity<List<StockInfoDTO>> countStockByStatus() {
        return ResponseEntity.ok(stockServiceImpl.countStockByStatus());
    }

    @GetMapping("/getCountByStatus")
    public ResponseEntity<?> getProductsByStatus(
            @RequestParam StockLevelStatus status
    ){
        return ResponseEntity.ok(Map.of( "count", stockServiceImpl.countByStatus(status)));
    }
}
