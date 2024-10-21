/**
 * Grocery Tracker Backend Application - Location repository
 * Version 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve location information.
 * Description - Class: This class serves as controller for the location entity,
 */
package com.carmen.GroceryTracker.Repository;

import com.carmen.GroceryTracker.Model.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location, Long> {
    List<Location> findByCity(String city);
    List<Location> findByState(String state);

}
