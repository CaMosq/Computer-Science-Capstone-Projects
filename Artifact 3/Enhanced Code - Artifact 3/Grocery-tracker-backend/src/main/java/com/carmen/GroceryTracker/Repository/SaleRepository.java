/**
 * Grocery Tracker Backend Application - Sale repository
 * Version 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve sale information.
 * Description - Class: This class serves as controller for the sale entity,
 */
package com.carmen.GroceryTracker.Repository;

import com.carmen.GroceryTracker.Model.Sale;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends CrudRepository<Sale, Long> {
    // Query to get purchases for a specific product on a specific date
    @Query("SELECT p FROM Sale p WHERE p.product.productId = :productId AND p.saleDate = :date")
    List<Sale> findSalesByProductAndDate(@Param("productId") Long productId, @Param("date") LocalDate date);

    // Query to count how many times a product was purchased on a given date
    @Query("SELECT COUNT(p) FROM Sale p WHERE p.product.productId = :productId AND p.saleDate = :date")
    Long countSalesByProductAndDate(@Param("productId") Long productId, @Param("date") LocalDate date);

    //Query to get the most purchased product
    @Query("SELECT s.product.productId, SUM(s.quantitySold) AS totalQuantitySold FROM Sale s GROUP BY s.product.productId ORDER BY totalQuantitySold DESC")
    List<Object[]> findMostPurchasedProduct(Pageable pageable);


    //  Query to get total sales per day (which date has more sales)
    @Query("SELECT s.product.productId, s.product.productName, s.saleDate, SUM(s.quantitySold) AS dailyQuantitySold FROM Sale s GROUP BY s.product.productId, s.saleDate ORDER BY s.saleDate ASC")
    List<Object[]> findProductFrequencyDaily();

    // Query to get the total sales for each product
    @Query("SELECT s.product.productName, SUM(s.quantitySold) AS totalQuantitySold " +
            "FROM Sale s " +
            "GROUP BY s.product.productName " +
            "ORDER BY totalQuantitySold DESC")
    List<Object[]> findTopSellingProducts(Pageable pageable); //Pageable controls how many products I want to display from the result

    // Query to get the top-selling categories
    @Query("SELECT s.product.productCategory.name, SUM(s.quantitySold) AS totalSales " +
            "FROM Sale s " +
            "GROUP BY s.product.productCategory.name " +
            "ORDER BY totalSales DESC")
    List<Object[]> findBestSellingCategories (Pageable pageable);
}
