/**
 * Grocery tracker frontend - filterPipe
 * Version: 1.0
 * Developer: Carmen Mosquera
 * Description - App: This application analyzes and tracks the frequency a product is purchased in a day.
 * This application uses mySQL database to store and retrieve product information.
 * Description - Component: This component allows the product component to filter the product table
 * by category, and location. Also implements fast product search in the search box.
 */
import { Pipe, PipeTransform } from '@angular/core';
import {ProductDTO} from '../model/product';

@Pipe({
  standalone: true,
  name: 'tableFilter'
})
export class TableFilterPipe implements PipeTransform {

  //filter category, location
  transform(list: ProductDTO[], filters: { category: string, region: string, name: string }): ProductDTO[] {
    return list.filter(item =>
      (filters.category ? item.productCategory.name.toLowerCase().includes(filters.category.toLowerCase()) : true) &&
      (filters.region ? item.location.name.toLowerCase().includes(filters.region.toLowerCase()) : true) &&
      (filters.name ? item.productName.toLowerCase().includes(filters.name.toLowerCase()) : true)


    );
  }

}
