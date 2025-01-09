/**
 * Grocery Tracker - ProductDTO (Data Transfer Object)
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: This class controls the data sent to the frontend
 * and avoids any issues with lazy loading or infinite recursion.
 * With this class I can control what information about the product
 * I want to send to the frontend. for example, here I'm not sending the
 * sales object with the product object.
 */
package com.carmen.GroceryTracker.Model.DTOs;

import java.math.BigDecimal;

public class ProductDTO {

    private Long productId;
    private String imageUrl;
    private String productName;
    private CategoryDTO productCategory;
    private BigDecimal productPrice;
    private int stockQuantity;
    private LocationDTO location;

    // Constructor
    public ProductDTO(Long productId, String imageUrl, String productName, CategoryDTO productCategory, BigDecimal productPrice, int stockQuantity, LocationDTO location) {
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.stockQuantity = stockQuantity;
        this.location = location;
    }

    public ProductDTO(Long productId, String imageUrl, String productName) {
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.productName = productName;
    }

    /////// Getters and setters

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public CategoryDTO getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(CategoryDTO productCategory) {
        this.productCategory = productCategory;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }
}