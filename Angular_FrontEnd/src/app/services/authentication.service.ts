import { Inject, Injectable, Optional }                      from '@angular/core';
import { Http, Headers, URLSearchParams }                    from '@angular/http';
import { RequestMethod, RequestOptions, RequestOptionsArgs } from '@angular/http';
import { Response, ResponseContentType }                     from '@angular/http';

import { Observable }                                        from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import { HttpClient } from '@angular/common/http';


 
@Injectable()
export class AuthenticationService {
    public token: string;
    private basePath:string; 
    constructor(private http: HttpClient) {
        // set token if saved in local storage
        var currentUser = JSON.parse(JSON.stringify(localStorage.getItem('currentUser')));
        this.token = currentUser && currentUser.token;
        this.basePath ="http://localhost:8080/CareManager-1/rs/staff/auth";
    }

    public getToken(): string {
        return localStorage.getItem('token');
      }
  
    login(username: string, password: string):Observable<Boolean>{
        return this.http.post(this.basePath, { login: username, password: password })
            .map((response: Response) => {
                var t = JSON.parse(JSON.stringify(response));
                let token = response && t.token;
                if (token) {
                    // set token property
                    this.token = token;
                    // store username and jwt token in local storage to keep user logged in between page refreshes
                    localStorage.setItem('currentUser',username);
                    localStorage.setItem('token',token);
                    return true;
                }
            });
    }
 
    logout(): void {
        // clear token remove user from local storage to log user out
        this.token = null;
        localStorage.removeItem('currentUser');
        localStorage.removeItem('token');
    }
}