import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductComponent } from './product/product.component';
import { ProductListComponent } from './product-list/product-list.component';
import { RouterModule, Routes } from '@angular/router';
import { NgxsModule } from '@ngxs/store';
import { ProductState } from './state/product.state';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { ProductFormComponent } from './product-form/product-form.component';
import { ReactiveFormsModule } from '@angular/forms';
import { FormlyMaterialModule } from '@ngx-formly/material';
import { MatButtonModule } from '@angular/material/button';
import { FormlyModule } from '@ngx-formly/core';
import { ProductDetailsComponent } from './product-details/product-details.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ShareModule } from '../../share/share.module';
import { MatAutocompleteModule } from '@angular/material/autocomplete';

const routes: Routes = [
  {
    path: "product",
    component: ProductComponent,
    children: [
      {
        path: "list",
        component: ProductListComponent
      },
      {
        path: "edit/:id",
        component: ProductFormComponent
      },
      {
        path: "create",
        component: ProductFormComponent
      },
      {
        path: "details/:id",
        component: ProductDetailsComponent
      },
    ]
  }
];

@NgModule({
  declarations: [ProductComponent, ProductListComponent, ProductFormComponent, ProductDetailsComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    NgxsModule.forFeature([ProductState]),
    MatTableModule,
    MatPaginatorModule,
    ReactiveFormsModule,
    FormlyMaterialModule,
    MatButtonModule,
    FormlyModule,
    MatFormFieldModule,
    MatInputModule,
    ShareModule,
    MatAutocompleteModule
  ]
})
export class ProductModule { }
