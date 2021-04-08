/* tslint:disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpHeaders } from '@angular/common/http';
import { BaseService as __BaseService } from '../base-service';
import { ApiConfiguration as __Configuration } from '../api-configuration';
import { StrictHttpResponse as __StrictHttpResponse } from '../strict-http-response';
import { Observable as __Observable } from 'rxjs';
import { map as __map, filter as __filter } from 'rxjs/operators';

import { PageCategoryDto } from '../models/page-category-dto';
import { CategoryDto } from '../models/category-dto';

/**
 * Category Controller
 */
@Injectable({
  providedIn: 'root',
})
class CategoryControllerService extends __BaseService {
  static readonly getPageCategoryUsingGETPath = '/api/categories';
  static readonly saveCategoryUsingPOSTPath = '/api/categories';
  static readonly getAllCategoriesUsingGETPath = '/api/categories/all';
  static readonly findByIdUsingGETPath = '/api/categories/{id}';
  static readonly updateCategoryUsingPUTPath = '/api/categories/{id}';
  static readonly deleteCategoryUsingDELETEPath = '/api/categories/{id}';

  constructor(
    config: __Configuration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * getPageCategory
   * @param params The `CategoryControllerService.GetPageCategoryUsingGETParams` containing the following parameters:
   *
   * - `size`: size
   *
   * - `page`: page
   *
   * @return OK
   */
  getPageCategoryUsingGETResponse(params: CategoryControllerService.GetPageCategoryUsingGETParams): __Observable<__StrictHttpResponse<PageCategoryDto>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (params.size != null) __params = __params.set('size', params.size.toString());
    if (params.page != null) __params = __params.set('page', params.page.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/api/categories`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<PageCategoryDto>;
      })
    );
  }
  /**
   * getPageCategory
   * @param params The `CategoryControllerService.GetPageCategoryUsingGETParams` containing the following parameters:
   *
   * - `size`: size
   *
   * - `page`: page
   *
   * @return OK
   */
  getPageCategoryUsingGET(params: CategoryControllerService.GetPageCategoryUsingGETParams): __Observable<PageCategoryDto> {
    return this.getPageCategoryUsingGETResponse(params).pipe(
      __map(_r => _r.body as PageCategoryDto)
    );
  }

  /**
   * saveCategory
   * @param categoryDto categoryDto
   * @return OK
   */
  saveCategoryUsingPOSTResponse(categoryDto: CategoryDto): __Observable<__StrictHttpResponse<CategoryDto>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    __body = categoryDto;
    let req = new HttpRequest<any>(
      'POST',
      this.rootUrl + `/api/categories`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<CategoryDto>;
      })
    );
  }
  /**
   * saveCategory
   * @param categoryDto categoryDto
   * @return OK
   */
  saveCategoryUsingPOST(categoryDto: CategoryDto): __Observable<CategoryDto> {
    return this.saveCategoryUsingPOSTResponse(categoryDto).pipe(
      __map(_r => _r.body as CategoryDto)
    );
  }

  /**
   * getAllCategories
   * @return OK
   */
  getAllCategoriesUsingGETResponse(): __Observable<__StrictHttpResponse<Array<CategoryDto>>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/api/categories/all`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<Array<CategoryDto>>;
      })
    );
  }
  /**
   * getAllCategories
   * @return OK
   */
  getAllCategoriesUsingGET(): __Observable<Array<CategoryDto>> {
    return this.getAllCategoriesUsingGETResponse().pipe(
      __map(_r => _r.body as Array<CategoryDto>)
    );
  }

  /**
   * findById
   * @param id id
   * @return OK
   */
  findByIdUsingGETResponse(id: number): __Observable<__StrictHttpResponse<CategoryDto>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/api/categories/${encodeURIComponent(id)}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<CategoryDto>;
      })
    );
  }
  /**
   * findById
   * @param id id
   * @return OK
   */
  findByIdUsingGET(id: number): __Observable<CategoryDto> {
    return this.findByIdUsingGETResponse(id).pipe(
      __map(_r => _r.body as CategoryDto)
    );
  }

  /**
   * updateCategory
   * @param params The `CategoryControllerService.UpdateCategoryUsingPUTParams` containing the following parameters:
   *
   * - `id`: id
   *
   * - `categoryDto`: categoryDto
   *
   * @return OK
   */
  updateCategoryUsingPUTResponse(params: CategoryControllerService.UpdateCategoryUsingPUTParams): __Observable<__StrictHttpResponse<CategoryDto>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    __body = params.categoryDto;
    let req = new HttpRequest<any>(
      'PUT',
      this.rootUrl + `/api/categories/${encodeURIComponent(params.id)}`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'json'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<CategoryDto>;
      })
    );
  }
  /**
   * updateCategory
   * @param params The `CategoryControllerService.UpdateCategoryUsingPUTParams` containing the following parameters:
   *
   * - `id`: id
   *
   * - `categoryDto`: categoryDto
   *
   * @return OK
   */
  updateCategoryUsingPUT(params: CategoryControllerService.UpdateCategoryUsingPUTParams): __Observable<CategoryDto> {
    return this.updateCategoryUsingPUTResponse(params).pipe(
      __map(_r => _r.body as CategoryDto)
    );
  }

  /**
   * deleteCategory
   * @param id id
   */
  deleteCategoryUsingDELETEResponse(id: number): __Observable<__StrictHttpResponse<null>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;

    let req = new HttpRequest<any>(
      'DELETE',
      this.rootUrl + `/api/categories/${encodeURIComponent(id)}`,
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
   * deleteCategory
   * @param id id
   */
  deleteCategoryUsingDELETE(id: number): __Observable<null> {
    return this.deleteCategoryUsingDELETEResponse(id).pipe(
      __map(_r => _r.body as null)
    );
  }
}

module CategoryControllerService {

  /**
   * Parameters for getPageCategoryUsingGET
   */
  export interface GetPageCategoryUsingGETParams {

    /**
     * size
     */
    size: number;

    /**
     * page
     */
    page: number;
  }

  /**
   * Parameters for updateCategoryUsingPUT
   */
  export interface UpdateCategoryUsingPUTParams {

    /**
     * id
     */
    id: number;

    /**
     * categoryDto
     */
    categoryDto: CategoryDto;
  }
}

export { CategoryControllerService }
