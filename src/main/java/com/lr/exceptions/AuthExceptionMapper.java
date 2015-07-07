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
		ErrorResponse eResponse = new ErrorResponse();
		eResponse.setCode(401);
		ErrorMessage errorMsg = new ErrorMessage(ex.getMessage());
		eResponse.setError(errorMsg);
		return Response.status(eResponse.getCode()).entity(eResponse).build();	}		
}