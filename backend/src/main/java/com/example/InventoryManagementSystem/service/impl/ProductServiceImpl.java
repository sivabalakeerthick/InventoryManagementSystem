package com.example.InventoryManagementSystem.service.impl;

import com.example.InventoryManagementSystem.dto.requestDTO.ProductRequestDto;
import com.example.InventoryManagementSystem.dto.responseDTO.ProductResponseDto;
import com.example.InventoryManagementSystem.entity.Category;
import com.example.InventoryManagementSystem.entity.Product;
import com.example.InventoryManagementSystem.entity.Stock;
import com.example.InventoryManagementSystem.exception.ProductAlreadyExistException;
import com.example.InventoryManagementSystem.exception.ProductNotFoundException;
import com.example.InventoryManagementSystem.repository.*;
import com.example.InventoryManagementSystem.service.ProductService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private PurchaseOrderItemRepository purchaseOrderItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ProductResponseDto> getAll(){
        return productRepository.findAll().stream().map(product -> modelMapper.map(product, ProductResponseDto.class))
                .toList();
    }


    public Page<ProductResponseDto> getAllProducts(Pageable pageable , String categoryName , String searchQuery) {
        Page<Product> productPage = null;
        System.out.println(categoryName);
        if((categoryName == null || categoryName.isEmpty()) && (searchQuery ==null ||
                searchQuery.isEmpty())) {
            System.out.println("Running 1");
            productPage = productRepository.findByActiveTrue(pageable);
        }
        else if(!categoryName.isEmpty() && !searchQuery.isEmpty()){
            System.out.println("Running 2");
            productPage = productRepository.findByCategory_CategoryNameAndProductNameStartingWithIgnoreCaseAndActiveTrue(categoryName , searchQuery , pageable);
        }
        else if(!categoryName.isEmpty()){
            System.out.println("Running 3");
            productPage = productRepository.findByCategory_CategoryNameAndActiveTrue(categoryName , pageable);
        }
        else {
            System.out.println("Running 4");
            productPage = productRepository.findByProductNameStartingWithIgnoreCaseAndActiveTrue(searchQuery , pageable);
        }

        return productPage.map(product -> modelMapper.map(product, ProductResponseDto.class));
    }

    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws ProductAlreadyExistException {
        if (productRepository.findByProductNameAndActiveTrue(productRequestDto.getProductName()).isPresent()) {
            throw new ProductAlreadyExistException("Product already exists!");
        }
        Product product = modelMapper.map(productRequestDto, Product.class);
        Category category = categoryRepository.findByCategoryName(productRequestDto.getCategoryName()).orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);
        ProductResponseDto productResponseDto = modelMapper.map(productRepository.save(product), ProductResponseDto.class);
        return productResponseDto;
    }

    public ProductResponseDto getProductById(Long id) throws ProductNotFoundException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        return modelMapper.map(product, ProductResponseDto.class);
    }

    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto) throws ProductNotFoundException {
        Product exist = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        // Update only non-null fields
        if (productRequestDto.getProductName() != null) {
            exist.setProductName(productRequestDto.getProductName());
        }
        if (productRequestDto.getUnitPrice() > 0) {
            exist.setUnitPrice(productRequestDto.getUnitPrice());
        }
        if (productRequestDto.getDescription() != null) {
            exist.setDescription(productRequestDto.getDescription());
        }
        if (productRequestDto.getReOrderLevel() != null) {
            exist.setReOrderLevel(productRequestDto.getReOrderLevel());
        }
        if (productRequestDto.getCategoryName() != null) {
            exist.setCategory(categoryRepository.findByCategoryName(productRequestDto.getCategoryName()).get());
        }

        // Save and return the updated product
        return modelMapper.map(productRepository.save(exist), ProductResponseDto.class);
    }

    @Transactional
    public void deleteProduct(Long id) throws ProductNotFoundException {
        Optional<Product> result = productRepository.findById(id);

        Optional<Stock> stock = stockRepository.findByProduct_productId(id);
        if(result.isEmpty())
            throw new ProductNotFoundException("Product Not Found with Id : " + id);
        boolean isProductInOrders = orderItemRepository.existsByProduct_ProductId(id);
        boolean isProductInPurchaseOrders = purchaseOrderItemRepository.existsByProduct_ProductId(id);
        Product product = result.get();
        if(isProductInOrders || isProductInPurchaseOrders){
            product.setActive(false);
            productRepository.save(product);
            if(stock.isPresent()) {

                stock.get().updateStockStatus();
                stockRepository.save(stock.get());
            }
        }else {
            productRepository.deleteById(id);
        }
    }

    public Page<ProductResponseDto> getProductsByCategoryName(String categoryName , Pageable pageable) {
        return productRepository.findByCategory_CategoryNameAndActiveTrue(categoryName , pageable).map(product -> modelMapper.map(product, ProductResponseDto.class));
    }


}
