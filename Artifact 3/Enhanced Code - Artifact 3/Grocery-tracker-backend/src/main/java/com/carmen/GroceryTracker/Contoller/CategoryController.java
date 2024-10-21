/**
 * Grocery Tracker Backend Application - Category controller
 * Version 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Class: This class serves as controller for the category entity,
 * It implements the backend endpoints GET, POST, PUT, DELETE to communicate with the database
 */
package com.carmen.GroceryTracker.Contoller;


import com.carmen.GroceryTracker.Model.Category;
import com.carmen.GroceryTracker.Model.DTOs.CategoryDTO;
import com.carmen.GroceryTracker.Repository.CategoryRepository;
import com.carmen.GroceryTracker.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    //GET
    @GetMapping
    public Iterable<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories();
    }

    //POST
    @PostMapping("/add")
    public Category createCategory(@RequestBody CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO.getName());
        return categoryRepository.save(category);
    }

    //GET
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //PUT
    @PutMapping("/{id}") //categories can only update the name attribute.
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        return categoryRepository.findById(id).map(category -> {
            category.setName(categoryDTO.getName()); //only update name
            return ResponseEntity.ok(categoryRepository.save(category));
        }).orElse(ResponseEntity.notFound().build());
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        return categoryRepository.findById(id).map(category -> {
            categoryRepository.delete(category);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
