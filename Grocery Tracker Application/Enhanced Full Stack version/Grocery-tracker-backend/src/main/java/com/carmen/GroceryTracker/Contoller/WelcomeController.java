/**
 * Grocery Tracker Backend Application - Welcome controller
 * Version 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Class: This class serves as controller for the welcome screen,
 * it displays a custom message (for testing purpose only)
 */
package com.carmen.GroceryTracker.Contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @GetMapping("/")
    public String[] welcome() {

        return new String[]{"Welcome to the Grocery Tracker Backend Application!", "Version 1.0", "Developed by Carmen Mosquera"};
    }
}
