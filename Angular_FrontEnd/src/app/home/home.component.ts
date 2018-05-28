import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService} from '../services/authentication.service'; 
import {StaffRestEndpointApi,Staff} from '../msstaff/index';

import jwtDecode = require('jwt-decode');
import { decode } from '@angular/router/src/url_tree';

@Component({
    selector: 'home',
    moduleId: module.id,
    templateUrl: 'home.component.html'
})
 
export class HomeComponent implements OnInit {
    user;
    role;
    constructor(private staffService:StaffRestEndpointApi) { 
        
    }
 
    ngOnInit() {
        this.staffService.getStaffByToken().subscribe(staff =>{ 
            staff = staff;
            this.user = localStorage.getItem('currentUser');
        });

        var decoded = jwtDecode(localStorage.getItem('token'));
        localStorage.setItem('role',decoded.role);
        localStorage.setItem('id',decoded.staffId);
        this.role = decoded.role;
        
    }
}