package com.lr.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import com.lr.filters.LRHTTPHeaders;

import com.lr.model.Consignee;
import com.lr.model.LR;
import com.lr.model.LRExpenditure;
import com.lr.model.LRIncome;

import com.lr.response.AppResponse;
import com.lr.response.ErrorMessage;
import com.lr.response.ErrorResponse;
import com.lr.service.LRExpenditureService;
import com.lr.service.LRIncomeService;
import com.lr.service.LrService;

/*
 * Version 1 Services for the LR
 */
@Path("/v1")
public class LRIncomeResource {
	
		
	//Create LRExpenditure	
	@POST
    @Path("/lr-service/addlrincome" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse addlrexpenditure(
        @Context HttpHeaders httpHeaders,
        @FormParam( "lrNo"               ) String lrNo,
        @FormParam( "freightToBroker"    ) String freightToBroker,
        @FormParam( "extraPayToBroker"   ) String extraPayToBroker,       
        @FormParam( "loadingCharges"     ) String loadingCharges,
		@FormParam( "unloadingCharges"   ) String unloadingCharges,
		@FormParam( "loadingDetBroker"   ) String loadingDetBroker,
		@FormParam( "unloadingDetBroker" ) String unloadingDetBroker)		
    {
		AppResponse response    = null;
		LRIncomeService lrIncomeService = new LRIncomeService();
		LrService lrService = new LrService();			
		
		//validate Input
		lrIncomeService.validateAuthData(lrNo);	
		
		//Convert View To Model object if any
		long llrNo = 0;
		int iferightToBroker = 0 , iextraPayToBroker = 0, iloadingCharges = 0; 
		int iunloadingCharges= 0 , iloadingDetBroker = 0, iunloadingDetBroker = 0;
		
		try {	llrNo               = Long.parseLong(lrNo);					}   catch (NumberFormatException ex)  {  }		
		try {	iferightToBroker    = Integer.parseInt(freightToBroker);	}	catch (NumberFormatException ex) {	}		
		try {	iextraPayToBroker   = Integer.parseInt(extraPayToBroker);	} 	catch (NumberFormatException ex) {	}				
		try {	iloadingCharges     = Integer.parseInt(loadingCharges);		}	catch (NumberFormatException ex) {	}		
		try {	iunloadingCharges   = Integer.parseInt(unloadingCharges);	}	catch (NumberFormatException ex) {	}				
		try {	iloadingDetBroker   = Integer.parseInt(loadingDetBroker);	}	catch (NumberFormatException ex) {	}		
		try {	iunloadingDetBroker = Integer.parseInt(unloadingDetBroker);	} 	catch (NumberFormatException ex) {  }
		
		LR lr = null;
		if (lrNo != null && !lrNo.equals("") && llrNo > 0) {
			lr  = lrService.getLr(lrNo);
			if(null == lr) {  
				ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from LR table", 500);
				response = new ErrorResponse(errorMsg);
		    } else {
		    	//Send to model using service              
		    	LRIncome lrIncome = lrIncomeService.newLRIncome(llrNo,
		 														iferightToBroker,
		 														iextraPayToBroker,
		 														iloadingCharges,
		 														iunloadingCharges,
		 														iloadingDetBroker,
		 														iunloadingDetBroker);        
		 		if (lrIncome != null) {		 			
		 			lr=lrService.updateIncomeToLR(lrIncome,lr);
		 			if (null == lr) {
		 				ErrorMessage errorMsg = new ErrorMessage("Issue while updating LR with income. Please try again", 500);
			 			response = new ErrorResponse(errorMsg);
		 			} else{
		 				response = lrIncomeService.createLRIncomeResponse(lrIncome);	
		 			}		 		
		 		} else {
		 			ErrorMessage errorMsg = new ErrorMessage("Issue while creating the lr. Please try again", 500);
		 			response = new ErrorResponse(errorMsg);
		 		}
		    }
		}

		return response;
    }
}
