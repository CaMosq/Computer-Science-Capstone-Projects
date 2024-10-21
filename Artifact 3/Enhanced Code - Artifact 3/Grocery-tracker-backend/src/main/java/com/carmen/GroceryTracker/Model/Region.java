/**
 * Grocery tracker backend - Region entity class
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Class: This class serves as model for the Region entity
 */

package com.carmen.GroceryTracker.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Regions")
public class Region {

    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long regionId;

    //NAME
    @Column(nullable = false, unique = true)
    private String regionName;


    //DESCRIPTION
    private String regionDescription;


    //CONSTRUCTORS
    public Region() {
    }

    public Region(Long regionId, String regionName, String regionDescription) {
        this.regionId = regionId;
        this.regionName = regionName;
        this.regionDescription = regionDescription;
    }

    /////////////GETTERS AND SETTERS
    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionDescription() {
        return regionDescription;
    }

    public void setRegionDescription(String regionDescription) {
        this.regionDescription = regionDescription;
    }
}
