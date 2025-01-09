/**
 * Grocery Tracker - SaleService
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: This class converts the Sale entity to SaleDTO.
 * and provides service to the Sales entity by implementing
 * methods that help the controller communicate with the repository to send
 * data to the frontend.
 */
package com.carmen.GroceryTracker.Service;

import com.carmen.GroceryTracker.Model.DTOs.LocationDTO;
import com.carmen.GroceryTracker.Model.DTOs.ProductDTO;
import com.carmen.GroceryTracker.Model.DTOs.SaleDTO;
import com.carmen.GroceryTracker.Model.Sale;
import com.carmen.GroceryTracker.Repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalesService {
    @Autowired
    private SaleRepository salesRepository;

    //method to get all the sales and map them to DTOs
    public List<SaleDTO> getAllSales() {
        List<Sale> sales = (List<Sale>) salesRepository.findAll();

        return sales.stream()
                .map(this::convertToSaleDTO)
                .collect(Collectors.toList());
    }

    private SaleDTO convertToSaleDTO(Sale sale) {

        LocationDTO locationDTO = new LocationDTO(
                sale.getProduct().getLocation().getId(),
                sale.getProduct().getLocation().getName(),
                sale.getProduct().getLocation().getAddress(),
                sale.getProduct().getLocation().getCity(),
                sale.getProduct().getLocation().getState()
        );

        ProductDTO productDTO = ProductService.getLightProductDTO(sale, locationDTO);
//        CategoryDTO categoryDTO = new CategoryDTO(
//                sale.getProduct().getProductCategory().getId(),
//                sale.getProduct().getProductCategory().getName(),
//                sale.getProduct().getProductCategory().getProducts()
//        );
//
//
//        ProductDTO productDTO = new ProductDTO(
//                sale.getProduct().getProductId(),
//                sale.getProduct().getImageUrl(),
//                sale.getProduct().getProductName(),
//                categoryDTO,
//                sale.getProduct().getProductPrice(),
//                sale.getProduct().getStockQuantity(),
//                locationDTO
//        );

        return new SaleDTO(
                sale.getSaleId(), 
                productDTO,
                sale.getSaleDate(),
                sale.getQuantitySold(), 
                sale.getTotalAmount()
        );
    }


    public Object[] getMostPurchasedProduct() {
        // Only need the first result, so using PageRequest to limit results
        return salesRepository.findMostPurchasedProduct(PageRequest.of(0, 1)).getFirst();
    }

    //method to get the total sales per date.
    public List<Object[]> getProductFrequencyDaily() {
        return salesRepository.findProductFrequencyDaily();
    }

    //method to get the top 10 most selling products
    public List<Object[]> getTopSellingProducts() {
        return salesRepository.findTopSellingProducts(PageRequest.of(0, 10));//only 10 product for the chart
    }

    //method to get the best-selling categories
    public List<Object[]> getTopSellingCategories(){
        return salesRepository.findBestSellingCategories(PageRequest.of(0, 10));//only 10 categories
    }
}
