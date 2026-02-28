package com.example.InventoryManagementSystem.repository;

import com.example.InventoryManagementSystem.dto.responseDTO.OrderStatusCountDTO;
import com.example.InventoryManagementSystem.entity.Order;
import com.example.InventoryManagementSystem.dto.responseDTO.ProductQuantityResponseDTO;
import com.example.InventoryManagementSystem.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByOrderStatus(OrderStatus orderStatus , Pageable pageable);
    Page<Order> findByCustomer_CustomerNameStartingWithIgnoreCase(String name , Pageable pageable);

    Page<Order> findByOrderStatusAndCustomer_CustomerNameStartingWithIgnoreCase(OrderStatus orderStatus, String name ,Pageable pageable);

    //for order
    @Query(value = "SELECT * FROM orders WHERE date BETWEEN :startDate AND :endDate ORDER BY total_price DESC LIMIT 5", nativeQuery = true)
    List<Order> findTopExpensiveOrders(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT sum(total_price) FROM orders WHERE date BETWEEN :startDate AND :endDate ", nativeQuery = true)
    Double findTotalAmount(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT count(order_id) FROM orders WHERE date BETWEEN :startDate AND :endDate ", nativeQuery = true)
    Long findTotalOrders(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    //for inventory
    @Query(value = "select p.product_name ,sum(oi.quantity) from  order_item oi " +
            "JOIN orders o ON oi.order_id = o.order_id " +
            "JOIN products p ON oi.product_id = p.product_id" +
            " where o.date between :startDate and :endDate " +
            "group by product_name order by sum(oi.quantity) desc "+
            "LIMIT 5", nativeQuery = true)
    List<ProductQuantityResponseDTO> findOrderProductQuantity(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT sum(quantity)" +
            " FROM order_item a join orders b on a.order_id=b.order_id " +
            "WHERE date Between :startDate AND :endDate ", nativeQuery = true)
    Long findTotalOrderQuantity(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT o.order_status, COUNT(*) FROM orders o " +
            "GROUP BY o.order_status", nativeQuery = true)
    List<OrderStatusCountDTO> countOrdersByStatus();

    Page<Order> findAll(Pageable pageable);
}