import {Component, OnInit} from '@angular/core';
import{StaffRestEndpointApi,Staff}from "../msstaff/index"
import{NodeRestEndpointApi,Node}from "../msnode/index"

import {SelectItem} from "primeng/api";

@Component({
    selector: 'app-table-admin',
    templateUrl: './table-admin.component.html',
    styleUrls: ['./table-admin.component.css']
})
export class TableAdminComponent implements OnInit {
    
    user;

    displayDialog: boolean;
    displayDialogUpdate:boolean;
    staff: Staff = {};

    selectedStaff: Staff;

    // selection du rôle du staff
    types: SelectItem[]= [];
    status:SelectItem[]= [];
    nodes:SelectItem[] = [{label:"Choisissez un noeud", value: null}];
    newStaff: boolean;
    
    staffs: Staff[];

    cols: any[];
    
    constructor(private staffService:StaffRestEndpointApi,private nodeService:NodeRestEndpointApi) {
    }

    ngOnInit() {
        this.user = localStorage.getItem('currentUser');
        this.staffService.getStaffs().subscribe(staffs => this.staffs = staffs);
        this.nodeService.getNodes().subscribe(nodes =>{
            for (let node of nodes) {
                this.nodes.push({label: node.name, value: node.id}); 
            }
        })
        this.cols = [
            {field: 'lastName', header: 'Nom'},
            {field: 'firstName', header: 'Prénom'},
            {field: 'mailStaff', header: 'Mail'},
            {field: 'phoneNumber', header: 'Téléphone'},
            {field: 'status', header: 'État'},
            {field: 'type', header: 'Rôle'}
        ];

        // liste deroulante pour la recherche d'un hopital
        this.types = [
            {label:"Choisissez un type", value: null},
            {label: 'Médecin', value: "DOCTOR"},
            {label: 'Secrétaire', value: "SECRETARY"},
            {label: 'Infirmier', value : 'NURSE'},
            {label: 'Administateur',value:'ADMIN'}
        ];

        this.status = [
            {label:"Choisissez un status", value: null},
            {label: 'Travaille', value: 'WORKING'},
            {label: 'Renvoyé', value: 'RETIRED'},
            {label: 'Décédé', value : 'DEAD'},
        ];
    }

    showDialogToAdd() {
        this.newStaff = true;
        this.staff = {};
        this.displayDialog = true;
    }

    save() {
        let staffs = [...this.staffs];
        if (this.newStaff){
            this.staff.password ="123";
            staffs.push(this.staff);
            this.staffService.createStaff(this.staff);
        }else{
            staffs[this.staffs.indexOf(this.selectedStaff)] = this.staff;
            this.staffService.updateStaff(this.staff);
        }

        this.staffs = staffs;
        this.staff = null;
        this.displayDialog = false;
        this.displayDialogUpdate = false;
    }

    delete() {
        this.staff = null;
        this.displayDialog = false;
        this.displayDialogUpdate = false;
    }

    onRowSelect(event) {
        this.newStaff = false;
        this.staff = this.cloneStaff(event.data);
        this.displayDialogUpdate = true;
    }

    cloneStaff(s: Staff): Staff {
        let staff = {};
        for (let prop in s) {
            staff[prop] = s[prop];
        }
        return staff;
    }

}
