package com.example.InventoryManagementSystem.repository;

import com.example.InventoryManagementSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.InventoryManagementSystem.enums.UserRole;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUserRole(UserRole userRole);

    Optional<User> findByEmail(String email);
}