package com.example.InventoryManagementSystem.service.impl;

import com.example.InventoryManagementSystem.dto.responseDTO.LowStockResponseDTO;
import com.example.InventoryManagementSystem.dto.requestDTO.StockUpdateRequestDTO;
import com.example.InventoryManagementSystem.dto.requestDTO.StockCreateRequestDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.StockInfoDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.StockResponseDTO;
import com.example.InventoryManagementSystem.entity.Stock;
import com.example.InventoryManagementSystem.enums.OrderType;
import com.example.InventoryManagementSystem.enums.StockLevelStatus;
import com.example.InventoryManagementSystem.exception.OutOfStockException;
import com.example.InventoryManagementSystem.exception.ProductNotFoundException;
import com.example.InventoryManagementSystem.repository.ProductRepository;
import com.example.InventoryManagementSystem.repository.StockRepository;
import com.example.InventoryManagementSystem.service.StockService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;


    public Page<StockResponseDTO> getAllStock(String categoryName , String searchQuery , StockLevelStatus stockLevelStatus , Pageable pageable) {
        Page<Stock> page = stockRepository.findFilteredStocks(categoryName , searchQuery , stockLevelStatus , pageable);
        return page
                .map(stock ->
                        modelMapper.map(stock, StockResponseDTO.class));
    }

    public List<StockResponseDTO> getAllStockEntries(){
        return stockRepository.findAll().stream().map(
                stock -> modelMapper.map(stock , StockResponseDTO.class)
        ).toList();
    }

    @Transactional
    public StockResponseDTO addStock(StockCreateRequestDTO stockCreateRequestDTO) throws ProductNotFoundException {
        Stock newStock = new Stock();
        newStock.setProduct(stockCreateRequestDTO.getProduct());
        newStock.setLastUpdated(LocalDate.now());
        newStock.updateStockStatus();

        Stock createdStock = stockRepository.save(newStock);
        return modelMapper.map(createdStock , StockResponseDTO.class);
    }

    public StockResponseDTO getStockById(Long id) throws ProductNotFoundException {
        if (!stockRepository.findByProduct_productId(id).isPresent())
            throw new ProductNotFoundException("Product does not exist");
        return modelMapper.map(stockRepository.findByProduct_productId(id), StockResponseDTO.class);
    }

    @Transactional
    public StockResponseDTO updateStockLevel(Long id, StockUpdateRequestDTO stockUpdateRequestDTO) throws OutOfStockException, ProductNotFoundException {
        Optional<Stock> result = stockRepository.findByProduct_productId(id);

        Stock stock = null;
        if (result.isPresent())
            stock = result.get();
        else {
            if (stockUpdateRequestDTO.getOrderType() == OrderType.SALES) {
                throw new OutOfStockException("Product Unavailable");
            } else {
                stock = new Stock();
                stock.setProduct(productRepository.findById(id).get());
            }
        }

        int updatedQuantity = stock.getQuantity();

        if (stockUpdateRequestDTO.getOrderType() == OrderType.PURCHASE) {
            updatedQuantity += stockUpdateRequestDTO.getQuantity();
        } else if (stockUpdateRequestDTO.getOrderType() == OrderType.SALES) {
            if (stockUpdateRequestDTO.getQuantity() > stock.getQuantity())
                throw new OutOfStockException(stock.getProduct().getProductName() + " is Out of Stock !!!");
            updatedQuantity -= stockUpdateRequestDTO.getQuantity();
        }
        stock.setQuantity(updatedQuantity);
        stock.setLastUpdated(LocalDate.now());
        stock.updateStockStatus();

        return modelMapper.map(stockRepository.save(stock), StockResponseDTO.class);
    }

    public List<LowStockResponseDTO> getLowStockProducts() {
        return stockRepository.findLowStockProducts();
    }

    @Override
    public long getTotalProductsCount() {
        return stockRepository.countByProduct_ActiveTrue();
    }


    @Override
    public List<StockInfoDTO> countStockByStatus() {
        return stockRepository.countStockByStatus();
    }

    @Override
    public long countByStatus(StockLevelStatus status) {
        return stockRepository.countByStatus(status);
    }
}
