/**
 * Grocery tracker backend - product model class
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Class: This class serves as model for the product entity.
 */

package com.carmen.GroceryTracker.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "Products")
public class Product {

    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    //IMAGE
    @Column(name = "image_url")
    private String imageUrl;

    //NAME
    @Column(name = "product_name", nullable = false)
    private String productName;

    //CATEGORY
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    @JsonBackReference(value = "product-category")  // Prevent circular reference
    @JsonIgnoreProperties("Products")  // Prevent recursion if I add a products list in Category
    private Category productCategory;

    //PRICE
    @Column(name = "product_price")
    private BigDecimal productPrice;

    //QUANTITY
    @Column(name = "stock_quantity")
    private int stockQuantity;

    //SALES
    @OneToMany(mappedBy = "product")
    @JsonManagedReference(value = "product-sales")  // Prevent circular reference
    private Set<Sale> sales;

    //LOCATION
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    @JsonIgnoreProperties("products")  // Prevent recursion if I add a products list in Location
    @JsonBackReference(value = "product-location")  // Prevent circular reference
    private Location location;


    //constructor
    public Product(String imageUrl, String productName, Category productCategory, BigDecimal productPrice, int stockQuantity, Set<Sale> sales, Location location) {
        this.imageUrl = imageUrl;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.stockQuantity = stockQuantity;
        this.sales = sales;
        this.location = location;
    }

    public Product() {

    }
    ////////////////////// getters and setters
    public Long getProductId() {
        return productId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setProductId(Long id) {
        this.productId = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String name) {
        this.productName = name;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal price) {
        this.productPrice = price;
    }

    public Category getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Category productCategory) {
        this.productCategory = productCategory;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
