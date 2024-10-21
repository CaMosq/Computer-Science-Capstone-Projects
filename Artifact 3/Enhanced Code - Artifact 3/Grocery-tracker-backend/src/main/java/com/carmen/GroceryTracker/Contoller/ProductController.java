/**
 * Grocery Tracker Backend Application - Product controller
 * Version 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Class: This class serves as controller for the product entity,
 * It implements the backend endpoints GET, POST, PUT, DELETE to communicate with the database
 * and instead of returning Product entities, it returns ProductDTO
 */
package com.carmen.GroceryTracker.Contoller;

import com.carmen.GroceryTracker.Model.Category;
import com.carmen.GroceryTracker.Model.Location;
import com.carmen.GroceryTracker.Model.Product;
import com.carmen.GroceryTracker.Model.DTOs.ProductDTO;
import com.carmen.GroceryTracker.Repository.ProductRepository;
import com.carmen.GroceryTracker.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    //GET
    @GetMapping(path = "/all")
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    //POST
    @PostMapping(value = "/add",  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    //GET
    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        //find the product by id
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return productService.getConvertToProductDTO(product);
    }

    //GET
    @GetMapping(path = "/name/{name}")
    public ProductDTO getProductByName(@PathVariable String name) {
        System.out.println("Searching for product with name: " + name); // Debug log

        // Find the product by name
        Product product = productRepository.findByProductNameIgnoreCase(name)
                .orElseThrow(() -> new RuntimeException("Product Nor found"));

        // If product is found, convert to DTO, otherwise throw exception
        return productService.getConvertToProductDTO(product);
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        //set Category from ProductDTO
        Category category = new Category();
        category.setId(productDTO.getProductCategory().getId());
        category.setName(productDTO.getProductCategory().getName());

        //set Location from ProductDTO
        Location location = new Location();
        location.setId(productDTO.getLocation().getId());
        location.setName(productDTO.getLocation().getName());

        //find product and update selected values
        return productRepository.findById(id).map(product -> {
            product.setProductName(productDTO.getProductName());
            product.setImageUrl(productDTO.getImageUrl());
            product.setProductCategory(category);
            product.setProductPrice(productDTO.getProductPrice());
            product.setStockQuantity(productDTO.getStockQuantity());
            product.setLocation(location);
            return ResponseEntity.ok(productRepository.save(product));
        }).orElse(ResponseEntity.notFound().build());
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return productRepository.findById(id).map(product -> {
            productRepository.delete(product);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

}
