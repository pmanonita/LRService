package com.lr.filters;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class LRResponseFilter implements ContainerResponseFilter {	
	 
    @Override
    public void filter( ContainerRequestContext requestCtx, 
    					ContainerResponseContext responseCtx ) 
    	throws IOException 
    {
 
        System.out.println("Filtering REST Response");
 
        // You may further limit certain client IPs with Access-Control-Allow-Origin instead of '*'
        responseCtx.getHeaders().add( "Access-Control-Allow-Origin", "*" );    
        
        responseCtx.getHeaders().add( "Access-Control-Allow-Credentials", "true" );
        responseCtx.getHeaders().add( "Access-Control-Allow-Methods", "GET, POST, DELETE, PUT" );
        responseCtx.getHeaders().add( "Access-Control-Allow-Headers", LRHTTPHeaders.SERVICE_KEY + ", " + LRHTTPHeaders.AUTH_TOKEN );
    }
}
