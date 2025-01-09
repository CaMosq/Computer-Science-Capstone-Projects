/**
 * Grocery Tracker Backend Application - Product repository
 * Version 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Class: This class serves as controller for the product entity,
 */
package com.carmen.GroceryTracker.Repository;

import com.carmen.GroceryTracker.Model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    // Custom query to find a product by name
   Optional<Product> findByProductNameIgnoreCase(String name);

}
