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



/*
 * Version 1 Services for the Scraper
 */
@Path("/v1")
public class LRResource {
	
	//Login
	@Path("/user-service")	
	public UserResource getHaysResource()
	{
		return new UserResource();
	}	
	

}
