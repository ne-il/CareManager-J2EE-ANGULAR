package application.node.rest;

import application.authentication.util.JWTTokenNeeded;
import application.authentication.util.TokenManager;
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
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;


@Path("/node")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@Api("NodeRest Endpoint")
public class NodeRestService {

    @EJB
    private NodeRepository repository;

    @EJB
    private PatientRepository patientRepo;

    @EJB
    private StaffRepository sr;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response getNodes(@HeaderParam("Authorization") String authorization) {
        String token = TokenManager.getTokenFromAuthorizationHeader(authorization);
        if (TokenManager.verifyTokenWithRole(token, "ADMIN") || TokenManager.verifyTokenWithRole(token, "SECRETARY") ) {
            List<Node> nodes = repository.list();
            return Response.ok(nodes).build();
        }
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }

    @GET
    @Path("/{nodeId}")
    @Produces(MediaType.APPLICATION_JSON)
    @JWTTokenNeeded
    public Response getNode(@PathParam("nodeId") Long nodeId, @HeaderParam("Authorization") String authorization) {
        String token = TokenManager.getTokenFromAuthorizationHeader(authorization);
        if (TokenManager.verifyTokenWithRole(token, "ADMIN")) {
            Node node = repository.find(nodeId);
            if (node == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            return Response.ok(node).build();
        } else {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
        }
    }


    @POST
    @ApiOperation(value = "Create Node")
    @JWTTokenNeeded
    public Response createNode(Node newNode, @HeaderParam("Authorization") String authorization) {
        String token = TokenManager.getTokenFromAuthorizationHeader(authorization);
        if (TokenManager.verifyTokenWithRole(token, "ADMIN")) {
            if (newNode.getFatherId() == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            Long fatherId = newNode.getFatherId();
            Node fatherNode = repository.find(fatherId);
            if (fatherNode == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            fatherNode.addChild(newNode);
            repository.update(fatherNode);
            return Response.ok().build();
        }
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }


//    @GET
//    @Path("/RelatedPatient")
//    @Produces(MediaType.APPLICATION_JSON)
//    @JWTTokenNeeded
//    public Response getRelatedPatient(@HeaderParam("Authorization") String authorization){
//        String token = TokenManager.getTokenFromAuthorizationHeader(authorization);
//
//
//        if(TokenManager.verifyTokenWithRole(token, "SECRETARY")){
//            List<Patient> allPatients = patientRepo.list();
//            return Response.ok(allPatients).build();
//        }
//
//        else if (TokenManager.verifyTokenWithRole(token, "DOCTOR") || TokenManager.verifyTokenWithRole(token, "NURSE")){
//
//            Long staffId = TokenManager.getTokenStaffId(token);
//            Staff requestAuthor = sr.find(staffId);
//            Node staffNode = repository.find(requestAuthor.getNodeId());
//            if(staffNode != null){
//                List<Patient> patientList = parcoursSousArbre(staffNode);
//                return Response.ok(patientList).build();
//            }
//            else{
//                return Response.status(Response.Status.NO_CONTENT).build();
//            }
//        }
//
//        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
//
//
//    }

//    private List<Patient> parcoursSousArbre(Node node) {
//        ArrayList<Patient> patients = new ArrayList<>();
//
//        if(node == null){
//            return patients;
//        }
//        patients.addAll(getPatientByNode(node));
//        for (Node n: node.getChildrens()){
//            patients.addAll(parcoursSousArbre(n));
//        }
//        return patients;
//    }
//
//
//    private List<Patient> getPatientByNode(Node targetNode) {
//
//        List<Patient> allPatients = patientRepo.list();
//        List<Patient> patientsAffectToTargetNode = new ArrayList<>();
//
//        for(Patient p : allPatients) {
//            if(targetNode.equals(p.getAffectedNode())) {
//                patientsAffectToTargetNode.add(p);
//            }
//        }
//        return patientsAffectToTargetNode;
//    }


}