package com.example.InventoryManagementSystem.repository;

import com.example.InventoryManagementSystem.entity.PurchaseOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem , Long> {
    boolean existsByProduct_ProductId(Long productId);
}
