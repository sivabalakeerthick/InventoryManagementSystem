package com.example.InventoryManagementSystem.repository;

import com.example.InventoryManagementSystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCustomerEmail(String customerEmail);
}

