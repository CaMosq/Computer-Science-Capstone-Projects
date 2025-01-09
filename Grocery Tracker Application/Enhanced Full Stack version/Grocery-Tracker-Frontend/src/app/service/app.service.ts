/**
 * Grocery tracker frontend - AppServiceComponent
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Component: This service component uses HttpClient to communicate with
 * the backed and retrieve the database data
 */
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {ProductDTO} from '../model/product';
import {CategoryDTO} from '../model/category';
import {LocationDTO} from '../model/location';
import {SaleDTO} from '../model/sale';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  private apiUrl = 'http://localhost:8080/api';  // backend URL

  constructor(private http: HttpClient) {}

  ////////////// Authentication

  ////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////// PRODUCT SECTION ////////////////////////////

  // Get all products ---GET
  getAllProducts(): Observable<ProductDTO[]> {
    return this.http.get<ProductDTO[]>(`${this.apiUrl}/products/all`);
  }

  // Get product by name ---GET
  getProductByName(name: string): Observable<ProductDTO> {
    return this.http.get<ProductDTO>(`${this.apiUrl}/products/name/${name}`);
  }

  // Get product by ID ---GET
  getProductById(id: number): Observable<ProductDTO> {
    return this.http.get<ProductDTO>(`${this.apiUrl}/products/${id}`);
  }

  // Create a new product ---POST
  createProduct(product: ProductDTO): Observable<ProductDTO> {
    return this.http.post<ProductDTO>(`${this.apiUrl}/products/add`, product, {
     headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    });
  }

  //update a product ---PUT
  updateProduct(id: number, product: ProductDTO): Observable<ProductDTO> {
    return this.http.put<ProductDTO>(`${this.apiUrl}/products/${id}`, product, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    })
  }

  //delete a product ---DELETE
  deleteProduct(id: number): Observable<ProductDTO> {
    return this.http.delete<ProductDTO>(`${this.apiUrl}/products/${id}`);
  }

  ////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////// CATEGORIES SECTION /////////////////////////

  //get all categories ---GET
  getAllCategories(): Observable<CategoryDTO[]> {
    return this.http.get<CategoryDTO[]>(`${this.apiUrl}/categories`);
  }

  //get category by id --- GET
  getCategoryById(id: number): Observable<CategoryDTO> {
    return this.http.get<CategoryDTO>(`${this.apiUrl}/categories/${id}`);
  }

  //add category ---POST
  createCategory(category: CategoryDTO): Observable<CategoryDTO> {
    return this.http.post<CategoryDTO>(`${this.apiUrl}/categories/add`, category, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    });
  }

  //update category --- PUT
  updateCategory(id: number, category: CategoryDTO): Observable<CategoryDTO> {
    return this.http.put<CategoryDTO>(`${this.apiUrl}/categories/${id}`, category, {
      headers: new HttpHeaders({'Content-Type': 'application/json'})
    });
  }

  //delete category --- DELETE
  deleteCategory(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/categories/${id}`);
  }


  ////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////// LOCATIONS SECTION /////////////////////////

  //get locations
  getAllLocations(): Observable<LocationDTO[]> {
    return this.http.get<LocationDTO[]>(`${this.apiUrl}/locations`);
  }


  ////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////// SALES SECTION /////////////////////////

  //get sales
  getAllSales(): Observable<SaleDTO[]> {
    return this.http.get<SaleDTO[]>(`${this.apiUrl}/sales/all`);
  }
  //get most purchased product
  getMostPurchasedProduct(): Observable<any> {
    return this.http.get(`${this.apiUrl}/sales/most-purchased-product`);
  }

  //get the total sales per day
  getProductFrequencyDaily(): Observable<any> {
    return this.http.get(`${this.apiUrl}/sales/product-frequency-daily`);
  }

  //get the top 10 selling products
  getTopSellingProducts(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/sales/top-selling-products`);
  }

  //get the top 10 selling categories
  getTopSellingCategories(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/sales/top-selling-categories`);
  }

}
