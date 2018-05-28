/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.staff.domain;

import application.resources.AuthenticateStaff;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;


@Entity
@NamedQuery(name = "ALL_STAFF", query = "SELECT s FROM Staff s")
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String SALT = "hehehe";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String mailStaff;

    @NotNull
    private String phoneNumber;

    @NotNull
    @Column(unique = true)
    private String login;

    @NotNull
    private String password;

    @NotNull
    private Long nodeId;

    @NotNull
    private String status;
    //    WORKING RETIRED DEAD
    @NotNull
    private String type;
//DOCTOR NURSE SECRETARY ADMIN


    public Staff() {
    }



    public Staff(String firstName, String lastName, String mailStaff, String phoneNumber,
                 String login, String plainPassword, Long node, String status, String type) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mailStaff = mailStaff;
        this.phoneNumber = phoneNumber;
        this.login = login;

        String saltedPassword = SALT + plainPassword;
        String hashedPassword = AuthenticateStaff.generateHash(saltedPassword);
//        this.hashedPassword = plainPassword;
        this.password = hashedPassword;


        this.nodeId = node;
        this.status = status;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMailStaff() {
        return mailStaff;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMailStaff(String mailStaff) {
        this.mailStaff = mailStaff;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String hashedPassword) {
        this.password = hashedPassword;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void hashPassword(){
        String saltedPassword = Staff.SALT + this.password;
        String hashedPassword = AuthenticateStaff.generateHash(saltedPassword);
        this.password = hashedPassword;
    }


}
