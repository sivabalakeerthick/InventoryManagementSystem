package com.example.InventoryManagementSystem.service;

import com.example.InventoryManagementSystem.entity.Category;
import com.example.InventoryManagementSystem.exception.CategoryAlreadyExistException;

import java.util.List;

public interface CategoryService {
    String addCategory(String categoryName) throws CategoryAlreadyExistException;

    List<Category> getAllCategories();
}
