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

import com.lr.response.AppResponse;
import com.lr.response.ErrorMessage;
import com.lr.response.ErrorResponse;
import com.lr.service.LRExpenditureService;
import com.lr.service.LrService;


/*
 * Version 1 Services for the Scraper
 */
@Path("/v1")
public class LRExpenditureResource {
	
		
	//Create LRExpenditure	
	@POST
    @Path("/lr-service/addlrexpenditure" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse addlrexpenditure(
        @Context HttpHeaders httpHeaders,       
        @FormParam( "lrNo" ) String lrNo,
        @FormParam( "freightToBroker" 	 ) 	String freightToBroker,
        @FormParam( "extraPayToBroker" 	 ) 	String extraPayToBroker,
        @FormParam( "advance" 			 )	String advance,
        @FormParam( "balanceFreight" 	 ) 	String balanceFreight,
        @FormParam( "loadingCharges" 	 ) 	String loadingCharges,
		@FormParam( "unloadingCharges" 	 ) 	String unloadingCharges,
		@FormParam( "loadingDetBroker" 	 ) 	String loadingDetBroker,
		@FormParam( "unloadingDetBroker" )  String unloadingDetBroker)		
    {
		AppResponse response                      = null;
		LRExpenditureService lrExpenditureService = new LRExpenditureService();
		LrService lrService                       = new LrService();
		
		//validate Input
		lrExpenditureService.validateAuthData(lrNo);	
		
		//Convert View To Model object if any 
		long llrNo = 0;
		int iferightToBroker = 0, iextraPayToBroker = 0, iadvance = 0, ibalanceFreight = 0, iloadingCharges = 0;
		int iloadingDetBroker = 0, iunloadingCharges = 0, iunloadingDetBroker = 0;
		
		try {	llrNo 			  = Long.parseLong(lrNo);					} 	catch (NumberFormatException ex) {	}		
		try {	iferightToBroker  = Integer.parseInt(freightToBroker);		} 	catch (NumberFormatException ex) {	}				
		try {	iextraPayToBroker = Integer.parseInt(extraPayToBroker);		} 	catch (NumberFormatException ex) {	}
		try {	iadvance 		  = Integer.parseInt(advance);				} 	catch (NumberFormatException ex) {	}		
		try {	ibalanceFreight   = Integer.parseInt(balanceFreight);		} 	catch (NumberFormatException ex) {	}		
		try {	iloadingCharges   = Integer.parseInt(loadingCharges);		} 	catch (NumberFormatException ex) {	}
		try {	iunloadingCharges = Integer.parseInt(unloadingCharges);		} 	catch (NumberFormatException ex) {	}
		try {	iloadingDetBroker = Integer.parseInt(loadingDetBroker);		} 	catch (NumberFormatException ex) {	}			
		try {	iunloadingDetBroker = Integer.parseInt(unloadingDetBroker);	} 	catch (NumberFormatException ex) {	}
		
		LR lr = null;
		if (lrNo!=null && !lrNo.equals("") && llrNo > 0) {
			lr  = lrService.findLR(lrNo);

			if (null == lr) {  
				 ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from LR table", 500);
				 response = new ErrorResponse(errorMsg);
		     }else{
		    	 
		    	 LRExpenditure lrExpenditure = lr.getLrexpenditureId();
		    	 if(lrExpenditure != null) {
		    		 //Expenditure should be updated
		    		 lrExpenditure = lrExpenditureService.updateLRExpenditure(llrNo,
								iferightToBroker,
								iextraPayToBroker,
								iadvance,
								ibalanceFreight,
								iloadingCharges,
								iunloadingCharges,
								iloadingDetBroker,
								iunloadingDetBroker,								
								lrExpenditure); 
		    		
		    	 }else{
		    		 //Expenditure should be newly added
		    		 lrExpenditure = lrExpenditureService.newLRExpenditure(llrNo,
								iferightToBroker,
								iextraPayToBroker,
								iadvance,
								ibalanceFreight,
								iloadingCharges,
								iunloadingCharges,
								iloadingDetBroker,								
								iunloadingDetBroker); 
		    		
		    	 }
		 		      
		 		if (lrExpenditure != null) {		 			
		 			lr=lrService.updateExpenditureToLR(lrExpenditure,lr);
		 			if (null == lr) {
		 				ErrorMessage errorMsg = new ErrorMessage("Issue while updating LR with expediture. Please try again", 500);
			 			response = new ErrorResponse(errorMsg);
		 			} else {
		 				response = lrExpenditureService.createLRExpenditureResponse(lrExpenditure);	
		 			}
		 		
		 		} else {
		 			ErrorMessage errorMsg = new ErrorMessage("Issue while creating/modifying LR expenditure. Please try again", 500);
		 			response = new ErrorResponse(errorMsg);
		 		}            
		    	 
		    }
		}
		   		
		return response;
    }	

}
