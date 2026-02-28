
package com.example.InventoryManagementSystem.service.impl;

import com.example.InventoryManagementSystem.entity.Category;
import com.example.InventoryManagementSystem.exception.CategoryAlreadyExistException;
import com.example.InventoryManagementSystem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.InventoryManagementSystem.repository.CategoryRepository;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public String addCategory(String categoryName) throws CategoryAlreadyExistException {
        if (categoryRepository.findByCategoryName(categoryName).isPresent()) {
            throw new CategoryAlreadyExistException("Category already exists!");
        }

        Category category = new Category();
        category.setCategoryName(categoryName);
        categoryRepository.save(category);

        return "Category '" + categoryName + "' added successfully!";
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

}
