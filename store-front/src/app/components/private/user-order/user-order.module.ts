import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserOrderComponent } from './user-order/user-order.component';
import { UserOrderListComponent } from './user-order-list/user-order-list.component';
import { RouterModule, Routes } from '@angular/router';
import { NgxsModule } from '@ngxs/store';
import { UserOrderState } from './state/user-order.state';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { ReactiveFormsModule } from '@angular/forms';
import { FormlyMaterialModule } from '@ngx-formly/material';
import { MatButtonModule } from '@angular/material/button';
import { FormlyModule } from '@ngx-formly/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { OrederDetailsComponent } from './oreder-details/oreder-details.component';

const routes: Routes = [
  {
    path: "order",
    component: UserOrderComponent,
    children: [
      {
        path: "list",
        component: UserOrderListComponent
      },
      {
        path: "details/:orderNumber",
        component: OrederDetailsComponent
      },
    ]
  }
];

@NgModule({
  declarations: [UserOrderComponent, UserOrderListComponent, OrederDetailsComponent],
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
    NgxsModule.forFeature([UserOrderState]),
  ]
})
export class UserOrderModule { }
