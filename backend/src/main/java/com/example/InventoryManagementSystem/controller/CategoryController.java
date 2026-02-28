package com.example.InventoryManagementSystem.controller;

import com.example.InventoryManagementSystem.entity.Category;
import com.example.InventoryManagementSystem.exception.CategoryAlreadyExistException;
import com.example.InventoryManagementSystem.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody Map<String, String> request) {
        try {
            String categoryName = request.get("categoryName");

            if (categoryName == null || categoryName.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message" , "Category name cannot be empty."));
            }

            return ResponseEntity.ok(Map.of("message" , categoryService.addCategory(categoryName)));
        } catch (CategoryAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the category.");
        }
    }


}

