package com.lr.resources;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.lr.filters.LRHTTPHeaders;
import com.lr.service.AutheticationService;



@Path("/")
public class UserResource {
	
	
	@POST
    @Path("/login" )
    @Produces( MediaType.APPLICATION_JSON )
    public Response login(
        @Context HttpHeaders httpHeaders,
        @FormParam( "username" ) String username,
        @FormParam( "password" ) String password )
    {
		Response response = null;
		AutheticationService service = AutheticationService.getInstance();
		
		String serviceKey = httpHeaders.getHeaderString(LRHTTPHeaders.SERVICE_KEY);
		 
        String authToken = service.login(serviceKey, username, password);
        
        System.out.println(authToken);
 
        JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
        jsonObjBuilder.add( "auth_token", authToken );
        JsonObject jsonObj = jsonObjBuilder.build();
 
        response = Response.status(Status.OK).entity(jsonObj.toString()).build();
        
        System.out.println(jsonObj.toString());
        		
		return response;
    }
}
