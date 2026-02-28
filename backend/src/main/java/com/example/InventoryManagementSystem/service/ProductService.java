package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.dto.requestDTO.ProductRequestDto;
import com.example.InventoryManagementSystem.dto.responseDTO.ProductResponseDto;
import com.example.InventoryManagementSystem.entity.Product;
import com.example.InventoryManagementSystem.exception.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface ProductService {

    Page<ProductResponseDto> getAllProducts(Pageable pageable , String categoryName , String searchQuery);

    ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws ProductNotFoundException;

    ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto) throws ProductNotFoundException;

    void deleteProduct(Long id) throws ProductNotFoundException;

    ProductResponseDto getProductById(Long id) throws ProductNotFoundException;

    Page<ProductResponseDto> getProductsByCategoryName(String categoryName , Pageable pageable);
}
