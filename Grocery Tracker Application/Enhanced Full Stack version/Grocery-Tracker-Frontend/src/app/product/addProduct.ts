/**
 * Grocery tracker frontend - productComponent
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Component: This component calls the appService component to retrieve
 * database data from the backend to edit the product tables and all product related UI.
 */
import {Component, OnInit, Input, EventEmitter, Output} from '@angular/core';
import {ProductDTO} from "../model/product";
import {AlertService} from "../alert";
import {AppService} from "../service/app.service";
import {RouterLink} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {CategoryDTO} from "../model/category";
import {LocationDTO} from "../model/location";


@Component({
    selector: 'add-product',
    template: `
        <div class="add-new-product-container">
            <h1>Add Product</h1>
            <form class="add-product-form">

                <label for="locationId">Select a Location: </label>
                <select  class="form-control" name="Location" id="locationSelect" [(ngModel)]="product.location.id">
                    <option *ngFor="let location of locations" [value]="location.id">{{ location.name }}</option>
                </select>


                <label for="name">Product Name</label>
                <input type="text" class="form-control" id="productName" placeholder="Enter product name"
                       [(ngModel)]="product.productName"
                       name="name">

              <label for="image">Product Image</label>
              <input type="text" class="form-control" id="productName" placeholder="enter image url"
                     [(ngModel)]="product.imageUrl"
                     name="image">

                <label for="categorySelect">Select Category:</label>
                <select class="form-control" name="Category" id="categorySelect" [(ngModel)]="product.productCategory.id">
                    <option *ngFor="let cat of categories" [value]="cat.id">{{ cat.name }}</option>
                </select>

                <label for="price">Product Price</label>
                <input type="number" class="form-control" id="price" placeholder=" Enter price"
                       [(ngModel)]="product.productPrice"
                       name="price">


                <label for="quantity">Stock Quantity</label>
                <input type="number" class="form-control" id="quantity" placeholder=" Enter quantity"
                       [(ngModel)]="product.stockQuantity"
                       name="quantity">

                <br>
                <button type="button" class="btn-save btn btn-success" (click)="addProduct() ">Save</button>
            </form>
        </div>
    `,
    styleUrl: './product.component.css',
    imports: [
        RouterLink,
        FormsModule,
        CommonModule
    ],

    standalone: true
})
export class AddProductComponent implements OnInit {

    @Input()
    product: ProductDTO = new ProductDTO();

    @Input()
    categories: CategoryDTO[] = [];

    @Input()
    locations: LocationDTO[] = [];

    //to tell the parent(product component) that child addProduct component
    // has added a new Product, so it can fetch the products list again
    @Output()
    productAddedEvent = new EventEmitter<void>();
    alert!: AlertService;


    //constructor
    constructor(private appService: AppService,  protected alertService: AlertService) {
    }


    ngOnInit(): void {
        this.getCategories();
        this.getLocations();
    }

    //method to add anew product
    addProduct(): void {
        this.appService.createProduct(this.product).subscribe(() => {
            this.productAddedEvent.emit();
            this.alertService.success("Product added successfully.");
        })
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

}
