/**
 * Grocery tracker frontend - productComponent
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Component: This component calls the appService component to retrieve
 * database data from the backend to create the product tables and all product related UI.
 */
import {Component, Input, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ProductDTO} from '../model/product';
import {AppService} from '../service/app.service';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CategoryDTO} from '../model/category';
import {LocationDTO} from '../model/location';

@Component({
  selector: 'view-product',
  template: `
    <div>
    <div class="view-product-container" *ngIf="product">
        <h1>Viewing {{product.productName}} <span><button class="btn-edit" id= "edit-btn" (click)="editProduct()">Edit Product</button></span></h1>
        <table class="pro-table">
          <tr>
            <td>Product Name:</td>
            <td id="name-cell">{{product.productName}}</td>
            <td><input class="search-box" type="text" id="new-name-cell" placeholder="Enter new name" [hidden]="true" [(ngModel)]="product.productName" ></td>
          </tr>
          <tr>
            <td>Product Picture:</td>
            <td id="image-cell"><img class="product-thumbnail" [src]="product.imageUrl" alt="{{product.productName}}" width="80px"
                     height="80px"></td>
            <td><input class="search-box" type="text" id="new-image-cell" placeholder="Enter new image url" [hidden]="true" [(ngModel)]="product.imageUrl" ></td>
          </tr>
          <tr>
            <td>Product Category:</td>
            <td id="category-cell">{{product.productCategory.name}}</td>
            <td>
              <label id="category-label" for="categorySelect" [hidden]="true">Select one:</label>
              <select class="form-control" name="Category" id="category-select" [hidden]="true" [(ngModel)]="product.productCategory.id ">
              <option *ngFor="let cat of categories" [value]="cat.id">{{ cat.name }}</option>
            </select></td>
          </tr>
          <tr>
            <td>Product Price:</td>
            <td id="price-cell">$ {{product.productPrice}}</td>
            <td><input class="search-box" type="text" id="new-price-cell" placeholder="Enter new price" [hidden]="true" [(ngModel)]="product.productPrice" ></td>
          </tr>
          <tr>
            <td>Stock Quantity:</td>
            <td id="quantity-cell">{{product.stockQuantity}}</td>
            <td><input class="search-box" type="text" id="new-quantity-cell" placeholder="Enter new quantity" [hidden]="true" [(ngModel)]="product.stockQuantity" ></td>
          </tr>
          <tr>
            <td>Product Location:</td>
            <td id="location-cell">{{product.location.name}}</td>
            <td><label id="location-label" for="locationSelect" [hidden]="true">Select one:</label>
              <select class="form-control" name="Location" id="location-select" [hidden]="true" [(ngModel)]="product.location.id ">
                <option *ngFor="let location of locations" [value]="location.id">{{ location.name }}</option>
              </select></td>
          </tr>
          <tr>
            <td>
              <button class="btn-save" id="save-btn" [hidden]="true" (click)="updateProduct(product.productId)" > Save Product </button>
            </td>
          </tr>
        </table>
      </div>
      <div class="return-wrapper">
        <button class="return-btn" [routerLink]="['/products']" > < Return to products</button>
      </div>
    </div>
  `,
  styleUrl: 'product.component.css',
  imports: [CommonModule, ReactiveFormsModule, RouterLink, FormsModule],
  standalone: true
})
export class ViewProductComponent implements OnInit {

  product!: ProductDTO;
  @Input()
  categories: CategoryDTO[] = [];

  @Input()
  locations: LocationDTO[] = [];


  constructor(private appService: AppService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit(): void {
    const prodId = this.route.snapshot.params['id'];

    //get product by id
    if(prodId){
      this.appService.getProductById(prodId).subscribe((data: any) => {
        this.product = data;
      })
    }
    this.getCategories();
    this.getLocations();

  }

  getCategories(): void {
    this.appService.getAllCategories().subscribe((data: CategoryDTO[]) => {
      this.categories = data;
      console.log(this.categories);
    })
  }

  getLocations(): void {
    this.appService.getAllLocations().subscribe((data: LocationDTO[]) => {
      this.locations = data;
    })
  }
  editProduct() {
    //show empty fields
    // @ts-ignore
    document.getElementById("new-name-cell").hidden = false;
    // @ts-ignore
    document.getElementById("new-image-cell").hidden = false;
    // @ts-ignore
    document.getElementById("category-label").hidden = false;
    // @ts-ignore
    document.getElementById("category-select").hidden = false;
    // @ts-ignore
    document.getElementById("new-price-cell").hidden = false;
    // @ts-ignore
    document.getElementById("new-quantity-cell").hidden = false;
    // @ts-ignore
    document.getElementById("location-label").hidden = false;
    // @ts-ignore
    document.getElementById("location-select").hidden = false;
    // @ts-ignore
    document.getElementById("save-btn").hidden = false;

    //hide old fields
    // @ts-ignore
    document.getElementById("name-cell").hidden = true;
    // @ts-ignore
    document.getElementById("image-cell").hidden = true;// @ts-ignore
    document.getElementById("category-cell").hidden = true;// @ts-ignore
    document.getElementById("price-cell").hidden = true;// @ts-ignore
    document.getElementById("quantity-cell").hidden = true;
    // @ts-ignore
    document.getElementById("location-cell").hidden = true;
  }

  updateProduct(id: number) {
    this.appService.updateProduct(id, this.product).subscribe(() => {
      this.router.navigate(['/products/']);
    })

  }




}
