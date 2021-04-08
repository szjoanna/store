import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthModule } from './auth/auth.module';
import { ProductModule } from './product/product.module';
import { ReactiveFormsModule } from '@angular/forms';
import { FormlyMaterialModule } from '@ngx-formly/material';
import { MatButtonModule } from '@angular/material/button';
import { FormlyModule } from '@ngx-formly/core';


@NgModule({
  imports: [
    CommonModule,
    AuthModule,
    ProductModule,
    ReactiveFormsModule,
    FormlyMaterialModule,
    MatButtonModule,
    FormlyModule
  ]
})
export class PublicModule { }
