import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
 
import { AuthenticationService } from '../services/authentication.service';
import { Observable } from 'rxjs/Observable';
 
@Component({
    moduleId: module.id,
    selector: 'login',
    templateUrl: 'login.component.html',
    styleUrls: ['./login.component.css']
})
 
export class LoginComponent implements OnInit {
    model: any = {};
    loading = false;
    error = '';
 
    constructor(
        private router: Router,
        private authenticationService: AuthenticationService) { }
 
    ngOnInit() {
        // reset login status
        this.authenticationService.logout();
     

    }
 
    login() {
        this.loading = true;
        this.authenticationService.login(this.model.username, this.model.password)
            .catch((err: Response) => {
                this.error = "Identifiant ou mot de passe incorrect";
                this.loading = false;
                return Observable.throw(err);
            })
            .subscribe(result => {
                    this.router.navigate(['/accueil']);
            });
    }
}