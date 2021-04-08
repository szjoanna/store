import { Injectable } from '@angular/core';
import { State, Action, StateContext } from '@ngxs/store';
import { tap } from 'rxjs/operators';
import { BasketDto } from 'src/api/models';
import { BasketControllerService } from 'src/api/services';
import { AddProductToBasketAction, DeleteAllCurrentUserProductsAction, DeleteProductFromBasketAction, GetBasketByCurrentUserAction, UpdateProductFromBasketAction } from './basket.actions';

export class BasketStateModel {
  public basket: BasketDto;
  public baskets: BasketDto[];
}

const defaults = {
  basket: null,
  baskets: []
};

@State<BasketStateModel>({
  name: 'basket',
  defaults
})
@Injectable()
export class BasketState {
  constructor(private basketService: BasketControllerService) {
  }
  @Action(GetBasketByCurrentUserAction)
  getBasketByCurrentUser({ patchState }: StateContext<BasketStateModel>, { }: GetBasketByCurrentUserAction) {
    return this.basketService.getBasketByCurrentUserUsingGET().pipe(
      tap(response => patchState({
        baskets: response
      }))
    )
  }
  @Action(AddProductToBasketAction)
  addProductToBasket({ dispatch }: StateContext<BasketStateModel>, { idProduct, quantity }: AddProductToBasketAction) {
    return this.basketService.addProductToBasketUsingPOST({ idProduct, quantity }).pipe(
      tap(response => {
        dispatch(new GetBasketByCurrentUserAction());
      })
    )
  }
  @Action(DeleteProductFromBasketAction)
  deleteProductFromBasket({ dispatch }: StateContext<BasketStateModel>, { id }: DeleteProductFromBasketAction) {
    return this.basketService.deleteProductFromBasketUsingDELETE(id).pipe(
      tap(response => {
        dispatch(new GetBasketByCurrentUserAction());
      })
    )
  }
  @Action(DeleteAllCurrentUserProductsAction)
  deleteAllCurrentUserProducts({ dispatch }: StateContext<BasketStateModel>, { }: DeleteAllCurrentUserProductsAction) {
    return this.basketService.deleteAllCurrentUserProductsUsingDELETE().pipe(
      tap(response => {
        dispatch(new GetBasketByCurrentUserAction());
      })
    )
  }
  @Action(UpdateProductFromBasketAction)
  updateProductFromBasket({ dispatch }: StateContext<BasketStateModel>, { quantity, id }: UpdateProductFromBasketAction) {
    return this.basketService.updateProductFromBasketUsingPUT({ quantity, id }).pipe(
      tap(response => {
        dispatch(new GetBasketByCurrentUserAction());
      })
    )
  }
}
