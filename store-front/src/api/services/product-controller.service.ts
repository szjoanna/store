/* tslint:disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpHeaders } from '@angular/common/http';
import { BaseService as __BaseService } from '../base-service';
import { ApiConfiguration as __Configuration } from '../api-configuration';
import { StrictHttpResponse as __StrictHttpResponse } from '../strict-http-response';
import { Observable as __Observable } from 'rxjs';
import { map as __map, filter as __filter } from 'rxjs/operators';

import { PageProductDto } from '../models/page-product-dto';
import { ProductDto } from '../models/product-dto';

/**
 * Product Controller
 */
@Injectable({
  providedIn: 'root',
})
class ProductControllerService extends __BaseService {
  static readonly getPageProductUsingGETPath = '/api/products';
  static readonly saveProductUsingPOSTPath = '/api/products';
  static readonly autoCompleteUsingGETPath = '/api/products/autocomplete';
  static readonly findProductsByMainUsingGETPath = '/api/products/main';
  static readonly findProductByIdUsingGETPath = '/api/products/{id}';
  static readonly updateProductUsingPUTPath = '/api/products/{id}';
  static readonly removeProductByIdUsingDELETEPath = '/api/products/{id}';

  constructor(
    config: __Configuration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * getPageProduct
   * @param params The `ProductControllerService.GetPageProductUsingGETParams` containing the following parameters:
   *
   * - `size`: size
   *
   * - `page`: page
   *
   * - `main`: main
   *
   * @return OK
   */
  getPageProductUsingGETResponse(params: ProductControllerService.GetPageProductUsingGETParams): __Observable<__StrictHttpResponse<PageProductDto>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (params.size != null) __params = __params.set('size', params.size.toString());
    if (params.page != null) __params = __params.set('page', params.page.toString());
    if (params.main != null) __params = __params.set('main', params.main.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/api/products`,
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
   * getPageProduct
   * @param params The `ProductControllerService.GetPageProductUsingGETParams` containing the following parameters:
   *
   * - `size`: size
   *
   * - `page`: page
   *
   * - `main`: main
   *
   * @return OK
   */
  getPageProductUsingGET(params: ProductControllerService.GetPageProductUsingGETParams): __Observable<PageProductDto> {
    return this.getPageProductUsingGETResponse(params).pipe(
      __map(_r => _r.body as PageProductDto)
    );
  }

  /**
   * saveProduct
   * @param params The `ProductControllerService.SaveProductUsingPOSTParams` containing the following parameters:
   *
   * - `productDto`: productDto
   *
   * - `file`: file
   *
   * @return OK
   */
  saveProductUsingPOSTResponse(params: ProductControllerService.SaveProductUsingPOSTParams): __Observable<__StrictHttpResponse<ProductDto>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let __formData = new FormData();
    __body = __formData;
    if (params.productDto != null) __params = __params.set('productDto', params.productDto.toString());
    if (params.file != null) { __formData.append('file', params.file as string | Blob);}
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/api/products`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<ProductDto>;
      })
    );
  }
  /**
   * saveProduct
   * @param params The `ProductControllerService.SaveProductUsingPOSTParams` containing the following parameters:
   *
   * - `productDto`: productDto
   *
   * - `file`: file
   *
   * @return OK
   */
  saveProductUsingPOST(params: ProductControllerService.SaveProductUsingPOSTParams): __Observable<ProductDto> {
    return this.saveProductUsingPOSTResponse(params).pipe(
      __map(_r => _r.body as ProductDto)
    );
  }

  /**
   * autoComplete
   * @param value value
   * @return OK
   */
  autoCompleteUsingGETResponse(value: string): __Observable<__StrictHttpResponse<Array<string>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (value != null) __params = __params.set('value', value.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/api/products/autocomplete`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<string>>;
      })
    );
  }
  /**
   * autoComplete
   * @param value value
   * @return OK
   */
  autoCompleteUsingGET(value: string): __Observable<Array<string>> {
    return this.autoCompleteUsingGETResponse(value).pipe(
      __map(_r => _r.body as Array<string>)
    );
  }

  /**
   * findProductsByMain
   * @param isMain isMain
   * @return OK
   */
  findProductsByMainUsingGETResponse(isMain: boolean): __Observable<__StrictHttpResponse<Array<ProductDto>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (isMain != null) __params = __params.set('isMain', isMain.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/api/products/main`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<ProductDto>>;
      })
    );
  }
  /**
   * findProductsByMain
   * @param isMain isMain
   * @return OK
   */
  findProductsByMainUsingGET(isMain: boolean): __Observable<Array<ProductDto>> {
    return this.findProductsByMainUsingGETResponse(isMain).pipe(
      __map(_r => _r.body as Array<ProductDto>)
    );
  }

  /**
   * findProductById
   * @param id id
   * @return OK
   */
  findProductByIdUsingGETResponse(id: number): __Observable<__StrictHttpResponse<ProductDto>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/api/products/${encodeURIComponent(id)}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<ProductDto>;
      })
    );
  }
  /**
   * findProductById
   * @param id id
   * @return OK
   */
  findProductByIdUsingGET(id: number): __Observable<ProductDto> {
    return this.findProductByIdUsingGETResponse(id).pipe(
      __map(_r => _r.body as ProductDto)
    );
  }

  /**
   * updateProduct
   * @param params The `ProductControllerService.UpdateProductUsingPUTParams` containing the following parameters:
   *
   * - `productDto`: productDto
   *
   * - `id`: id
   *
   * @return OK
   */
  updateProductUsingPUTResponse(params: ProductControllerService.UpdateProductUsingPUTParams): __Observable<__StrictHttpResponse<ProductDto>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = params.productDto;

    let req = new HttpRequest<any>(
      'PUT',
      this.rootUrl + `/api/products/${encodeURIComponent(params.id)}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<ProductDto>;
      })
    );
  }
  /**
   * updateProduct
   * @param params The `ProductControllerService.UpdateProductUsingPUTParams` containing the following parameters:
   *
   * - `productDto`: productDto
   *
   * - `id`: id
   *
   * @return OK
   */
  updateProductUsingPUT(params: ProductControllerService.UpdateProductUsingPUTParams): __Observable<ProductDto> {
    return this.updateProductUsingPUTResponse(params).pipe(
      __map(_r => _r.body as ProductDto)
    );
  }

  /**
   * removeProductById
   * @param id id
   */
  removeProductByIdUsingDELETEResponse(id: number): __Observable<__StrictHttpResponse<null>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'DELETE',
      this.rootUrl + `/api/products/${encodeURIComponent(id)}`,
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
   * removeProductById
   * @param id id
   */
  removeProductByIdUsingDELETE(id: number): __Observable<null> {
    return this.removeProductByIdUsingDELETEResponse(id).pipe(
      __map(_r => _r.body as null)
    );
  }
}

module ProductControllerService {

  /**
   * Parameters for getPageProductUsingGET
   */
  export interface GetPageProductUsingGETParams {

    /**
     * size
     */
    size: number;

    /**
     * page
     */
    page: number;

    /**
     * main
     */
    main: boolean;
  }

  /**
   * Parameters for saveProductUsingPOST
   */
  export interface SaveProductUsingPOSTParams {

    /**
     * productDto
     */
    productDto: string;

    /**
     * file
     */
    file: Blob;
  }

  /**
   * Parameters for updateProductUsingPUT
   */
  export interface UpdateProductUsingPUTParams {

    /**
     * productDto
     */
    productDto: ProductDto;

    /**
     * id
     */
    id: number;
  }
}

export { ProductControllerService }
