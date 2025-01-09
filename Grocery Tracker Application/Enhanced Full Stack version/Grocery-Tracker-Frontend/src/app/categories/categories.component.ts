/**
 * Grocery tracker frontend - categoriesComponent
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Component: This component calls the appService component to retrieve
 * database data from the backend to create the categories tables and all categories related UI.
 */
import {Component, Input, OnInit} from '@angular/core';
import {CategoryDTO} from '../model/category';
import {AppService} from '../service/app.service';
import {JsonPipe, NgForOf} from '@angular/common';
import {AlertService} from '../alert';
import {FormsModule} from '@angular/forms';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-categories',
  standalone: true,
  imports: [
    NgForOf,
    JsonPipe,
    FormsModule,
    RouterLink
  ],
  templateUrl: './categories.component.html',
  styleUrl: './categories.component.css'
})
export class CategoriesComponent implements OnInit {
  categories: CategoryDTO[] = [];
  @Input()
  category!: CategoryDTO;
  categoryName: string = '';
  alert!: AlertService;

  constructor(private appService: AppService, protected alertService: AlertService) {}

  ngOnInit(): void {
    this.getCategories();
  }

  //method to get the list of all categories
  getCategories() {
    this.appService.getAllCategories().subscribe((data: CategoryDTO[]) => {
      this.categories = data;
      // console.log(this.categories);
    })
  }

  //method to add a category to the database
  addCategory() {
    this.category = new CategoryDTO(this.categoryName);
    this.appService.createCategory(this.category).subscribe(() => {
      this.alertService.success("Category added successfully.");
      this.getCategories();
    })
  }

  //method to delete a category from database
  deleteCategory(id: number) {
    this.appService.deleteCategory(id).subscribe(() => {
        // console.log("category deleted successfully. id: " + id);
        this.alertService.success("Category deleted successfully.");
        this.getCategories();
    })
  }

}
