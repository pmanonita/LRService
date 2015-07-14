package com.lr.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.lr.response.ErrorMessage;
import com.lr.response.ErrorResponse;

@Provider
public class SignupExceptionMapper implements ExceptionMapper<SignupException> {
	
	@Override
	public Response toResponse(SignupException ex) {				
		ErrorMessage errorMsg = new ErrorMessage(ex.getMessage(), 422);		
		ErrorResponse eResponse = new ErrorResponse(errorMsg);
		return Response.status(errorMsg.getErrorCode()).entity(eResponse).build();
	}		
}