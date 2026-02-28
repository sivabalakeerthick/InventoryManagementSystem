package com.example.InventoryManagementSystem.repository;

import com.example.InventoryManagementSystem.dto.responseDTO.OrderStatusCountDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.ProductQuantityResponseDTO;
import com.example.InventoryManagementSystem.dto.responseDTO.TopSupplierResponseDTO;
import com.example.InventoryManagementSystem.entity.PurchaseOrder;
import com.example.InventoryManagementSystem.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    Page<PurchaseOrder> findByOrderStatusAndSupplier_NameStartingWithIgnoreCase(OrderStatus orderStatus , String name , Pageable pageable);

    Page<PurchaseOrder> findByOrderStatus(OrderStatus orderStatus , Pageable pageable);

    Page<PurchaseOrder> findBySupplier_NameStartingWithIgnoreCase(String name , Pageable pageable);
    //for purchase
    @Query(value = "SELECT * FROM purchase_orders WHERE purchased_date BETWEEN :startDate AND :endDate ORDER BY total_amount DESC LIMIT 5", nativeQuery = true)
    List<PurchaseOrder> findTopExpensivePurchaseOrders(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT sum(total_amount) FROM purchase_orders WHERE purchased_date BETWEEN :startDate AND :endDate ", nativeQuery = true)
    Double findTotalPurchaseAmount(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT count(purchase_order_id) FROM purchase_orders WHERE purchased_date BETWEEN :startDate AND :endDate ", nativeQuery = true)
    Long findTotalPurchaseOrders(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    //for inventory
    @Query(value = "select p.product_name ,sum(oi.quantity) from  purchase_order_item oi" +
            " JOIN purchase_orders o ON oi.purchase_order_id = o.purchase_order_id " +
            "JOIN products p ON oi.product_id = p.product_id" +
            " where o.purchased_date between :startDate and :endDate " +
            "group by product_name order by sum(oi.quantity) desc "+
            "LIMIT 5", nativeQuery = true)
    List<ProductQuantityResponseDTO> findPurchaseProductQuantity(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT sum(quantity) FROM purchase_order_item a join purchase_orders b on a.purchase_order_id=b.purchase_order_id WHERE purchased_date Between :startDate AND :endDate ", nativeQuery = true)
    Long findTotalPurchaseQuantity(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT s.name, COUNT(DISTINCT poi.product_id) AS total_products_supplied FROM purchase_orders po JOIN purchase_order_item poi ON po.purchase_order_id = poi.purchase_order_id JOIN products p ON poi.product_id = p.product_id JOIN supplier s ON po.supplier_id = s.supplier_id where po.purchased_date between :startDate and :endDate GROUP BY s.name ORDER BY total_products_supplied DESC", nativeQuery = true)
    List<TopSupplierResponseDTO> findTopSuppliers(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT po.order_status, COUNT(*) FROM purchase_orders po " +
            "GROUP BY po.order_status", nativeQuery = true)
    List<OrderStatusCountDTO> countPurchaseOrdersByStatus();
    Page<PurchaseOrder> findByPurchaseOrderId(Long searchId, PageRequest pageable);
}
