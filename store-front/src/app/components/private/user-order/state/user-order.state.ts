import { Injectable } from '@angular/core';
import { Navigate } from '@ngxs/router-plugin';
import { State, Action, StateContext } from '@ngxs/store';
import { tap } from 'rxjs/operators';
import { Order, PageProductDto, ProductDto } from 'src/api/models';
import { UserOrderControllerService } from 'src/api/services';
import { CreateOrderAction, GetOrderDetailsAction, GetUserOrdersAction } from './user-order.actions';

export class UserOrderStateModel {
  public orders: Order[];
  public products: PageProductDto;
}

const defaults = {
  orders: [],
  products: null
};

@State<UserOrderStateModel>({
  name: 'userOrder',
  defaults
})
@Injectable()
export class UserOrderState {
  constructor(private userOrderService: UserOrderControllerService) {
  }

  @Action(GetUserOrdersAction)
  getUserOrders({ patchState }: StateContext<UserOrderStateModel>, { }: GetUserOrdersAction) {
    return this.userOrderService.getOrdersUsingGET().pipe(
      tap(response => patchState({
        orders: response
      }))
    )
  }

  @Action(GetOrderDetailsAction)
  getOrderDetails({ patchState }: StateContext<UserOrderStateModel>, { orderNumber, page, size }: GetOrderDetailsAction) {
    return this.userOrderService.getOrderDetailsUsingGET({ orderNumber, page, size }).pipe(
      tap(response => patchState({
        products: response
      }))
    )
  }

  @Action(CreateOrderAction)
  createOrder({ dispatch }: StateContext<UserOrderStateModel>, { }: CreateOrderAction) {
    return this.userOrderService.createOrderUsingPOST().pipe(
      tap(response => {
        dispatch(new Navigate(["/order/list"]));
      })
    )
  }
}
