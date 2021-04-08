import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductsComponent } from './products/products.component';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';
import { FileFieldComponent } from './file-field/file-field.component';
import { ReactiveFormsModule } from '@angular/forms';
import { FormlyModule } from '@ngx-formly/core';
import { FileValueAccesorDirective } from './file-value-accesor.directive';
import { RouterModule } from '@angular/router';
import { NgxsModule } from '@ngxs/store';


@NgModule({
  declarations: [ProductsComponent, FileFieldComponent, FileValueAccesorDirective],
  exports: [ProductsComponent],
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatButtonModule,
    ReactiveFormsModule,
    FormlyModule.forChild(),
    RouterModule.forChild([]),
    NgxsModule.forFeature([])
  ]
})
export class ShareModule { }
