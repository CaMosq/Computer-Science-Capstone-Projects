/**
 * Grocery Tracker - LocationService
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: This class converts the Location entity to LocationDTO.
 * and provides service to the Location entity by implementing
 * methods that help the controller communicate with the repository to send
 * data to the frontend.
 */

package com.carmen.GroceryTracker.Service;

import com.carmen.GroceryTracker.Model.DTOs.LocationDTO;
import com.carmen.GroceryTracker.Model.Location;
import com.carmen.GroceryTracker.Repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    //get all locations
    public List<LocationDTO> getLocations(){
        List<Location> locations = (List< Location>) locationRepository.findAll();

        return locations.stream()
                .map(this::convertToLocationDTO)
                .collect(Collectors.toList());
    }

    //convert location to LocationDTO
    private LocationDTO convertToLocationDTO(Location location) {
        return new LocationDTO(location.getId(), location.getName(), location.getAddress(), location.getCity(), location.getState());
    }
}
