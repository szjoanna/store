import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminModule } from './admin/admin.module';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './token.interceptor';
import { AuthGuard } from './auth.guard';
import { CategoryModule } from './category/category.module';
import { BasketModule } from './basket/basket.module';
import { UserOrderModule } from './user-order/user-order.module';

@NgModule({
  imports: [
    CommonModule,
    AdminModule,
    CategoryModule,
    BasketModule,
    UserOrderModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    AuthGuard
  ]
})
export class PrivateModule { }
