package application.staff.rest;


import application.authentication.util.JWTTokenNeeded;
import application.authentication.util.TokenManager;
import application.resources.AuthenticateStaff;
import application.staff.domain.Staff;
import application.staff.repository.StaffRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/staff")
public class StaffRestService {
    @EJB
    StaffRepository repository;


    @POST
    @Path("auth")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticate(AuthenticateStaff authStaff) {
        List<Staff> staffList = repository.list();

        Staff staffOfAuthStaff = null;
        for (Staff staff : staffList) {
            if (authStaff.getLogin().equals(staff.getLogin())) {
                staffOfAuthStaff = staff;
                break;
            }
        }
        if (staffOfAuthStaff == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        if (!AuthenticateStaff.authenticateStaff(staffOfAuthStaff, authStaff.getPassword())) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

//        String token = TokenManager.generateTokenWithRole(authStaff.getLogin(), staffOfAuthStaff.getType());
        String token = TokenManager.generateTokenWithRoleAndStaffId(authStaff.getLogin(),
                staffOfAuthStaff.getType(), staffOfAuthStaff.getId());

        return Response.ok("{ \"token\":\"" + token + "\" }").build();
    }




    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "all Staff")
    @ApiResponse(code = 404, message = "Method not allowed")
    @JWTTokenNeeded
    public Response getStaffs(@HeaderParam("Authorization") String authorization) {
        String token = TokenManager.getTokenFromAuthorizationHeader(authorization);
        if (TokenManager.verifyTokenWithRole(token, "ADMIN")) {
            List<Staff> allStaffs = repository.list();
            return Response.ok(allStaffs).build();
        } else {
            throw new NotAuthorizedException("");
        }
    }

    @GET
    @Path("/{staffId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "all Staff")
    @ApiResponse(code = 404, message = "Method not allowed")
    @JWTTokenNeeded
    public Response getStaffByStaffId(@HeaderParam("Authorization") String authorization, @PathParam("staffId") Long staffId) {
        String token = TokenManager.getTokenFromAuthorizationHeader(authorization);
        if (TokenManager.verifyTokenWithRole(token, "ADMIN")) {
            Staff staff = repository.find(staffId);
            return Response.ok(staff).build();
        } else {
            throw new NotAuthorizedException("");
        }
    }


    @GET
    @Path("getStaffByToken")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "all Staff")
    @ApiResponse(code = 404, message = "Method not allowed")
    @JWTTokenNeeded
    public Response getStaffByToken(@HeaderParam("Authorization") String authorization) {
        String token = TokenManager.getTokenFromAuthorizationHeader(authorization);

        if (TokenManager.verifyToken(token)) {
            Long staffId = TokenManager.getTokenStaffId(token);
            Staff requestAuthor = repository.find(staffId);
            return Response.ok(requestAuthor).build();
        } else {
            throw new NotAuthorizedException("");
        }
    }


    @POST
    @ApiOperation(value = "Create Staff")
    @ApiResponse(code = 404, message = "Method not allowed")
    @JWTTokenNeeded
    public Response createStaff(Staff staff, @HeaderParam("Authorization") String authorization) {
        String token = TokenManager.getTokenFromAuthorizationHeader(authorization);
        staff.hashPassword();
        if (TokenManager.verifyTokenWithRole(token, "ADMIN")) {
            repository.save(staff);
            return Response.ok().build();
        }
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }

    @PUT
    @ApiOperation(value = "Update Staff")
    @ApiResponse(code = 404, message = "Method not allowed")
    @JWTTokenNeeded
    public Response updateStaff(Staff staff, @HeaderParam("Authorization") String authorization) {
        String token = TokenManager.getTokenFromAuthorizationHeader(authorization);
//        staff.hashPassword();
        if (TokenManager.verifyTokenWithRole(token, "ADMIN")) {
            repository.update(staff);
            return Response.ok().build();
        }
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }
}
