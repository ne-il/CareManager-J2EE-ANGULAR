import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import 'rxjs/Rx';
import {SelectItem} from 'primeng/api';
import {PatientRestEndpointApi, Patient} from '../mspatient/index';
import {Document, DocumentRestEndpointApi} from '../msdocument/index';
import {Router, ActivatedRoute} from '@angular/router';

@Component({
    selector: 'app-info-patient',
    templateUrl: './info-patient.component.html',
    styleUrls: ['./info-patient.component.css']
})
export class InfoPatientComponent implements OnInit {

    user;
    role;
    thedraft = false;
    authorId: number;
    patient: Patient;
    selectedDocument: Document;
    documents: Document[];
    drafts: Document[] = [];
    types: SelectItem[] = [];
    cols: any[];
    displayDialog: boolean;
    displayDialogEdit: boolean;
    document: Document = {};
    newDocument: boolean;

    constructor(private route: ActivatedRoute, private documentService: DocumentRestEndpointApi, private patientService: PatientRestEndpointApi) {
    }

    ngOnInit() {
        this.user = localStorage.getItem('currentUser');
        this.role = localStorage.getItem('role');
        this.authorId = Number(localStorage.getItem('id'));
        let id = this.route.snapshot.paramMap.get('id');
        this.patientService.getPatient(id).subscribe(data => {
            this.patient = data;
            this.documents = this.patient.documents;
        });
        if (this.role !== 'SECRETARY') {
            this.documentService.getDrafts().subscribe(drafts => {
                for (let draft of drafts) {
                    if (draft.idPatient == id) {
                        this.drafts.push(draft);
                    }
                }
            });
        }
        this.cols = [
            {field: 'type', header: 'type du document'},
            {field: 'description', header: 'Description'},
            {field: 'dateOfValidation', header: 'Date de Création'},
        ];
        if (this.role == 'DOCTOR') {
            this.types = [
                {label: 'Choisissez un type', value: null},
                {label: 'PRESCRIPTION', value: 'PRESCRIPTION'},
                {label: 'ANALYSE', value: 'ANALYSE'},
                {label: 'ORDONNANCE', value: 'ORDONNANCE'},
                {label: 'POSOLOGIE', value: 'POSOLOGIE'},
                {label: 'OBSERVATION', value: 'OBSERVATION'},
                {label: 'COMMENTAIRE', value: 'COMMENTAIRE'},
                {label: 'RADIO', value: 'RADIO'},

            ];
        } else {
            this.types = [
                {label: 'Choisissez un type', value: null},
                {label: 'FICHE DE SOIN', value:'FICHE DE SOIN'},
                {label: 'OBSERVATION', value:'OBSERVATION'},
                {label: 'TRANSMISSION', value:'TRANSMISSION'},
            ];
        }
    }

    onRowSelect(event) {
        if (this.role !== 'SECRETARY') {
            this.newDocument = false;
            this.document = this.cloneStaff(event.data);
            this.displayDialogEdit = true;
        }
    }

    cloneStaff(s: Document): Document {
        let patient = {};
        for (let prop in s) {
            patient[prop] = s[prop];
        }
        return patient;
    }

    showDialogToAdd() {
        this.newDocument = true;
        this.document = {};
        this.displayDialog = true;
    }

    showDraftTable() {
        this.thedraft = true;
    }

    showDocumentTable() {
        this.thedraft = false;
    }

    save() {
        let documents = [...this.documents];
        if (this.newDocument) {
            this.document.dateOfValidation = new Date().toLocaleDateString();
            documents.push(this.document);
            this.document.idPatient = this.patient.id;
            this.document.status = 'VALIDATED';
            this.document.author = this.authorId;
            this.document.urlPicture = 'je sais pas'
            this.documentService.createDocument(this.document);
        }
        this.documents = documents;
        this.document = null;
        this.displayDialog = false;
    }

    save1() {
        let documents = [...this.documents];

        this.document.dateOfValidation = new Date().toLocaleDateString();
        documents.push(this.document);
        this.document.idPatient = this.patient.id;
        this.document.status = 'VALIDATED';
        this.document.author = this.authorId;
        this.document.urlPicture = 'je sais pas'
        this.documentService.saveDocument(this.document);

        let index = this.drafts.indexOf(this.selectedDocument);
        this.drafts = this.drafts.filter((val, i) => i != index);
        this.documents = documents;
        this.document = null;
        this.displayDialogEdit = false;
    }

    Brouillon() {

        this.document.dateOfValidation = new Date().toLocaleDateString();
        this.document.idPatient = this.patient.id;
        this.document.status = 'IN_PROGRESS';
        this.document.author = this.authorId;
        this.document.urlPicture = 'je sais pas'
        this.drafts.push(this.document);
        this.documentService.createDocument(this.document);

        this.document = null;
        this.displayDialog = false;
    }

    Brouillon1() {

        this.document.dateOfValidation = new Date().toLocaleDateString();
        this.document.idPatient = this.patient.id;
        this.document.status = 'IN_PROGRESS';
        this.document.author = this.authorId;
        this.document.urlPicture = 'je sais pas'
        this.drafts[this.drafts.indexOf(this.selectedDocument)] = this.document;
        this.documentService.updateDocument(this.document);

        this.document = null;

        this.displayDialogEdit = false;
    }

    delete() {
        this.document = null;
        this.displayDialog = false;
        this.displayDialogEdit = false;
    }

}
