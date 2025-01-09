/**
 * Grocery tracker frontend - productComponent
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Component: This component calls the appService component to retrieve
 * database data from the backend to create the product tables and all product related UI.
 */
import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {ProductDTO} from '../model/product';
import {AppService} from '../service/app.service';
import {TableFilterPipe} from '../pipe/table-filter.pipe';
import {CommonModule, NgForOf} from '@angular/common';
import {AddProductComponent} from './addProduct';
import {Router, RouterLink} from '@angular/router';
import {ViewProductComponent} from './viewProduct';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [
    FormsModule,
    TableFilterPipe,
    NgForOf,
    CommonModule,
    AddProductComponent,
    RouterLink,
    ViewProductComponent
  ],
  templateUrl: './product.component.html',
  styleUrl: './product.component.css'
})

export class ProductComponent implements OnInit {

  products: ProductDTO[] = [];

  //variables for the summary section
  totalProducts: number = 0;
  countLA: number = 0;
  countNY: number = 0;
  countCHI: number = 0;
  countMIA: number = 0;
  countATL: number = 0;

  //variables to filter the table
  category!:string;
  location!: string;

  //variables to search item by name in the top input box
  itemName: string = '';

  //variables for the toggle window
  showAddProduct = false; //determines if the addProduct section is shown


  @Output()
  productDeletedEvent = new EventEmitter<ProductDTO>();

  constructor(private appService: AppService, private router: Router ) {

  }


  ngOnInit(): void {
    this.loadProducts();

  }

  //method to load all the product in the table
  loadProducts(): void {
    this.appService.getAllProducts().subscribe(
      (data: ProductDTO[]) => {
      this.products = data;
      this.totalForEachCategory();
      console.log(this.products);
    },
      (error) => {
      console.log('Error loading Products', error);
    });
    // this.filterByCategory();
  }

  //functions to toggles the visibility of the toggle panel per component
  toggleAddProduct() {
    this.showAddProduct = !this.showAddProduct;
  }


  // Functions to be called when a new product is created, edited or viewed  to hide the form
  onProductCreated() {
    this.showAddProduct = false;
    this.loadProducts();
  }

  //method that implements the logic of the table filters
  filterByCategory() {
    for(let i = 0; i < this.products.length; i++) {
      this.category = this.products[i].productCategory.name;
      this.location = this.products[i].location.name;
    }
  }


  //method to display the total product in stock for each city
  totalForEachCategory() {
     for(let i = 0; i < this.products.length; i++) {
       if(this.products[i].location.city === "Los Angeles"){
         this.countLA++;
       }
       if(this.products[i].location.city === "New York"){
         this.countNY++;
       }
       if(this.products[i].location.city === "Chicago"){
         this.countCHI++;
       }
       if(this.products[i].location.city === "Miami"){
         this.countMIA++;
       }
       if(this.products[i].location.city === "Atlanta"){
         this.countATL++;
       }
       this.totalProducts = this.products.length;
     }

  }
  deleteProduct(id: number) {
    this.appService.deleteProduct(id).subscribe(() => {
      this.router.navigate(['/products/']);
      this.loadProducts();
    })
  }

}
