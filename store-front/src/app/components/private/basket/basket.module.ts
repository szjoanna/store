import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { ReactiveFormsModule } from '@angular/forms';
import { FormlyMaterialModule } from '@ngx-formly/material';
import { MatButtonModule } from '@angular/material/button';
import { FormlyModule } from '@ngx-formly/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { RouterModule, Routes } from '@angular/router';
import { NgxsModule } from '@ngxs/store';
import { BasketState } from './state/basket.state';
import { BasketComponent } from './basket/basket.component';
import { BasketListComponent } from './basket-list/basket-list.component';

const routes: Routes = [
  {
    path: "basket",
    component: BasketComponent,
    children: [
      {
        path: "list",
        component: BasketListComponent
      }
    ]
  }
];

@NgModule({
  declarations: [BasketComponent, BasketListComponent],
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    ReactiveFormsModule,
    FormlyMaterialModule,
    MatButtonModule,
    FormlyModule,
    MatFormFieldModule,
    MatInputModule,
    RouterModule.forChild(routes),
    NgxsModule.forFeature([BasketState]),
  ]
})
export class BasketModule { }
