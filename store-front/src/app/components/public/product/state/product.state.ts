import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Navigate } from '@ngxs/router-plugin';
import { State, Action, StateContext } from '@ngxs/store';
import { tap } from 'rxjs/operators';
import { PageProductDto, ProductDto } from 'src/api/models';
import { ProductControllerService } from 'src/api/services';
import { environment } from 'src/environments/environment';
import { AutoCompleteProductsAction, DeleteProductAction, LoadProductByIdAction, LoadProductPageAction, LoadProductsByMainAction, SaveProductAction, UpdateProductAction } from './product.actions';

export class ProductStateModel {
  public productPage: PageProductDto;
  public product: ProductDto;
  public products: ProductDto[];
  public autoComplete: string[];
}

const defaults = {
  productPage: null,
  product: null,
  products: [],
  autoComplete: []
};

@State<ProductStateModel>({
  name: 'product',
  defaults
})
@Injectable()
export class ProductState {
  constructor(private productService: ProductControllerService, private httpClient: HttpClient) {
  }
  @Action(LoadProductPageAction)
  loadProducts({ patchState }: StateContext<ProductStateModel>, { page, size, main }: LoadProductPageAction) {
    return this.productService.getPageProductUsingGET({ page, size, main }).pipe(
      tap(response => patchState({
        productPage: response
      }))
    )
  }
  @Action(LoadProductByIdAction)
  loadProductById({ patchState }: StateContext<ProductStateModel>, { id }: LoadProductByIdAction) {
    return this.productService.findProductByIdUsingGET(id).pipe(
      tap(response => patchState({
        product: response
      }))
    )
  }
  @Action(AutoCompleteProductsAction)
  loadProductsByValue({ patchState }: StateContext<ProductStateModel>, { value }: AutoCompleteProductsAction) {
    return this.productService.autoCompleteUsingGET(value).pipe(
      tap(response => patchState({
        autoComplete: response
      }))
    )
  }
  @Action(LoadProductsByMainAction)
  loadProductByMain({ patchState }: StateContext<ProductStateModel>, { main }: LoadProductsByMainAction) {
    return this.productService.findProductsByMainUsingGET(main).pipe(
      tap(response => patchState({
        products: response
      }))
    )
  }
  @Action(DeleteProductAction)
  deleteProductById({ dispatch }: StateContext<ProductStateModel>, { id }: DeleteProductAction) {
    return this.productService.removeProductByIdUsingDELETE(id).pipe(
      tap(response => dispatch(new LoadProductPageAction(0, 20, true)))
    )
  }
  @Action(SaveProductAction)
  saveProductById({ dispatch }: StateContext<ProductStateModel>, { productDto, blob }: SaveProductAction) {
    return this.productService.saveProductUsingPOST({ productDto: JSON.stringify(productDto), file: blob }).pipe(
      tap(
        response => {
          dispatch(new Navigate(["/product/list"]));
          dispatch(new LoadProductPageAction(0, 20, true));
        })
    )
  }
  @Action(UpdateProductAction)
  updateProductById({ dispatch }: StateContext<ProductStateModel>, { id, productDto }: UpdateProductAction) {
    return this.productService.updateProductUsingPUT({ id, productDto }).pipe(
      tap(
        response => {
          dispatch(new Navigate(["/product/list"]));
          dispatch(new LoadProductPageAction(0, 20, true));
        })
    )
  }
}
