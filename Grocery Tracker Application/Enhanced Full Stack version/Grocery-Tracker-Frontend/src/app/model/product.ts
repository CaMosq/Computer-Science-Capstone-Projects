/**
 * Grocery tracker frontend - product model component
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Class: This component serves as model for the product entity.
 */

export class ProductDTO {
  productId: number;
  imageUrl: string;
  productName: string;
  productCategory: {
    id: number;
    name: string;
  }
  productPrice: number;
  stockQuantity: number;
  location: {
    id: number;
    name: string;
    city: string;
  }


  constructor() {
    this.productId = 0;
    this.imageUrl = "";
    this.productName ='';
    this.productCategory = {id: 0, name: ''};
    this.productPrice = 0;
    this.stockQuantity = 0;
    this.location = {id: 0, name: '', city: ''};
  }
}
