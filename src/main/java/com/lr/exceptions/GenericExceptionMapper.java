package com.lr.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.lr.response.ErrorMessage;
import com.lr.response.ErrorResponse;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Override 
	public Response toResponse(Throwable ex) {		
		ErrorMessage errorMsg = new ErrorMessage();	
		if( null != ex.getMessage() && !ex.getMessage().equals("")) {
			errorMsg.setMessage(ex.getMessage());
		} else {
				errorMsg.setMessage("Server can not process your request");
		}
		setHTTPStatus(ex, errorMsg);
		
		ErrorResponse eResponse = new ErrorResponse(errorMsg);
		return Response.status(errorMsg.getErrorCode()).entity(eResponse).build();		
	}

	private void setHTTPStatus(Throwable ex, ErrorMessage errorMsg) {
		if (ex instanceof WebApplicationException) {
			errorMsg.setErrorCode(((WebApplicationException) ex).getResponse().getStatus());			
		} else {
			errorMsg.setErrorCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
		}		
	}		
}
