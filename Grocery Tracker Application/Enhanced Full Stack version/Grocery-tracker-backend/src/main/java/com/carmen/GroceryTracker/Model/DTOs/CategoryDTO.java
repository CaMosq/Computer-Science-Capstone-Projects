/**
 * Grocery Tracker - CategoryDTO (Data Transfer Object)
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: This class controls the data sent to the frontend
 * and avoids any issues with lazy loading or infinite recursion.
 * With this class I can control what information about the category
 * I want to send to the frontend.
 */
package com.carmen.GroceryTracker.Model.DTOs;
import com.carmen.GroceryTracker.Model.Product;

import java.util.Set;

public class CategoryDTO {

    private Long id;
    private String name;
    private Set<Product> products;

    // Constructor
    public CategoryDTO(Long id, String name, Set<Product> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }


    /////////////Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProductDTOS(Set<Product> products) {
        this.products = products;
    }
}