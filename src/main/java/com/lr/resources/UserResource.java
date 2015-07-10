package com.lr.resources;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.BeanParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.lr.filters.LRHTTPHeaders;
import com.lr.model.User;
import com.lr.response.LoginResponse;
import com.lr.response.Result;
import com.lr.service.AutheticationService;
import com.lr.service.UserService;



@Path("/")
public class UserResource {
	
	@POST
    @Path("/signup" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response signup(
        @Context HttpHeaders httpHeaders,
        @FormParam( "userName" ) String userName,
        @FormParam( "password" ) String password,
        @FormParam( "firstName" ) String firstName,
        @FormParam( "lastName" ) String lastName,
        @FormParam( "email" ) String email,
        @FormParam( "mobile" ) String mobile )
    {
		Response response    = null;
		UserService uService = new UserService();
		
		String serviceKey = httpHeaders.getHeaderString(LRHTTPHeaders.SERVICE_KEY);		 
              
		boolean  isSuccess = uService.signUp(serviceKey, userName, password,
        							firstName, lastName, email,
        							mobile);
		JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
        
		if (isSuccess) {
			jsonObjBuilder.add( "Status", "Successfullt created the user");
	        JsonObject jsonObj = jsonObjBuilder.build();
			response = Response.status(Status.OK).entity(jsonObj.toString()).build();			
		} else {
			jsonObjBuilder.add( "Status", "Issue while creating the user");
			JsonObject jsonObj = jsonObjBuilder.build();
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(jsonObj.toString()).build();
		}
               		
		return response;
    }
	
	@POST
    @Path("/login" )
    @Produces( MediaType.APPLICATION_JSON )
    public Result login(
        @Context HttpHeaders httpHeaders,
        @FormParam( "username" ) String username,
        @FormParam( "password" ) String password )
    {		
		AutheticationService authService = AutheticationService.getInstance();
		UserService uService = new UserService();
		
		String serviceKey = httpHeaders.getHeaderString(LRHTTPHeaders.SERVICE_KEY);		 
        String authToken  = authService.login(serviceKey, username, password);
                
        //JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
        //jsonObjBuilder.add( "auth_token", authToken );
        //JsonObject jsonObj = jsonObjBuilder.build();
        //return Response.status(Status.OK).entity(jsonObj.toString()).build();        
           
        LoginResponse response = uService.createLoginResponse(authService, authToken, serviceKey);        
        Result result = new Result(response);       		
		return result;
    }
	
	@POST
    @Path("/logout")
	public Response logout(
	    @Context HttpHeaders httpHeaders )
	{
        try {
        	AutheticationService authService = AutheticationService.getInstance();
            String serviceKey = httpHeaders.getHeaderString(LRHTTPHeaders.SERVICE_KEY);
            String authToken = httpHeaders.getHeaderString(LRHTTPHeaders.AUTH_TOKEN);
 
            authService.logout(serviceKey, authToken );
 
            return getNoCacheResponseBuilder( Response.Status.NO_CONTENT ).build();
        } catch ( final GeneralSecurityException ex ) {
            return getNoCacheResponseBuilder( Response.Status.INTERNAL_SERVER_ERROR ).build();
        }
    }
	private Response.ResponseBuilder getNoCacheResponseBuilder( Response.Status status ) {
        CacheControl cc = new CacheControl();
        cc.setNoCache(true);
        cc.setMaxAge(-1);
        cc.setMustRevalidate(true);
 
        return Response.status(status).cacheControl(cc);
    }
}
