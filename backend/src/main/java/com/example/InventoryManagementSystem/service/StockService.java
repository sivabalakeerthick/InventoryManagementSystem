package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.requestDTO.StockUpdateRequestDTO;
import com.example.InventoryManagementSystem.dto.requestDTO.StockCreateRequestDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.StockInfoDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.StockResponseDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.LowStockResponseDTO;
import com.example.InventoryManagementSystem.enums.StockLevelStatus;
import com.example.InventoryManagementSystem.exception.OutOfStockException;
import com.example.InventoryManagementSystem.exception.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StockService {

    Page<StockResponseDTO> getAllStock(String categoryName , String searchQuery , StockLevelStatus stockLevelStatus , Pageable pageable);

    List<StockResponseDTO> getAllStockEntries();

    StockResponseDTO addStock(StockCreateRequestDTO stockCreateRequestDTO) throws ProductNotFoundException;

    StockResponseDTO getStockById(Long id) throws ProductNotFoundException;

    StockResponseDTO updateStockLevel(Long id, StockUpdateRequestDTO stockUpdateRequestDTO) throws OutOfStockException, ProductNotFoundException;

    List<LowStockResponseDTO> getLowStockProducts();

    long getTotalProductsCount();

    List<StockInfoDTO> countStockByStatus();

    long countByStatus(StockLevelStatus status);



}
