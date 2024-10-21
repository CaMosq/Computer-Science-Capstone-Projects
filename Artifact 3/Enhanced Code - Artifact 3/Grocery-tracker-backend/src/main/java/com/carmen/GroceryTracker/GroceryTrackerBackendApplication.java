/**
 * Grocery Tracker Backend Application
 * Version 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 */
package com.carmen.GroceryTracker;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class GroceryTrackerBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroceryTrackerBackendApplication.class, args);
	}



}
