package com.example.InventoryManagementSystem.controller;

import com.example.InventoryManagementSystem.dto.requestDTO.ProductRequestDto;
import com.example.InventoryManagementSystem.dto.responseDTO.ProductResponseDto;
import com.example.InventoryManagementSystem.entity.Product;
import com.example.InventoryManagementSystem.exception.ProductNotFoundException;
import com.example.InventoryManagementSystem.service.impl.ProductServiceImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ModelMapper modelMapper;
    private ProductRequestDto productRequestDto;
    private ProductResponseDto productResponseDto;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public void setProductServiceImpl(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @GetMapping("getAll")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(){
        return ResponseEntity.ok(productServiceImpl.getAll());
    }

    @GetMapping("/filterProducts")
    public ResponseEntity<Page<ProductResponseDto>> getFilteredProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "productName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String searchQuery) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Page<ProductResponseDto> responsePage = productServiceImpl.getAllProducts(pageable , categoryName , searchQuery);
        return ResponseEntity.ok(responsePage);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ProductResponseDto> addProduct(@RequestBody ProductRequestDto productDto) {
        logger.info("Creating a new product: {}", productRequestDto);
        return ResponseEntity.ok(productServiceImpl.addProduct(productDto));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDto productRequestDto) throws ProductNotFoundException {
        // Ensure ID remains consistent
        return ResponseEntity.ok(productServiceImpl.updateProduct(id, productRequestDto));
    }

    @GetMapping("get/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) throws ProductNotFoundException {
        return ResponseEntity.ok(productServiceImpl.getProductById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        productServiceImpl.deleteProduct(id);
        return ResponseEntity.ok(Map.of("Message", "Product deleted successfully"));

    }


//    @GetMapping("/by-category")
//    public Page<ProductResponseDto> getProductsByCategory(
//            @RequestParam(required = false) String categoryName,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size
//
//    ) {
//        Pageable pageable = PageRequest.of(page , size);
//        if(categoryName == null){
//            return productServiceImpl.getAllProducts(pageable);
//        }
//        else{
//            return productServiceImpl.getProductsByCategoryName(categoryName , pageable);
//        }
//
//    }

}
