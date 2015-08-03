package com.lr.resources;


import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.lr.model.AccountSource;
import com.lr.response.AccountListResponse;
import com.lr.response.AppResponse;
import com.lr.response.ErrorMessage;
import com.lr.response.ErrorResponse;
import com.lr.service.AccountService;



@Path("/v1")
public class AccountResource {
	
	@GET
    @Path("/account/list" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse listAccount()
    {		
		AppResponse response;
		AccountService acctService = new AccountService();		

        List<AccountSource> accounts  = acctService.listAccount();
      
        if (null != accounts) {           
        	response = new AccountListResponse(accounts);
        } else {
        	ErrorMessage errorMsg = new ErrorMessage("Not able to list accounts", 500);
			response = new ErrorResponse(errorMsg);
        }
              		
		return response;
    }	
}
