/* tslint:disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpHeaders } from '@angular/common/http';
import { BaseService as __BaseService } from '../base-service';
import { ApiConfiguration as __Configuration } from '../api-configuration';
import { StrictHttpResponse as __StrictHttpResponse } from '../strict-http-response';
import { Observable as __Observable } from 'rxjs';
import { map as __map, filter as __filter } from 'rxjs/operators';

import { Order } from '../models/order';
import { PageProductDto } from '../models/page-product-dto';

/**
 * User Order Controller
 */
@Injectable({
  providedIn: 'root',
})
class UserOrderControllerService extends __BaseService {
  static readonly getOrdersUsingGETPath = '/api/orders';
  static readonly createOrderUsingPOSTPath = '/api/orders';
  static readonly getOrderDetailsUsingGETPath = '/api/orders/details';

  constructor(
    config: __Configuration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * getOrders
   * @return OK
   */
  getOrdersUsingGETResponse(): __Observable<__StrictHttpResponse<Array<Order>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/api/orders`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<Order>>;
      })
    );
  }
  /**
   * getOrders
   * @return OK
   */
  getOrdersUsingGET(): __Observable<Array<Order>> {
    return this.getOrdersUsingGETResponse().pipe(
      __map(_r => _r.body as Array<Order>)
    );
  }

  /**
   * createOrder
   */
  createOrderUsingPOSTResponse(): __Observable<__StrictHttpResponse<null>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/api/orders`,
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
   * createOrder
   */
  createOrderUsingPOST(): __Observable<null> {
    return this.createOrderUsingPOSTResponse().pipe(
      __map(_r => _r.body as null)
    );
  }

  /**
   * getOrderDetails
   * @param params The `UserOrderControllerService.GetOrderDetailsUsingGETParams` containing the following parameters:
   *
   * - `size`: size
   *
   * - `page`: page
   *
   * - `orderNumber`: orderNumber
   *
   * @return OK
   */
  getOrderDetailsUsingGETResponse(params: UserOrderControllerService.GetOrderDetailsUsingGETParams): __Observable<__StrictHttpResponse<PageProductDto>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (params.size != null) __params = __params.set('size', params.size.toString());
    if (params.page != null) __params = __params.set('page', params.page.toString());
    if (params.orderNumber != null) __params = __params.set('orderNumber', params.orderNumber.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/api/orders/details`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<PageProductDto>;
      })
    );
  }
  /**
   * getOrderDetails
   * @param params The `UserOrderControllerService.GetOrderDetailsUsingGETParams` containing the following parameters:
   *
   * - `size`: size
   *
   * - `page`: page
   *
   * - `orderNumber`: orderNumber
   *
   * @return OK
   */
  getOrderDetailsUsingGET(params: UserOrderControllerService.GetOrderDetailsUsingGETParams): __Observable<PageProductDto> {
    return this.getOrderDetailsUsingGETResponse(params).pipe(
      __map(_r => _r.body as PageProductDto)
    );
  }
}

module UserOrderControllerService {

  /**
   * Parameters for getOrderDetailsUsingGET
   */
  export interface GetOrderDetailsUsingGETParams {

    /**
     * size
     */
    size: number;

    /**
     * page
     */
    page: number;

    /**
     * orderNumber
     */
    orderNumber: string;
  }
}

export { UserOrderControllerService }
