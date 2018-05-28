import { Inject, Injectable, Optional }                      from '@angular/core';
import { Http, Headers, URLSearchParams }                    from '@angular/http';
import { RequestMethod, RequestOptions, RequestOptionsArgs } from '@angular/http';
import { Response, ResponseContentType }                     from '@angular/http';
import { HttpClient, HttpRequest, HttpResponse, 
    HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable }                                        from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import { Router } from '@angular/router';


import * as models                                           from '../model/models';
import { BASE_PATH, COLLECTION_FORMATS }                     from '../variables';
import { Configuration }                                     from '../configuration';


@Injectable()
export class DocumentRestEndpointApi {

    protected basePath = 'http://localhost:8080/CareManager-1/rs/';
    public defaultHeaders: Headers = new Headers();
    public configuration: Configuration = new Configuration();

    constructor(protected http:HttpClient,private router:Router, @Optional()@Inject(BASE_PATH) basePath: string, @Optional() configuration: Configuration) {
        if (basePath) {
            this.basePath = basePath;
        }
        if (configuration) {
            this.configuration = configuration;
        }
    }

    public getDrafts(){
        return this.http.get(this.basePath+"document/getDrafts")
            .map(response =><Document[]> response).catch((err: Response) => {    
                this.router.navigate(['/login']);
                return Observable.throw(err);
            });
    }

     /**
     * 
     * @summary Create document
     */
    public createDocument(HttpRequestParams: any){
        const path = this.basePath + 'document/create';

        return this.http.post(path,HttpRequestParams).catch((err: Response) => {    
            this.router.navigate(['/login']);
            return Observable.throw(err);
        }).subscribe(data=>data);
    }
    
  
    /**
     * 
     * @summary save document
     */
    public saveDocument(HttpRequestParams: any){
        const path = this.basePath + 'document/save';

        return this.http.put(path,HttpRequestParams).catch((err: Response) => {    
            this.router.navigate(['/login']);
            return Observable.throw(err);
        }).subscribe(data=>data);
    }

    /**
     * 
     * @summary Update document
     */
    public updateDocument(HttpRequestParams?: any){
        const path = this.basePath + 'document/update';

        return this.http.put(path,HttpRequestParams).catch((err: Response) => {    
            this.router.navigate(['/login']);
            return Observable.throw(err);
        }).subscribe(data=>data);
    }

}
