/**
 * Grocery tracker backend - sale entity class
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Class: This class serves as model for the sale entity.
 */

package com.carmen.GroceryTracker.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "Sales")
public class Sale {

    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saleId;

    //PRODUCT
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonBackReference(value = "product-sales")
    @JsonIgnoreProperties("sales")
    private Product product;

    //SALE DATE
    private Date saleDate;

    //QUANTITY
    private int quantitySold;

    //TOTAL PRICE
    private BigDecimal totalAmount;

    //CONSTRUCTOR

    public Sale(Long saleId, Product product, Date saleDate, int quantitySold, BigDecimal totalAmount) {
        this.saleId = saleId;
        this.product = product;
        this.saleDate = saleDate;
        this.quantitySold = quantitySold;
        this.totalAmount = totalAmount;
    }

    public Sale() {
    }


    ///////////////////////////////// Getters and Setters

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
