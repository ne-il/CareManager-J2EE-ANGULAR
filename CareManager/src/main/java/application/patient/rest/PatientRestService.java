package application.patient.rest;

import application.authentication.util.JWTTokenNeeded;
import application.authentication.util.TokenManager;
import application.document.domain.Document;
import application.node.domain.Node;
import application.node.repository.NodeRepository;
import application.patient.domain.Patient;
import application.patient.repository.PatientRepository;
import application.staff.domain.Staff;
import application.staff.repository.StaffRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;


@Path("/patient")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@Api("PatientRest Endpoint")
public class PatientRestService {
    @EJB
    PatientRepository repository;

    @EJB
    NodeRepository nodeRepository;

    @EJB
    StaffRepository sr;

//    @GET
//    @ApiOperation(value = "Find all Patient")
//    @ApiResponse(code = 404,message = "Method not allowed")
//    @JWTTokenNeeded
//    public List<Patient> getPatients() {
//        return repository.list();
//    }

    @GET
    @ApiOperation(value = "Find all Patient")
    @ApiResponse(code = 404, message = "Method not allowed")
    @JWTTokenNeeded
    public Response getRelatedPatients(@HeaderParam("Authorization") String authorization) {
        String token = TokenManager.getTokenFromAuthorizationHeader(authorization);


        if (TokenManager.verifyTokenWithRole(token, "SECRETARY")) {
            List<Patient> allPatients = repository.list();
            return Response.ok(allPatients).build();
        }

         if (TokenManager.verifyTokenWithRole(token, "DOCTOR") || TokenManager.verifyTokenWithRole(token, "NURSE") ) {

            Long staffId = TokenManager.getTokenStaffId(token);
            Staff requestAuthor = sr.find(staffId);
            Node staffNode = nodeRepository.find(requestAuthor.getNodeId());
            if (staffNode != null) {
                List<Patient> patientList = parcoursSousArbre(staffNode);
                return Response.ok(patientList).build();
            } else {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
        }

        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }


    private List<Patient> parcoursSousArbre(Node node) {
        ArrayList<Patient> patients = new ArrayList<>();

        if (node == null) {
            return patients;
        }
        patients.addAll(getPatientByNode(node));
        for (Node n : node.getChildrens()) {
            patients.addAll(parcoursSousArbre(n));
        }
        return patients;
    }


    private List<Patient> getPatientByNode(Node targetNode) {

        List<Patient> allPatients = repository.list();
        List<Patient> patientsAffectToTargetNode = new ArrayList<>();

        for (Patient p : allPatients) {
            if (targetNode.equals(p.getAffectedNode())) {
                patientsAffectToTargetNode.add(p);
            }
        }
        return patientsAffectToTargetNode;
    }


    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find Patient")
    @ApiResponse(code = 404, message = "Method not allowed")
    @JWTTokenNeeded
    public Response getPatientAndDocuments(@PathParam("id") long patientId, @HeaderParam("Authorization") String authorization) {
        String token = TokenManager.getTokenFromAuthorizationHeader(authorization);
        if (TokenManager.verifyTokenWithRole(token, "DOCTOR")) {
            Patient patient = repository.find(patientId);
            return Response.ok(patient).build();
        }

        else if (TokenManager.verifyTokenWithRole(token, "NURSE")) {
            Patient patient = repository.find(patientId);
//            IL FAUT NETTOYER LES DOCUMENTS
            ArrayList<Document> patientDocuments = new ArrayList<>(patient.getDocuments());
            List<Document> filteredDocuments = patientDocuments.stream()
                    .filter(document -> document.getType().equals("OBSERVATION")
                            || document.getType().equals("NOTE")
                            || document.getType().equals("PRESCRIPTION")
                            || document.getType().equals("FICHE DE SOIN")
                            || document.getType().equals("OBSERVATION")
                            || document.getType().equals("COMMENTAIRE")
                    )
                    .collect(Collectors.toList());
            patient.setDocuments(filteredDocuments);
            return Response.ok(patient).build();
        }

        else if (TokenManager.verifyTokenWithRole(token, "SECRETARY")) {
            Patient patient = repository.find(patientId);
            patient.setDocuments(new ArrayList<Document>());
//            On renvoie avec une liste de document vide
            return Response.ok(patient).build();
        }



        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }


    @POST
    @Path("/{nodeId}")
    @ApiOperation(value = "Create Patient")
    @ApiResponse(code = 404, message = "Method not allowed")
    @JWTTokenNeeded
    public Response createPatient(@NotNull Patient patient, @PathParam("nodeId") long nodeId, @HeaderParam("Authorization") String authorization) {
        String token = TokenManager.getTokenFromAuthorizationHeader(authorization);
        if (TokenManager.verifyTokenWithRole(token, "SECRETARY")) {
            Node affectedNode = nodeRepository.find(nodeId);

            if (affectedNode != null) {
                patient.setAffectedNode(affectedNode);
                repository.save(patient);
                return Response.ok().build();
            }

            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }

    @PUT
    @Path("/{nodeId}")
    @ApiOperation(value = "Update Patient")
    @ApiResponse(code = 404, message = "Method not allowed")
    @JWTTokenNeeded
    public Response updatePatientAndNode(Patient patient,@PathParam("nodeId") long nodeId, @HeaderParam("Authorization") String authorization) {
        String token = TokenManager.getTokenFromAuthorizationHeader(authorization);
        if (TokenManager.verifyTokenWithRole(token, "SECRETARY")) {
            Node affectedNode = nodeRepository.find(nodeId);
            if (affectedNode != null) {
                patient.setAffectedNode(affectedNode);
                repository.update(patient);
                return Response.ok().build();
            }
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }


    @PUT
    @ApiOperation(value = "Update Patient")
    @ApiResponse(code = 404, message = "Method not allowed")
    @JWTTokenNeeded
    public Response updatePatient(Patient patient, @HeaderParam("Authorization") String authorization) {
        String token = TokenManager.getTokenFromAuthorizationHeader(authorization);
        if (TokenManager.verifyTokenWithRole(token, "SECRETARY")) {
            Patient oldPatient = repository.find(patient.getId());
            Node oldNode = oldPatient.getAffectedNode();
            if (oldNode != null) {
                patient.setAffectedNode(oldNode);
                repository.update(patient);
                return Response.ok().build();
            }
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }


    @JWTTokenNeeded
    @GET
    @Path("/node/{nodeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPatientsAffectedToOneNode(@PathParam("nodeId") long nodeId, @HeaderParam("Authorization") String authorization) {
        String token = TokenManager.getTokenFromAuthorizationHeader(authorization);
        if (TokenManager.verifyToken(token)) {
            //if nodeId does not exist, return empty list
            if (nodeRepository.find(nodeId) == null) {
                return Response.ok().build();
            }
            return Response.ok(repository.getPatientByNode(nodeRepository.find(nodeId))).build();
        }
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }
}
