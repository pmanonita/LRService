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
import com.lr.response.AppResponse;
import com.lr.response.ErrorMessage;
import com.lr.response.ErrorResponse;
import com.lr.response.UserResponse;
import com.lr.response.Result;
import com.lr.service.AutheticationService;
import com.lr.service.UserService;



@Path("/")
public class UserResource {
	
	@POST
    @Path("/signup" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse signup(
        @Context HttpHeaders httpHeaders,
        @FormParam( "userName" ) String userName,
        @FormParam( "password" ) String password,
        @FormParam( "firstName" ) String firstName,
        @FormParam( "lastName" ) String lastName,
        @FormParam( "email" ) String email,
        @FormParam( "mobile" ) String mobile,
        @FormParam( "role" ) String role)
    {
		AppResponse response    = null;
		UserService uService = new UserService();
		
		String serviceKey = httpHeaders.getHeaderString(LRHTTPHeaders.SERVICE_KEY);
		
		//validate Input
		uService.validateAuthData(userName, password);
		
		//Convert View To Model object if any (To-do : This can be done in better way)
		Long lmobile = 0L;
		try {
		    lmobile = Long.parseLong(mobile);
		} catch (NumberFormatException ex) {					
			//Suppress the warning
		}
		
		//Send to model using service              
		User user = uService.signUp(serviceKey, userName, password,
        							firstName, lastName, email,
        							lmobile, role);        
		if (user != null) {
			response = uService.createUserResponse(user);			
		} else {
			ErrorMessage errorMsg = new ErrorMessage("Issue while creating the user. Please try again", 500);
			response = new ErrorResponse(errorMsg);
		}
               		
		return response;
    }
	
	@GET
    @Path("/listuser" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse listUser()
    {		
		AppResponse response;
		UserService uService = new UserService();			

        List<User> userList  = uService.listUser();
      
        if (null != userList) {           
        	response = uService.createListUserResponse(userList);
        } else {
        	ErrorMessage errorMsg = new ErrorMessage("Not able to list users", 500);
			response = new ErrorResponse(errorMsg);
        }
              		
		return response;
    }
	
	@POST
    @Path("/login" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse login(
        @Context HttpHeaders httpHeaders,
        @FormParam( "username" ) String userName,
        @FormParam( "password" ) String password )
    {		
		AppResponse response;
		UserService uService = new UserService();
		
		String serviceKey = httpHeaders.getHeaderString(LRHTTPHeaders.SERVICE_KEY);
		
		//validate Input
		uService.validateAuthData(userName, password);
		
		//Send to model using service 
        User user  = uService.login(serviceKey, userName, password);
      
        if(null != user
        		&& null != user.getAuthKey()
        		&& !user.getAuthKey().equals("")) 
        {           
        	response = uService.createUserResponse(user);
        } else {
        	ErrorMessage errorMsg = new ErrorMessage("Issue while logging in. Please try again", 500);
			response = new ErrorResponse(errorMsg);
        }
              		
		return response;
    }
	
	@POST
    @Path("/logout")
	public Response logout(
	    @Context HttpHeaders httpHeaders )
	{
        try {
        	UserService uService = new UserService();
        	
            String serviceKey = httpHeaders.getHeaderString(LRHTTPHeaders.SERVICE_KEY);
            String authToken = httpHeaders.getHeaderString(LRHTTPHeaders.AUTH_TOKEN);
 
            uService.logout(serviceKey, authToken );
 
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
