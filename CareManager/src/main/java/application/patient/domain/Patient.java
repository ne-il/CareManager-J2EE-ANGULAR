/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.patient.domain;

import application.document.domain.Document;
import application.node.domain.Node;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Entity
@NamedQuery(name = "ALL_PATIENT", query = "SELECT p FROM Patient p")
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String socialSecurityNumber;

    @NotNull
    private String address;

    @NotNull
    private String birthday;

    @NotNull
    private String birthPlace;

    @NotNull
    private String genderPatient;

    @NotNull
    private Boolean isMarried;

    @ManyToOne
    private Node affectedNode;

    private String nodeName;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Document> documents;

    public Patient() {
    }

    public Patient(String firstName, String lastName, String socialSecurityNumber, String address,
                   String birthday, String birthPlace, String genderPatient,
                   Boolean isMarried, Node affectedNode, String nodeName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.socialSecurityNumber = socialSecurityNumber;
        this.address = address;
        this.birthday = birthday;
        this.birthPlace = birthPlace;
        this.genderPatient = genderPatient;
        this.isMarried = isMarried;
        this.affectedNode = affectedNode;
        this.documents = new ArrayList<Document>();
        this.nodeName = nodeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getGenderPatient() {
        return genderPatient;
    }

    public void setGenderPatient(String genderPatient) {
        this.genderPatient = genderPatient;
    }

    public Boolean getMarried() {
        return isMarried;
    }

    public void setMarried(Boolean isMarried) {
        this.isMarried = isMarried;
    }

    public Node getAffectedNode() {
        return affectedNode;
    }

    public void setAffectedNode(Node affectedNode) {
        this.affectedNode = affectedNode;
        this.nodeName = affectedNode.getName();
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public void addDocument(Document document) {
        documents.add(document);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id) &&
                Objects.equals(firstName, patient.firstName) &&
                Objects.equals(lastName, patient.lastName) &&
                Objects.equals(socialSecurityNumber, patient.socialSecurityNumber) &&
                Objects.equals(address, patient.address) &&
                Objects.equals(birthday, patient.birthday) &&
                Objects.equals(birthPlace, patient.birthPlace) &&
                Objects.equals(genderPatient, patient.genderPatient) &&
                Objects.equals(isMarried, patient.isMarried) &&
                Objects.equals(affectedNode, patient.affectedNode) &&
                Objects.equals(documents, patient.documents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, socialSecurityNumber, address, birthday, birthPlace, genderPatient, isMarried, affectedNode, documents);
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
}
