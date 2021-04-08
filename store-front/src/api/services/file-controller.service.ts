/* tslint:disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpHeaders } from '@angular/common/http';
import { BaseService as __BaseService } from '../base-service';
import { ApiConfiguration as __Configuration } from '../api-configuration';
import { StrictHttpResponse as __StrictHttpResponse } from '../strict-http-response';
import { Observable as __Observable } from 'rxjs';
import { map as __map, filter as __filter } from 'rxjs/operators';


/**
 * File Controller
 */
@Injectable({
  providedIn: 'root',
})
class FileControllerService extends __BaseService {
  static readonly testUsingGET1Path = '/api/files';
  static readonly testGenericUsingGET1Path = '/api/files/generic';

  constructor(
    config: __Configuration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * test
   * @param fileType fileType
   */
  testUsingGET1Response(fileType: 'PDF' | 'DOC' | 'XLS' | 'CSV' | 'JSON'): __Observable<__StrictHttpResponse<null>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (fileType != null) __params = __params.set('fileType', fileType.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/api/files`,
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
   * test
   * @param fileType fileType
   */
  testUsingGET1(fileType: 'PDF' | 'DOC' | 'XLS' | 'CSV' | 'JSON'): __Observable<null> {
    return this.testUsingGET1Response(fileType).pipe(
      __map(_r => _r.body as null)
    );
  }

  /**
   * testGeneric
   * @param fileType fileType
   * @return OK
   */
  testGenericUsingGET1Response(fileType: 'PDF' | 'DOC' | 'XLS' | 'CSV' | 'JSON'): __Observable<__StrictHttpResponse<ArrayBuffer>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (fileType != null) __params = __params.set('fileType', fileType.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/api/files/generic`,
      __body,
      {
        headers: __headers,
        params: __params,
        responseType: 'arraybuffer'
      });

    return this.http.request<any>(req).pipe(
      __filter(_r => _r instanceof HttpResponse),
      __map((_r) => {
        return _r as __StrictHttpResponse<ArrayBuffer>;
      })
    );
  }
  /**
   * testGeneric
   * @param fileType fileType
   * @return OK
   */
  testGenericUsingGET1(fileType: 'PDF' | 'DOC' | 'XLS' | 'CSV' | 'JSON'): __Observable<ArrayBuffer> {
    return this.testGenericUsingGET1Response(fileType).pipe(
      __map(_r => _r.body as ArrayBuffer)
    );
  }
}

module FileControllerService {
}

export { FileControllerService }
