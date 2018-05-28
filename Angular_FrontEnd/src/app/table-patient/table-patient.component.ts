import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import 'rxjs/Rx';
import {SelectItem} from 'primeng/api';
import {PatientRestEndpointApi, Patient} from '../mspatient/index';
import {NodeRestEndpointApi, Node} from '../msnode/index';


@Component({
    selector: 'app-table-patient',
    templateUrl: './table-patient.component.html',
    styleUrls: ['./table-patient.component.css']
})
export class TablePatientComponent implements OnInit {

    patients: Patient[];
    hospitals: SelectItem[];
    services: SelectItem[] = [];
    selectedPatient: Patient;
    user;
    role;
    service;
    cols: any[];
    displayDialog: boolean;
    updateDialog: boolean;
    patient: Patient = {};
    newPatient: boolean;

    constructor(private patientService: PatientRestEndpointApi, private nodeService: NodeRestEndpointApi) {
    }

    ngOnInit() {
        this.user = localStorage.getItem('currentUser');
        this.role = localStorage.getItem('role');
        this.patientService.getPatients().subscribe(patients => this.patients = patients);
        if (this.role == 'SECRETARY') {
            this.nodeService.getNodes().subscribe(nodes => {
                for (let node of nodes) {
                    this.services.push({label: node.name, value: {id: node.id, name: node.name}});
                }

            });
        }
        // on définit ici les colonnes du tableaux
        this.cols = [
            {field: 'lastName', header: 'Nom'},
            {field: 'firstName', header: 'Prénom'},
            {field: 'socialSecurityNumber', header: 'NIR'},
            {field: 'nodeName', header: 'Service'}
        ];

    }

    showDialogToAdd() {
        this.newPatient = true;
        this.patient = {};
        this.displayDialog = true;
    }

    save() {
        let patients = [...this.patients];
        if (this.newPatient) {
            this.patient.nodeName = this.service.name;
            this.patients.push(this.patient);
            this.patientService.createPatient(this.service.id, this.patient);
            this.displayDialog = false;
        } else {

            this.patientService.updatePatient(this.service.id, this.patient);
            this.patientService.getPatients().subscribe(patients =>this.patients = patients);
            //this.patient.nodeName = this.service.name;
            //this.patients[this.patients.indexOf(this.selectedPatient)] = this.patient;

            this.updateDialog = false;

        }

        this.patient = null;
    }

    delete() {

        this.patient = null;
        this.displayDialog = false;
        this.updateDialog = false;
    }

    onRowSelect(event) {
        if (this.role == 'SECRETARY') {
            this.newPatient = false;
            this.patient = this.cloneStaff(event.data);
            this.updateDialog = true;
        }
    }

    cloneStaff(s: Patient): Patient {
        let patient = {};
        for (let prop in s) {
            patient[prop] = s[prop];
        }
        return patient;
    }
}
