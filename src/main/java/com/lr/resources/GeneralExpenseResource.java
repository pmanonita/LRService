package com.lr.resources;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.BeanParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.lr.filters.LRHTTPHeaders;
import com.lr.model.AccountSource;
import com.lr.model.Expense;
import com.lr.model.User;
import com.lr.response.AppResponse;
import com.lr.response.ErrorMessage;
import com.lr.response.ErrorResponse;
import com.lr.response.UserResponse;
import com.lr.response.Result;
import com.lr.service.AccountService;
import com.lr.service.AutheticationService;
import com.lr.service.GeneralExpenseService;
import com.lr.service.UserService;



@Path("/v1")
public class GeneralExpenseResource {
	
	@POST
    @Path("/general-expense/newexpense" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse newexpense(
        @FormParam( "amount" 	  ) String amount,
        @FormParam( "accountId"   ) String accountId,
        @FormParam( "date"	 	  ) String date,
        @FormParam( "status"      ) String status,
        @FormParam( "remark"      ) String remark)
    {
		AppResponse response = null;

		GeneralExpenseService expenseService = new GeneralExpenseService();
		
		//Validate
		expenseService.validateData(amount, accountId, date);
		
		//Find account
		AccountService accountService = new AccountService();
		AccountSource account = accountService.findAccount(accountId);
		
		if (null == account) {
			ErrorMessage errorMsg = new ErrorMessage("Issue while creating general expense. Account not found.", 500);
			response = new ErrorResponse(errorMsg);
			return response;
		}
	
		//Create expense              
		Expense expense = expenseService.createExpense(amount, account, date, status, remark);        
		if (expense != null) {
			response = expenseService.createExpenseResponse(expense);			
		} else {
			ErrorMessage errorMsg = new ErrorMessage("Issue while creating the general expense. Please try again", 500);
			response = new ErrorResponse(errorMsg);
		}
               		
		return response;
    }
	
	@POST
	@Path("/general-expense/editexpense" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse editexpense(
    	@FormParam( "id" 	  	  ) String id,
        @FormParam( "amount" 	  ) String amount,
        @FormParam( "accountId"   ) String accountId,
        @FormParam( "date"	 	  ) String date,
        @FormParam( "status"      ) String status,
        @FormParam( "remark"      ) String remark)
    {
		AppResponse response = null;

		GeneralExpenseService expenseService = new GeneralExpenseService();
		
		//Validate
		expenseService.validateData(amount, accountId, date);
		
		//Find account
		AccountService accountService = new AccountService();
		AccountSource account = accountService.findAccount(accountId);
		
		if (null == account) {
			ErrorMessage errorMsg = new ErrorMessage("Issue while editing general expense. Account not found.", 500);
			response = new ErrorResponse(errorMsg);
			return response;
		}
	
		//Edit expense              
		Expense expense = expenseService.editExpense(id, amount, account, date, status, remark);        
		if (expense != null) {
			response = expenseService.createExpenseResponse(expense);			
		} else {
			ErrorMessage errorMsg = new ErrorMessage("Issue while editing the general expense. Please try again", 500);
			response = new ErrorResponse(errorMsg);
		}
               		
		return response;
    }

	
	@POST
    @Path("/general-expense/list" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse listExpenses(
        @FormParam( "accountId"   ) String accountId,
        @FormParam( "fromDate"	  ) String fromDate,
        @FormParam( "toDate"      ) String toDate,
        @FormParam( "status"      ) String status)
    {
		AppResponse response = null;

		GeneralExpenseService expenseService = new GeneralExpenseService();		
	
		//Create expense              
		List<Expense> expenses = expenseService.listExpense(accountId, fromDate, toDate, status);        
		if (expenses != null) {
			response = expenseService.createListExpenseResponse(expenses);			
		} else {
			ErrorMessage errorMsg = new ErrorMessage("Issue while creating the general expense. Please try again", 500);
			response = new ErrorResponse(errorMsg);
		}
               		
		return response;
    }	

	
}
