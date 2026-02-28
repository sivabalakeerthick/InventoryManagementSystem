package com.example.InventoryManagementSystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.InventoryManagementSystem.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //  List<Product> findByCategoryId(Long categoryId);
    Page<Product> findByCategory_CategoryNameAndActiveTrue(String CategoryName , Pageable pageable);

    Optional<Product> findByProductNameAndActiveTrue(String productName);

    Page<Product> findByCategory_CategoryNameAndProductNameStartingWithIgnoreCaseAndActiveTrue(String categoryName ,
                                                                   String searchQuery,
                                                                   Pageable pageable);

    Page<Product> findByProductNameStartingWithIgnoreCaseAndActiveTrue(String searchQuery , Pageable pageable);



    Page<Product> findByActiveTrue(Pageable pageable);
}
