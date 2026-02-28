package com.example.InventoryManagementSystem.repository;

import com.example.InventoryManagementSystem.dto.responseDTO.LowStockResponseDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.OrderStatusCountDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.StockInfoDTO;
import com.example.InventoryManagementSystem.enums.StockLevelStatus;
import com.example.InventoryManagementSystem.enums.StockLevelStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.InventoryManagementSystem.entity.Stock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByProduct_productId(Long productId);

    @Query(value = "select p.product_id , p.product_name , s.quantity , p.re_order_level from stock s join products p on s.product_id = p.product_id where s.quantity <= p.re_order_level and s.quantity > 0 and p.active;", nativeQuery = true)
    List<LowStockResponseDTO> findLowStockProducts();

    @Query(value = "SELECT SUM(s.quantity * p.unit_price) " +
            "FROM stock s " +
            "JOIN products p ON s.product_id = p.product_id " +
            "WHERE s.quantity > 0 ", nativeQuery = true)
    Double getTotalStockValue();

    @Query(value = "SELECT s.status, COUNT(*) FROM stock s " +
            "GROUP BY s.status", nativeQuery = true)
    List<StockInfoDTO> countStockByStatus();

    long countByProduct_ActiveTrue();

    long countByStatus(StockLevelStatus status);


    @Query("SELECT s FROM Stock s " +
            "JOIN s.product p " +
            "JOIN p.category c " +
            "WHERE (:search IS NULL OR p.productName LIKE CONCAT(:search, '%')) AND " +
            "(:category IS NULL OR c.categoryName = :category) AND " +
            "(:status IS NULL OR s.status = :status) AND s.status <> NOT_AVAILABLE")
    Page<Stock> findFilteredStocks(
            @Param("category") String category,
            @Param("search") String search,
            @Param("status") StockLevelStatus status,
            Pageable pageable
    );
}
