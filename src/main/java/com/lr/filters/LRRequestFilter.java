package com.lr.filters;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.lr.exceptions.AuthException;
import com.lr.service.AutheticationService;


@Provider
@PreMatching
public class LRRequestFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestCtx) throws IOException 
	{				
		String path = requestCtx.getUriInfo().getPath();
        System.out.println("Filtering request path: " + path );
 
        // IMPORTANT!!! First, Acknowledge any pre-flight test from browsers for this case before validating the headers (CORS stuff)
        if (requestCtx.getRequest().getMethod().equals("OPTIONS")) {
            requestCtx.abortWith( Response.status( Response.Status.OK ).build() ); 
            return;
        }
 
        // Check: service key exists and is valid.
        AutheticationService authService = AutheticationService.getInstance();
        
        String serviceKey = requestCtx.getHeaderString( LRHTTPHeaders.SERVICE_KEY );
        
        System.out.println("serviceKey" + serviceKey);
 
        if ( !authService.isServiceKeyValid(serviceKey)) {
        	System.out.println("Invalid service key");
            requestCtx.abortWith( Response.status( Response.Status.UNAUTHORIZED ).build() ); 
            return;
        }
 
        System.out.println("path" + path);
        // For any methods besides login, the authToken must be verified
        if ( !path.startsWith("v1/user-service/login") && !path.startsWith("v1/user-service/signup") ) {
        	System.out.println("Not login or signup request. Need auth token");
            String authToken = requestCtx.getHeaderString( LRHTTPHeaders.AUTH_TOKEN );
            if ( !authService.isAuthTokenValid( serviceKey, authToken ) ) {
                requestCtx.abortWith( Response.status( Response.Status.UNAUTHORIZED ).build() );
            }
        }

	}
}
