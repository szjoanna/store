import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoryComponent } from './category/category.component';
import { CategoryListComponent } from './category-list/category-list.component';
import { CategoryFormComponent } from './category-form/category-form.component';
import { AuthGuard } from '../auth.guard';
import { RouterModule, Routes } from '@angular/router';
import { NgxsModule } from '@ngxs/store';
import { CategoryState } from './state/category.state';
import { FormlyModule } from '@ngx-formly/core';
import { MatButtonModule } from '@angular/material/button';
import { FormlyMaterialModule } from '@ngx-formly/material';
import { ReactiveFormsModule } from '@angular/forms';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';

const routes: Routes = [
  {
    path: "category",
    component: CategoryComponent,
    canActivate: [AuthGuard],
    data: {
      roles: ["ROLE_ADMIN"]
    },
    children: [
      {
        path: "list",
        component: CategoryListComponent
      },
      {
        path: "create",
        component: CategoryFormComponent
      },
      {
        path: "edit/:id",
        component: CategoryFormComponent
      }
    ]
  }
];

@NgModule({
  declarations: [CategoryComponent, CategoryListComponent, CategoryFormComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    NgxsModule.forFeature([CategoryState]),
    MatTableModule,
    MatPaginatorModule,
    ReactiveFormsModule,
    FormlyMaterialModule,
    MatButtonModule,
    FormlyModule
  ]
})
export class CategoryModule { }
