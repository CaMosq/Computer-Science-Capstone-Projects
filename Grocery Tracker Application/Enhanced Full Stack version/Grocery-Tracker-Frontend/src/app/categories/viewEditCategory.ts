/**
 * Grocery tracker frontend - categoriesComponent
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Component: This component calls the appService component to retrieve
 * database data from the backend to create the categories tables and all categories related UI.
 */
import {Component, OnInit} from '@angular/core';
import {AlertService} from "../alert";
import {AppService} from "../service/app.service";
import {FormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import {CategoryDTO} from '../model/category';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'view-edit-product',
  template: `
    <div class="main-wrapper">
    <div class="view-edit-category-container" *ngIf="category">
      <h1>View / Edit Category <span><button class="btn-edit" id= "edit-btn" (click)="editCategory()">Edit category</button></span></h1>
      <h3>Category Name: {{category.name}}</h3>
      <input class="search-box" type="text" id="newNameInput" placeholder="Enter new Name" [(ngModel)]="newName" [hidden]="true" >
      <span><button class="btn-save" id="save-btn" (click)="updateCategory(category.id)" [hidden]="true">Save category</button></span>
      <h3>Total product type: {{category.products.length}}</h3>
      <table class="cat-table view-table">
        <thead>
        <tr>
          <th>Product type: </th>
          <th>Product image: </th>
          <th>Stock Quantity: </th>
        </tr>
        </thead>
        <tbody>
        <tr *ngFor="let product of category.products">
          <td>{{ product.productName }}</td>
          <td><img class="product-thumbnail" [src]="product.imageUrl" alt="{{product.productName}}" width="80px"
                   height="80px"></td>
          <td>{{product.stockQuantity}}</td>
        </tr>
        </tbody>
      </table>
    </div>
    </div>
  `,
  styleUrl: './categories.component.css',
  imports: [FormsModule, CommonModule],
  standalone: true
})

export class ViewEditCategoryComponent implements OnInit {

  category!: CategoryDTO;
  newName!: string;



  constructor(private appService: AppService, private route: ActivatedRoute, protected alertService: AlertService, private router: Router) {
  }

  ngOnInit(): void {
    const categoryId = this.route.snapshot.params['id'];

    if(categoryId){
      this.appService.getCategoryById(+categoryId).subscribe((data: any) => {
        this.category = data;
      });
    }
  }

  //method to make the edit categories form editable
  editCategory() {
    //set visibility
    // @ts-ignore
    document.getElementById("newNameInput").hidden= false;
    // @ts-ignore
    document.getElementById("save-btn").hidden= false;
    // @ts-ignore
    document.getElementById("edit-btn").hidden= true;
  }

  //method to edit a category in the database
  updateCategory(id: number) {
    const newCategory = new CategoryDTO(this.newName);
    this.appService.updateCategory(id, newCategory).subscribe(() => {
      this.alertService.success("Category updated successfully.");
      this.router.navigate(['/categories/']);
    })
  }

}
