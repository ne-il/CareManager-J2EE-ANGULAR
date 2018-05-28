/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.authentication.rest;

import application.resources.AuthenticateStaff;
import application.staff.repository.StaffRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Stateless
@Path("/authentication")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@Api("AuthenticationRest Endpoint")
public class StaffAuthenticationRest {

    @EJB
    StaffRepository repository;

    @GET
    @Path("/login")
    @ApiOperation(value = "Authenticate Staff")
    @ApiResponse(code = 404,message = "Method not allowed")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String authenticateUser(AuthenticateStaff authStaff) {
      return "ok";
    }

}
