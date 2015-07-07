package com.lr.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.lr.response.ErrorMessage;
import com.lr.response.ErrorResponse;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {
	
	@Override
	public Response toResponse(DataNotFoundException ex) {		
		ErrorResponse eResponse = new ErrorResponse();
		eResponse.setCode(404);
		ErrorMessage errorMsg = new ErrorMessage(ex.getMessage());
		eResponse.setError(errorMsg);
		return Response.status(eResponse.getCode()).entity(eResponse).build();	}		
}