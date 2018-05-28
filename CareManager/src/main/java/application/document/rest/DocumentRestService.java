package application.document.rest;

import application.authentication.util.JWTTokenNeeded;
import application.authentication.util.TokenManager;
import application.document.domain.Document;
import application.document.repository.DocumentRepository;
import application.patient.domain.Patient;
import application.patient.repository.PatientRepository;
import application.staff.domain.Staff;
import application.staff.repository.StaffRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

import java.text.DateFormat;
import java.util.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/document")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@Api("DocumentRest Endpoint")
public class DocumentRestService {

    @EJB
    private DocumentRepository dr;

    @EJB
    private PatientRepository pr;

    @EJB
    private StaffRepository sr;


    @POST
    @Path("create")
    @ApiOperation(value = "Create document")
    @ApiResponse(code = 404, message = "Method not allowed")
    @JWTTokenNeeded
    public Response createDocument(Document newDocument, @HeaderParam("Authorization") String authorization) {

        String token = TokenManager.getTokenFromAuthorizationHeader(authorization);
        if (TokenManager.verifyTokenWithRole(token, "DOCTOR") || TokenManager.verifyTokenWithRole(token, "NURSE")) {
            Long authorId = TokenManager.getTokenStaffId(token);
            newDocument.setAuthor(authorId);

            if (newDocument.getStatus().equals("VALIDATED")) {
                Date date = new Date();
                DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
                newDocument.setDateOfValidation(shortDateFormat.format(date));
                Long idPatient = newDocument.getIdPatient();
                Patient patient = pr.find(idPatient);
                if(patient == null){
                    return Response.status(Response.Status.BAD_REQUEST).build();
                }
                patient.addDocument(newDocument);
                pr.update(patient);

            } else {
                dr.save(newDocument);
            }
            return Response.ok().build();

        } else
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }

    @PUT
    @Path("update")
    @ApiOperation(value = "Update document", response = Document.class)
    @ApiResponse(code = 404, message = "Method not allowed")
    @JWTTokenNeeded
    public Response updateDocument(Document entity) {
        dr.update(entity);
        return Response.ok().build();
    }

    @PUT
    @Path("save")
    @ApiOperation(value = "save document", response = Document.class)
    @ApiResponse(code = 404, message = "Method not allowed")
    @JWTTokenNeeded
    public Response saveDocument(Document entity) {
        entity.setStatus("STATUS");
        Date date = new Date();
        DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        entity.setDateOfValidation(shortDateFormat.format(date));
        Patient patient = pr.find(entity.getIdPatient());
        patient.addDocument(entity);
        pr.update(patient);
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "all documents")
    @ApiResponse(code = 404, message = "Method not allowed")
    public List<Document> findAll() {
        return dr.list();
    }


    @GET
    @Path("/getDrafts")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponse(code = 404, message = "Method not allowed")
    @JWTTokenNeeded
    public Response getDrafts(@HeaderParam("Authorization") String authorization) {
        String token = TokenManager.getTokenFromAuthorizationHeader(authorization);
        if (TokenManager.verifyTokenWithRole(token, "DOCTOR") || TokenManager.verifyTokenWithRole(token, "NURSE")) {
            Long staffId = TokenManager.getTokenStaffId(token);
            List<Document> allDocuments = dr.list();
            List<Document> myDrafts = new ArrayList<Document>();

            for (Document d : allDocuments) {
                if (d.getAuthor().equals(staffId) && d.getStatus().equals("IN_PROGRESS")) {
                    myDrafts.add(d);
                }
            }
            return Response.ok(myDrafts).build();

        } else {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
        }
    }

}
