/**
 * Grocery tracker frontend - sale model component
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Class: This component serves as model for the sale entity.
 */
export class SaleDTO {
  saleId: number;
  product: {
    productId: number;
    productName: string;
    imageUrl: string;
  }
  saleDate: Date;
  quantitySold: number;
  totalAmount: number;


  constructor(saleId: number, product: { productId: number; productName: string; imageUrl: string }, saleDate: Date, quantitySold: number, totalAmount: number) {
    this.saleId = saleId;
    this.product = product;
    this.saleDate = saleDate;
    this.quantitySold = quantitySold;
    this.totalAmount = totalAmount;
  }
}
