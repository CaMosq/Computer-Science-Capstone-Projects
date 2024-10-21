/**
 * Grocery Tracker Backend Application - Location controller
 * Version 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Class: This class serves as controller for the location entity,
 * It implements the backend endpoints GET, POST, PUT, DELETE to communicate with the database
 */
package com.carmen.GroceryTracker.Contoller;


import com.carmen.GroceryTracker.Model.DTOs.LocationDTO;
import com.carmen.GroceryTracker.Model.Location;
import com.carmen.GroceryTracker.Repository.LocationRepository;
import com.carmen.GroceryTracker.Service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationService locationService;

    //GET
    @GetMapping
    public Iterable<LocationDTO> getAllLocations() {
        return locationService.getLocations();
    }

    //POST
    @PostMapping
    public Location createLocation(@RequestBody Location location) {
        return locationRepository.save(location);
    }

    //GET
    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id) {
        return locationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //GET
    @GetMapping("/city/{city}")
    public List<Location> getLocationsByCity(@PathVariable String city) {
        return locationRepository.findByCity(city);
    }

    //GET
    @GetMapping("/state/{state}")
    public List<Location> getLocationsByState(@PathVariable String state) {
        return locationRepository.findByState(state);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long id) {
        return locationRepository.findById(id).map(location -> {
            locationRepository.delete(location);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
