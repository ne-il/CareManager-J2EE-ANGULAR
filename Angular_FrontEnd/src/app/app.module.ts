import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {InputTextModule} from 'primeng/inputtext';
import {ButtonModule} from 'primeng/button';
import {TableModule} from 'primeng/table';
import {DialogModule} from 'primeng/dialog';
import {DropdownModule} from "primeng/primeng";
import {MultiSelectModule} from "primeng/primeng";
import {TreeModule} from 'primeng/tree';
import {SliderModule} from "primeng/slider";
import { CollapseModule, BsDropdownModule } from 'ngx-bootstrap';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {PanelModule} from 'primeng/panel';
import {RadioButtonModule} from 'primeng/radiobutton';
import {CalendarModule} from 'primeng/calendar';

import { TooltipModule } from 'ngx-bootstrap/tooltip';
import { ModalModule } from 'ngx-bootstrap/modal';
import {NgControl} from "@angular/forms";

import {AppComponent} from './app.component';
import {TablePatientComponent} from './table-patient/table-patient.component';
import { TableAdminComponent } from './table-admin/table-admin.component';
import { TreeNodeComponent } from './tree-node/tree-node.component';
import { InfoPatientComponent } from './info-patient/info-patient.component';



import {BASE_PATH,DocumentRestEndpointApi} from './msdocument/index';
import {PatientRestEndpointApi} from './mspatient/index';
import {NodeRestEndpointApi} from './msnode/index';
import {StaffRestEndpointApi} from './msstaff/index';
import { AuthGuard } from './guards/auth.guard';
import { AuthenticationService} from './services/authentication.service';
import { LoginComponent } from './login/login.component';
import {HomeComponent} from './home/home.component';
import { TokenInterceptor } from './auth/token.interceptor';
import { HttpClientModule,HTTP_INTERCEPTORS} from '@angular/common/http';


import {Routes,RouterModule} from '@angular/router';
import { HttpModule } from '@angular/http';

var routes:Routes = [ 
    {path:'',redirectTo:"accueil",pathMatch:'full'},
    {path: 'login', component: LoginComponent}, 
    {path:"accueil",component:HomeComponent,canActivate: [AuthGuard]},
    {path:"patients",component:TablePatientComponent,canActivate: [AuthGuard]},
    {path:"patient/:id",component:InfoPatientComponent,canActivate: [AuthGuard]},
    {path:"staffs",component:TableAdminComponent,canActivate: [AuthGuard]},
    {path:"nodes",component:TreeNodeComponent,canActivate: [AuthGuard]}
  ];

@NgModule({
    declarations: [
        AppComponent,
        TablePatientComponent,
        TableAdminComponent,
        TreeNodeComponent,
        InfoPatientComponent,
        LoginComponent,
        HomeComponent,
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        FormsModule,
        TableModule,
        HttpClientModule,
        InputTextModule,
        DialogModule,
        ButtonModule,
        RadioButtonModule,
        CalendarModule,
        DropdownModule,
        MultiSelectModule,
        PanelModule,
        NgbModule,
        TreeModule,
        HttpModule,
        HttpClientModule,
        RouterModule.forRoot(routes),
        SliderModule,
    ],
    providers: [
        DocumentRestEndpointApi,
        PatientRestEndpointApi,
        NodeRestEndpointApi,
        StaffRestEndpointApi,
        AuthGuard,
        AuthenticationService,
        {
          provide: HTTP_INTERCEPTORS,
          useClass: TokenInterceptor,
          multi: true
        },
        {provide:BASE_PATH,useValue:"http://localhost:8080/CareManager-1/rs/"}
    ],
    bootstrap: [AppComponent],
})
export class AppModule {
}
