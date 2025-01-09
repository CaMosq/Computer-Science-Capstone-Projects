/**
 * Grocery Tracker Backend Application - Sale controller
 * Version 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Class: This class serves as controller for the sale entity,
 * It implements the backend endpoints GET, POST, PUT, DELETE to communicate with the database
 */
package com.carmen.GroceryTracker.Contoller;

import com.carmen.GroceryTracker.Model.DTOs.SaleDTO;
import com.carmen.GroceryTracker.Model.Sale;
import com.carmen.GroceryTracker.Repository.SaleRepository;
import com.carmen.GroceryTracker.Service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/sales")
public class SaleController {
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SalesService salesService;

    //GET all sales
    @GetMapping("/all")
    public Iterable<SaleDTO> getAllSales (){
        return salesService.getAllSales();
    }

    //POST add a sale
    @PostMapping
    public Sale createSale(@RequestBody Sale sale) {
        return saleRepository.save(sale);
    }

    //GET all sales of a specific product by date
    @GetMapping("/product/{productId}/date/{date}")
    public List<Sale> getSalesByProductAndDate(@PathVariable Long productId, @PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return saleRepository.findSalesByProductAndDate(productId, localDate);
    }

    //GET total sales of a specific product by date
    @GetMapping("/product/{productId}/count/{date}")
    public Long countSalesByProductAndDate(@PathVariable Long productId, @PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return saleRepository.countSalesByProductAndDate(productId, localDate);
    }

    //GET most purchased product
    @GetMapping("/most-purchased-product")
    public ResponseEntity<Object[]> getMostPurchasedProduct() {
        return new ResponseEntity<>(salesService.getMostPurchasedProduct(), HttpStatus.OK);
    }

    //GET the total sales per day ( how many products the store sold in a specific day)
    @GetMapping("/product-frequency-daily")
    public ResponseEntity<List<Object[]>> getProductFrequencyDaily() {
        return new ResponseEntity<>(salesService.getProductFrequencyDaily(), HttpStatus.OK);
    }

    //GET the top 10 selling products
    @GetMapping("/top-selling-products")
    public ResponseEntity<List<Object[]>> getTopSellingProducts() {
        return new ResponseEntity<>(salesService.getTopSellingProducts(), HttpStatus.OK);
    }

    //GET the top 10 selling categories
    @GetMapping("/top-selling-categories")
    public ResponseEntity<List<Object[]>> getTopSellingCategories() {
        return new ResponseEntity<>(salesService.getTopSellingCategories(), HttpStatus.OK);
    }
}
