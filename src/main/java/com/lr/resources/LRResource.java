package com.lr.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lr.exceptions.DataNotFoundException;
import com.lr.message.ServiceMessage;


/*
 * Version 1 Services for the Scraper
 */
@Path("/v1")
public class LRResource {
	
	private static final String S_Service = "LR Service";
	private static final String S_Version = "1.0.0";	
	
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public ServiceMessage getVersionJson()			
	{
		ServiceMessage info = new ServiceMessage();
		info.setServiceName(S_Service);
		info.setServiceVersion(S_Version);				
		return info;			
	}	

	
	//Login
	@Path("/user-service")	
	public UserResource getHaysResource()
	{
		return new UserResource();
	}	
	

}
