
import { Inject, Injectable, Optional }                      from '@angular/core';
import { Http, Headers, URLSearchParams }                    from '@angular/http';
import { RequestMethod, RequestOptions, RequestOptionsArgs } from '@angular/http';
import { Response, ResponseContentType }                     from '@angular/http';

import { Observable }                                        from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

import * as models                                           from '../model/models';
import {Staff} from '../index';

@Injectable()
export class StaffRestEndpointApi{
  
    private basePath:string; 
    constructor(private http: HttpClient,private router:Router) {
        this.basePath ="http://localhost:8080/CareManager-1/rs/staff/";
    }
    public getStaffs(){
        return this.http.get(this.basePath)
            .map(response =><Staff[]> response).catch((err: Response) => {    
                this.router.navigate(['/login']);
                return Observable.throw(err);
            });
    }

    public getStaffByStaffId(id:number){
        const path = this.basePath + id;
        return this.http.get(path)
            .map(response =><Staff> response).catch((err: Response) => {    
                this.router.navigate(['/login']);
                return Observable.throw(err);
            });
    }

    public getStaffByToken(){
        const path = this.basePath+'getStaffByToken';
        return this.http.get(path)
            .map(response =>{
                response = <Staff> response;
                var staff = JSON.parse(JSON.stringify(response));
                localStorage.setItem('currentUser',staff.firstName + " " + staff.lastName );
            }).catch((err: Response) => {    
                this.router.navigate(['/login']);
                return Observable.throw(err);
            });
    }

    public createStaff(HttpRequestParams: any){
        const path = this.basePath;

        return this.http.post(path,HttpRequestParams).catch((err: Response) => {    
            this.router.navigate(['/login']);
            return Observable.throw(err);
        }).subscribe(data=>data);
    }

    public updateStaff(HttpRequestParams: any){
        return this.http.put(this.basePath,HttpRequestParams).catch((err: Response) => {    
            this.router.navigate(['/login']);
            return Observable.throw(err);
        }).subscribe(data=>data);
    }
      
}
