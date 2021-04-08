import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { NgxsModule } from '@ngxs/store';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { UserState } from './state/user.state';
import { UserComponent } from './user/user.component';
import { UserListComponent } from './user-list/user-list.component';
import { AuthGuard } from '../auth.guard';
import { UserFormComponent } from './user-form/user-form.component';
import { ReactiveFormsModule } from '@angular/forms';
import { FormlyMaterialModule } from '@ngx-formly/material';
import { FormlyModule } from '@ngx-formly/core';
import { MatButtonModule } from '@angular/material/button';

const routes: Routes = [
  {
    path: "user",
    component: UserComponent,
    canActivate: [AuthGuard],
    data: {
      roles: ["ROLE_ADMIN"]
    },
    children: [
      {
        path: "list",
        component: UserListComponent
      },
      {
        path: "create",
        component: UserFormComponent
      },
      {
        path: "edit/:id",
        component: UserFormComponent
      }
    ]
  }
];

@NgModule({
  declarations: [UserComponent, UserListComponent, UserFormComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    NgxsModule.forFeature([UserState]),
    MatTableModule,
    MatPaginatorModule,
    ReactiveFormsModule,
    FormlyMaterialModule,
    MatButtonModule,
    FormlyModule
  ]
})
export class AdminModule { }
