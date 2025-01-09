/**
 * Grocery Tracker - LocationDTO (Data Transfer Object)
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: This class controls the data sent to the frontend
 * and avoids any issues with lazy loading or infinite recursion.
 * With this class I can control what information about the location
 * I want to send to the frontend. for example, here I'm not sending the
 * list of products with the location object.
 */
package com.carmen.GroceryTracker.Model.DTOs;

public class LocationDTO {

    private Long id;
    private String name;
    private String address;
    private String city;
    private String state;

    // Constructor

    public LocationDTO(Long id, String name, String address, String city, String state) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
    }


    // Getters and setters

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