/**
 * Grocery tracker frontend - category model component
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Class: This component serves as model for the category entity.
 */
import {ProductDTO} from './product';


export class CategoryDTO {
  id: number;
  name!: string;
  products!: ProductDTO[];


  constructor(name: string) {
    this.id = 0;
    this.name = name;
    this.products = [] ;
  }
}
