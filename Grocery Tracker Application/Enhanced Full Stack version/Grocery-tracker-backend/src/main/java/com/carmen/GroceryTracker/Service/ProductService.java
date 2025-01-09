/**
 * Grocery Tracker - ProductService
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: This class converts the Product entity to ProductDTO.
 * and provides service to the product entity by implementing
 * methods that help the controller communicate with the repository to send
 * data to the frontend.
 */
package com.carmen.GroceryTracker.Service;

import com.carmen.GroceryTracker.Model.Category;
import com.carmen.GroceryTracker.Model.DTOs.CategoryDTO;
import com.carmen.GroceryTracker.Model.DTOs.LocationDTO;
import com.carmen.GroceryTracker.Model.Location;
import com.carmen.GroceryTracker.Model.Product;
import com.carmen.GroceryTracker.Model.DTOs.ProductDTO;
import com.carmen.GroceryTracker.Model.Sale;
import com.carmen.GroceryTracker.Repository.CategoryRepository;
import com.carmen.GroceryTracker.Repository.LocationRepository;
import com.carmen.GroceryTracker.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private LocationRepository locationRepository;


    // Method to get all products and map them to DTOs
    public List<ProductDTO> getAllProducts() {
        List<Product> products = (List<Product>) productRepository.findAll();

        return products.stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());
    }


    // Convert Product entity to ProductDTO
    private ProductDTO convertToProductDTO(Product product) {

        // Create CategoryDTO with the productDTOSet
        CategoryDTO categoryDTO = new CategoryDTO(
                product.getProductCategory().getId(),
                product.getProductCategory().getName(),
                product.getProductCategory().getProducts()
        );

        // Create LocationDTO
        LocationDTO locationDTO = new LocationDTO(
                product.getLocation().getId(),
                product.getLocation().getName(),
                product.getLocation().getAddress(),
                product.getLocation().getCity(),
                product.getLocation().getState()
        );

        // Create and return the ProductDTO
        return new ProductDTO(
                product.getProductId(),
                product.getImageUrl(),
                product.getProductName(),
                categoryDTO,
                product.getProductPrice(),
                product.getStockQuantity(),
                locationDTO
        );
    }

    // Helper method to convert a Product entity to ProductDTO without categories and location entities.
    private ProductDTO convertProductToDTO(Product product) {
        // implement this similarly to avoid circular references when needed
        return new ProductDTO(
                product.getProductId(),
                product.getImageUrl(),
                product.getProductName(),
                null,  // Avoid circular references by not including the category again
                product.getProductPrice(),
                product.getStockQuantity(),
                null   // Assuming location is not needed for recursive products
        );
    }

    //getter for the private method ConvertToProductDTO()
    public ProductDTO getConvertToProductDTO(Product product){
        return convertToProductDTO(product);
    }

    //Method to convert the ProductDTO to a Product entity, save it to the database,
    // and return the saved product as a ProductDTO.
    public ProductDTO createProduct(ProductDTO productDTO) {
        // Convert ProductDTO to Product entity
        Product product = new Product();
        product.setProductName(productDTO.getProductName());
        product.setProductPrice(productDTO.getProductPrice());
        product.setStockQuantity(productDTO.getStockQuantity());

        // Fetch the Category and Location entities
        Category category = categoryRepository.findById(productDTO.getProductCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Location location = locationRepository.findById(productDTO.getLocation().getId())
                .orElseThrow(() -> new RuntimeException("Location not found"));

        // Set category and location
        product.setProductCategory(category);
        product.setLocation(location);

        // Save product to the database
        Product savedProduct = productRepository.save(product);

        // Convert back to ProductDTO and return
        return convertToProductDTO(savedProduct);
    }

    //this method creates a productDTO to reuse with other classes
    public static ProductDTO getProductDTO(Sale sale, LocationDTO locationDTO) {
        CategoryDTO categoryDTO = new CategoryDTO(
                sale.getProduct().getProductCategory().getId(),
                sale.getProduct().getProductCategory().getName(),
                sale.getProduct().getProductCategory().getProducts()
        );

        return new ProductDTO(
                sale.getProduct().getProductId(),
                sale.getProduct().getImageUrl(),
                sale.getProduct().getProductName(),
                categoryDTO,
                sale.getProduct().getProductPrice(),
                sale.getProduct().getStockQuantity(),
                locationDTO
        );
    }

    //Method to create a light version of the product entity.
    public static ProductDTO getLightProductDTO(Sale sale, LocationDTO locationDTO) {
        CategoryDTO categoryDTO = new CategoryDTO(
                sale.getProduct().getProductCategory().getId(),
                sale.getProduct().getProductCategory().getName(),
                sale.getProduct().getProductCategory().getProducts()
        );

        return new ProductDTO(
                sale.getProduct().getProductId(),
                sale.getProduct().getImageUrl(),
                sale.getProduct().getProductName()
        );
    }

}