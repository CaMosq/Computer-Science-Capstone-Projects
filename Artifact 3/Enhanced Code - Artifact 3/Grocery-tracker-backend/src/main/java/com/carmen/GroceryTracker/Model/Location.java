/**
 * Grocery tracker backend - location model class
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Class: This class serves as model for the location entity.
 */

package com.carmen.GroceryTracker.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "locations")
public class Location {

    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //NAME
    private String name;

    //PRODUCTS LIST
    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    @JsonManagedReference(value = "product-location")
    private List<Product> products;

    //ADDRESS
    @Column(nullable = false)
    private String address;

    //CITY
    @Column(nullable = false)
    private String city;

    //STATE
    @Column(nullable = false)
    private String state;


    // Constructors
    public Location() {}

    public Location(String name) {
        this.name = name;
    }

    //////////////////////////// getters and setters
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}