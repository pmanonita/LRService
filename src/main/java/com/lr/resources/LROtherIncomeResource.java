package com.lr.resources;


import java.util.HashSet;

import java.util.Set;

import javax.ws.rs.FormParam;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;



import com.lr.model.LR;
import com.lr.model.LROtherIncome;

import com.lr.response.AppResponse;
import com.lr.response.ErrorMessage;
import com.lr.response.ErrorResponse;
import com.lr.service.LROtherIncomeService;

import com.lr.service.LrService;




/*
 * Version 1 Services for the Scraper
 */
@Path("/v1")
public class LROtherIncomeResource {	
	
	//Create LROtherIncome	
	@POST
    @Path("/lr-service/addlrotherincome" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse addlrotherincome(
        @Context HttpHeaders httpHeaders,       
        @FormParam( "lrNo" 		) String lrNo,
        @FormParam( "amount" 	) String amount,
        @FormParam( "remarks" 	) String remarks)		
    {
		AppResponse response            = null;
		LROtherIncomeService lrOtherIncomeService = new LROtherIncomeService();
		LrService lrService             = new LrService();		

		long llrNo = 0;
		int iamount = 0;		
		try {	llrNo = Long.parseLong(lrNo);		} 	catch (NumberFormatException ex) {	}			
		try {	iamount = Integer.parseInt(amount);	} 	catch (NumberFormatException ex) {	}
		
		LR lr = null;
		if (lrNo !=null && !lrNo.equals("") && llrNo > 0) { 
			lr  = lrService.findLR(lrNo);

			if (null == lr) {  
				 ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from LR table", 500);
				 response = new ErrorResponse(errorMsg);
		     } else {
		    	//Send to model using service              
		    	LROtherIncome lrOtherIncome = lrOtherIncomeService.newLROtherIncome(llrNo,
		 														iamount,
		 														remarks);
		 								      
		 		if (lrOtherIncome != null) {
		 			Set<LROtherIncome> lrOtherIncomes = lr.getOtherIncomes();
		 			
		 			if(null == lrOtherIncomes) {
		 				lrOtherIncomes = new HashSet();		 				
		 			}
		 			lrOtherIncomes.add(lrOtherIncome);
		 			response = lrOtherIncomeService.createLROtherIncomeResponse(lrOtherIncomes);			
		 		} else {
		 			ErrorMessage errorMsg = new ErrorMessage("Issue while creating the lrOtherIncome. Please try again", 500);
		 			response = new ErrorResponse(errorMsg);
		 		}
		     }
		}	
		
               		
		return response;
    }
	
	@POST
    @Path("/lr-service/removelrotherincome" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse removelrotherincome(
        @Context HttpHeaders httpHeaders,       
        @FormParam( "lrOtherIncomeId" 		) String lrOtherIncomeId,
        @FormParam( "lrNo" 		                ) String lrNo)       		
    {
		AppResponse response            = null;
		LROtherIncomeService lrOtherIncomeService = new LROtherIncomeService();
		LrService lrService             = new LrService();		

		long llrOtherIncomeId = 0;
		long llrNo                 = 0;
		try {	llrOtherIncomeId = Long.parseLong(lrOtherIncomeId);		} 	catch (NumberFormatException ex) {	}
		try {	llrNo                 = Long.parseLong(lrNo);		                } 	catch (NumberFormatException ex) {	}	
				
		LR lr = null;
		if ( llrOtherIncomeId > 0 && llrNo > 0 ) {		
				//Send to model using service              
				LROtherIncome lrOtherIncome = lrOtherIncomeService.findLROtherIncome(llrOtherIncomeId);
			 								      
				if ( lrOtherIncome != null ) {
					if ( lrOtherIncomeService.removeLROtherIncome(lrOtherIncome) ) {
						lr  = lrService.findLR(lrNo);
						if ( null == lr ) {  
							ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from LR table", 500);
							response = new ErrorResponse(errorMsg);
						} else {
							Set<LROtherIncome> lrOtherIncomes = lr.getOtherIncomes();		 						 				
				 			response = lrOtherIncomeService.createLROtherIncomeResponse(lrOtherIncomes);	
						}
			 				
			 		} else {
			 			ErrorMessage errorMsg = new ErrorMessage("Issue while Removing LROtherIncome. Please try again", 500);
			 			response = new ErrorResponse(errorMsg);
			 		}		
			 			
			 	} else {
			 		ErrorMessage errorMsg = new ErrorMessage("Issue while getting LROtherIncome. Please try again", 500);
			 		response = new ErrorResponse(errorMsg);
			 	}	
			
		} else {
	 		ErrorMessage errorMsg = new ErrorMessage("LrExpenditureID is wrong.", 500);
	 		response = new ErrorResponse(errorMsg);
	 	}	
		
		return response;
    }
		

}
