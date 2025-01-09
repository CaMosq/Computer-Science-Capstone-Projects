import { Routes } from '@angular/router';
import {ProductComponent} from './product/product.component';
import {CategoriesComponent} from './categories/categories.component';
import {LocationsComponent} from './locations/locations.component';
import {SalesComponent} from './sales/sales.component';
import {ViewEditCategoryComponent} from './categories/viewEditCategory';
import {ViewProductComponent} from './product/viewProduct';
import {AboutComponent} from './about/about';
import {ContactComponent} from './about/contact';

export const routes: Routes = [
  {path: '', component: SalesComponent},
  {path: 'sales', component: SalesComponent},
  {path: 'products', component: ProductComponent},
  {path: 'categories', component: CategoriesComponent},
  {path: 'locations', component: LocationsComponent},
  {path: 'view-category/:id', component: ViewEditCategoryComponent},
  {path: 'view-product/:id', component: ViewProductComponent},
  {path: 'about', component: AboutComponent},
  {path: 'contact', component: ContactComponent},

];
