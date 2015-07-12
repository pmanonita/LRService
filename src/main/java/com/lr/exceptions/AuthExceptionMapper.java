package com.lr.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.lr.response.ErrorMessage;
import com.lr.response.ErrorResponse;

@Provider
public class AuthExceptionMapper implements ExceptionMapper<AuthException> {
	
	@Override
	public Response toResponse(AuthException ex) {				
		ErrorMessage errorMsg = new ErrorMessage(ex.getMessage(), 401);		
		ErrorResponse eResponse = new ErrorResponse(errorMsg);
		return Response.status(errorMsg.getErrorCode()).entity(eResponse).build();
	}		
}