/* tslint:disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpHeaders } from '@angular/common/http';
import { BaseService as __BaseService } from '../base-service';
import { ApiConfiguration as __Configuration } from '../api-configuration';
import { StrictHttpResponse as __StrictHttpResponse } from '../strict-http-response';
import { Observable as __Observable } from 'rxjs';
import { map as __map, filter as __filter } from 'rxjs/operators';

import { BasketDto } from '../models/basket-dto';

/**
 * Basket Controller
 */
@Injectable({
  providedIn: 'root',
})
class BasketControllerService extends __BaseService {
  static readonly getBasketByCurrentUserUsingGETPath = '/api/baskets';
  static readonly addProductToBasketUsingPOSTPath = '/api/baskets';
  static readonly deleteAllCurrentUserProductsUsingDELETEPath = '/api/baskets';
  static readonly updateProductFromBasketUsingPUTPath = '/api/baskets/{id}';
  static readonly deleteProductFromBasketUsingDELETEPath = '/api/baskets/{id}';

  constructor(
    config: __Configuration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * getBasketByCurrentUser
   * @return OK
   */
  getBasketByCurrentUserUsingGETResponse(): __Observable<__StrictHttpResponse<Array<BasketDto>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/api/baskets`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<BasketDto>>;
      })
    );
  }
  /**
   * getBasketByCurrentUser
   * @return OK
   */
  getBasketByCurrentUserUsingGET(): __Observable<Array<BasketDto>> {
    return this.getBasketByCurrentUserUsingGETResponse().pipe(
      __map(_r => _r.body as Array<BasketDto>)
    );
  }

  /**
   * addProductToBasket
   * @param params The `BasketControllerService.AddProductToBasketUsingPOSTParams` containing the following parameters:
   *
   * - `quantity`: quantity
   *
   * - `idProduct`: idProduct
   *
   * @return OK
   */
  addProductToBasketUsingPOSTResponse(params: BasketControllerService.AddProductToBasketUsingPOSTParams): __Observable<__StrictHttpResponse<BasketDto>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (params.quantity != null) __params = __params.set('quantity', params.quantity.toString());
    if (params.idProduct != null) __params = __params.set('idProduct', params.idProduct.toString());
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/api/baskets`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<BasketDto>;
      })
    );
  }
  /**
   * addProductToBasket
   * @param params The `BasketControllerService.AddProductToBasketUsingPOSTParams` containing the following parameters:
   *
   * - `quantity`: quantity
   *
   * - `idProduct`: idProduct
   *
   * @return OK
   */
  addProductToBasketUsingPOST(params: BasketControllerService.AddProductToBasketUsingPOSTParams): __Observable<BasketDto> {
    return this.addProductToBasketUsingPOSTResponse(params).pipe(
      __map(_r => _r.body as BasketDto)
    );
  }

  /**
   * deleteAllCurrentUserProducts
   */
  deleteAllCurrentUserProductsUsingDELETEResponse(): __Observable<__StrictHttpResponse<null>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'DELETE',
      this.rootUrl + `/api/baskets`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<null>;
      })
    );
  }
  /**
   * deleteAllCurrentUserProducts
   */
  deleteAllCurrentUserProductsUsingDELETE(): __Observable<null> {
    return this.deleteAllCurrentUserProductsUsingDELETEResponse().pipe(
      __map(_r => _r.body as null)
    );
  }

  /**
   * updateProductFromBasket
   * @param params The `BasketControllerService.UpdateProductFromBasketUsingPUTParams` containing the following parameters:
   *
   * - `quantity`: quantity
   *
   * - `id`: id
   *
   * @return OK
   */
  updateProductFromBasketUsingPUTResponse(params: BasketControllerService.UpdateProductFromBasketUsingPUTParams): __Observable<__StrictHttpResponse<BasketDto>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (params.quantity != null) __params = __params.set('quantity', params.quantity.toString());

    let req = new HttpRequest<any>(
      'PUT',
      this.rootUrl + `/api/baskets/${encodeURIComponent(params.id)}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<BasketDto>;
      })
    );
  }
  /**
   * updateProductFromBasket
   * @param params The `BasketControllerService.UpdateProductFromBasketUsingPUTParams` containing the following parameters:
   *
   * - `quantity`: quantity
   *
   * - `id`: id
   *
   * @return OK
   */
  updateProductFromBasketUsingPUT(params: BasketControllerService.UpdateProductFromBasketUsingPUTParams): __Observable<BasketDto> {
    return this.updateProductFromBasketUsingPUTResponse(params).pipe(
      __map(_r => _r.body as BasketDto)
    );
  }

  /**
   * deleteProductFromBasket
   * @param id id
   */
  deleteProductFromBasketUsingDELETEResponse(id: number): __Observable<__StrictHttpResponse<null>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'DELETE',
      this.rootUrl + `/api/baskets/${encodeURIComponent(id)}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<null>;
      })
    );
  }
  /**
   * deleteProductFromBasket
   * @param id id
   */
  deleteProductFromBasketUsingDELETE(id: number): __Observable<null> {
    return this.deleteProductFromBasketUsingDELETEResponse(id).pipe(
      __map(_r => _r.body as null)
    );
  }
}

module BasketControllerService {

  /**
   * Parameters for addProductToBasketUsingPOST
   */
  export interface AddProductToBasketUsingPOSTParams {

    /**
     * quantity
     */
    quantity: number;

    /**
     * idProduct
     */
    idProduct: number;
  }

  /**
   * Parameters for updateProductFromBasketUsingPUT
   */
  export interface UpdateProductFromBasketUsingPUTParams {

    /**
     * quantity
     */
    quantity: number;

    /**
     * id
     */
    id: number;
  }
}

export { BasketControllerService }
