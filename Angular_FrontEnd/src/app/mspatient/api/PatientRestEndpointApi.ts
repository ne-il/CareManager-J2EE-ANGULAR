import {Inject, Injectable, Optional} from '@angular/core';
import {Http, Headers, URLSearchParams} from '@angular/http';
import {RequestMethod, RequestOptions, RequestOptionsArgs} from '@angular/http';
import {Response, ResponseContentType} from '@angular/http';

import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';

import * as models from '../model/models';
import {Patient} from '../index';

@Injectable()
export class PatientRestEndpointApi {

    private basePath: string;

    constructor(private http: HttpClient, private router: Router) {
        this.basePath = 'http://localhost:8080/CareManager-1/rs/patient/';
    }

    public getPatients() {
        return this.http.get(this.basePath)
            .map(response => <Patient[]> response).catch((err: Response) => {
                this.router.navigate(['/login']);
                return Observable.throw(err);
            });
    }

    public getPatient(id) {
        const path = this.basePath + id;
        return this.http.get(path)
            .map(response => <Patient> response).catch((err: Response) => {
                this.router.navigate(['/login']);
                return Observable.throw(err);
            });
    }

    public createPatient(nodeId, HttpRequestParams: any) {
        const path = this.basePath + nodeId;

        return this.http.post(path, HttpRequestParams).catch((err: Response) => {
            this.router.navigate(['/login']);
            return Observable.throw(err);
        }).subscribe(data => data);
    }

    public updatePatient(nodeId, HttpRequestParams: any) {
        const path = this.basePath + nodeId;
        return this.http.put(path, HttpRequestParams).catch((err: Response) => {
            this.router.navigate(['/login']);
            return Observable.throw(err);
        }).subscribe(data => data);
    }

    public getPatientsByNode(nodeId: number) {
        const path = this.basePath + '/node/' + nodeId;
        return this.http.get(path)
            .map(response => <Patient> response).catch((err: Response) => {
                this.router.navigate(['/login']);
                return Observable.throw(err);
            });
    }
}
