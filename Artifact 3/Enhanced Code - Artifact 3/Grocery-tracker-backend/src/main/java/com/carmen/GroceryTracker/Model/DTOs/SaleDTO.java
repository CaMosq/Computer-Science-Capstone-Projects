/**
 * Grocery Tracker - SaleDTO (Data Transfer Object)
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description: This class controls the data sent to the frontend
 * and avoids any issues with lazy loading or infinite recursion.
 * With this class I can control what information about the sale
 * I want to send to the frontend.
 */
package com.carmen.GroceryTracker.Model.DTOs;


import java.math.BigDecimal;
import java.util.Date;

public class SaleDTO {
    private long saleId;
    private ProductDTO productDTO;
    private Date saleDate;
    private int quantitySold;
    private BigDecimal totalAmount;

    public SaleDTO(long saleId, ProductDTO productDTO, Date saleDate, int quantitySold, BigDecimal totalAmount) {
        this.saleId = saleId;
        this.productDTO = productDTO;
        this.saleDate = saleDate;
        this.quantitySold = quantitySold;
        this.totalAmount = totalAmount;
    }

    //////getters and setters

    public long getSaleId() {
        return saleId;
    }

    public void setSaleId(long saleId) {
        this.saleId = saleId;
    }

    public ProductDTO getProduct() {
        return productDTO;
    }

    public void setProduct(ProductDTO product) {
        this.productDTO = product;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
