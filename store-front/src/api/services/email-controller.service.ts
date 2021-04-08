/* tslint:disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpResponse, HttpHeaders } from '@angular/common/http';
import { BaseService as __BaseService } from '../base-service';
import { ApiConfiguration as __Configuration } from '../api-configuration';
import { StrictHttpResponse as __StrictHttpResponse } from '../strict-http-response';
import { Observable as __Observable } from 'rxjs';
import { map as __map, filter as __filter } from 'rxjs/operators';


/**
 * Email Controller
 */
@Injectable({
  providedIn: 'root',
})
class EmailControllerService extends __BaseService {
  static readonly testUsingGETPath = '/api/emails';
  static readonly testGenericUsingGETPath = '/api/emails/generic';

  constructor(
    config: __Configuration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * test
   * @param emailType emailType
   */
  testUsingGETResponse(emailType: 'RESTART_PASSWORD' | 'REMIND_PASSWORD' | 'REGISTER_USER'): __Observable<__StrictHttpResponse<null>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (emailType != null) __params = __params.set('emailType', emailType.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/api/emails`,
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
   * @param emailType emailType
   */
  testUsingGET(emailType: 'RESTART_PASSWORD' | 'REMIND_PASSWORD' | 'REGISTER_USER'): __Observable<null> {
    return this.testUsingGETResponse(emailType).pipe(
      __map(_r => _r.body as null)
    );
  }

  /**
   * testGeneric
   * @param emailType emailType
   */
  testGenericUsingGETResponse(emailType: 'RESTART_PASSWORD' | 'REMIND_PASSWORD' | 'REGISTER_USER'): __Observable<__StrictHttpResponse<null>> {
    let __params = this.newParams();
    let __headers = new HttpHeaders();
    let __body: any = null;
    if (emailType != null) __params = __params.set('emailType', emailType.toString());
    let req = new HttpRequest<any>(
      'GET',
      this.rootUrl + `/api/emails/generic`,
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
   * testGeneric
   * @param emailType emailType
   */
  testGenericUsingGET(emailType: 'RESTART_PASSWORD' | 'REMIND_PASSWORD' | 'REGISTER_USER'): __Observable<null> {
    return this.testGenericUsingGETResponse(emailType).pipe(
      __map(_r => _r.body as null)
    );
  }
}

module EmailControllerService {
}

export { EmailControllerService }
