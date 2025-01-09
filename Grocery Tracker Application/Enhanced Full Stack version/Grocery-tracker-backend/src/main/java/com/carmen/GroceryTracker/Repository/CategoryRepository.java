/**
 * Grocery Tracker Backend Application - Category repository
 * Version 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product category information.
 * Description - Class: This class serves as repository for the category entity,
 */
package com.carmen.GroceryTracker.Repository;

import com.carmen.GroceryTracker.Model.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Long > {
    Optional<Category> findByName(String name);
}
