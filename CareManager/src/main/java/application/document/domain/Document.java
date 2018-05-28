/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.document.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@NamedQuery(name = "FIND_ALL_DOCUMENT", query = "SELECT d FROM Document d")
@XmlRootElement
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     
//    @NotNull
    private long authorid;
    
//    @NotNull
    private String type;
//    PRESCRIPTION OBSERVATION NOTES RADIO
	
//    @NotNull
    private String description;	

//    @NotNull
    private String urlPicture;


    @NotNull
    //VALIDATED OR IN_PROGRESS
    private String status;

//    @NotNull
    private String dateOfValidation;
	
//    @NotNull
    private Long idPatient;
    
    public Long getAuthor() {
        return authorid;
    }

    public void setAuthor(Long authorid) {
        this.authorid = authorid;
    }

	
    public Long getId() {
	return id;
	}

    public void setId(Long id) {
		this.id = id;
	}

    public String getType() {
		return type;
	}

    public void setType(String type) {
		this.type = type;
	}

    public String getDescription() {
		return description;
	}

    public void setDescription(String description) {
		this.description = description;
	}

    public String getUrlPicture() {
		return urlPicture;
	}

    public void setUrlPicture(String urlPicture) {
		this.urlPicture = urlPicture;
	}

    public String getStatus() {
		return status;
	}

    public void setStatus(String status) {
		this.status = status;
	}

    public String getDateOfValidation() {
		return dateOfValidation;
	}

    public void setDateOfValidation(String dateOfValidation) {
		this.dateOfValidation = dateOfValidation;
	}

    public Long getIdPatient() {
		return idPatient;
	}

    public void setIdPatient(Long idpatient) {
		this.idPatient = idpatient;
	}


    public Document(){

    }

    public Document(Long authorid, String type, String description, String urlPicture, Long idpatient, String status) {
       
        this.authorid = authorid;
        this.type = type;
        this.description = description;
        this.urlPicture = urlPicture;
        this.idPatient = idpatient;
        this.status = status;
    }

    


    

    @Override
    public String toString() {
        return "application.document.domain.Document[ id=" + id +" ]";
    }
}
