package com.example.InventoryManagementSystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.InventoryManagementSystem.entity.Supplier;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    long count();
    Optional<Supplier> findByName(String name);

    Page<Supplier> findByNameStartingWithIgnoreCase(String name , Pageable pageable);
}
