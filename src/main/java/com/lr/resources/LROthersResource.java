package com.lr.resources;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.lr.exceptions.DataNotFoundException;
import com.lr.filters.LRHTTPHeaders;
import com.lr.model.LR;
import com.lr.model.LROthers;
import com.lr.model.User;
import com.lr.response.AppResponse;
import com.lr.response.ErrorMessage;
import com.lr.response.ErrorResponse;
import com.lr.service.LROthersService;
import com.lr.service.LrService;
import com.lr.service.UserService;



/*
 * Version 1 Services for the Scraper
 */
@Path("/v1")
public class LROthersResource {	
	
	//Create LROthers	
	@POST
    @Path("/lr-service/addlrothers" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse addlrothers(
        @Context HttpHeaders httpHeaders,       
        @FormParam( "lrNo" 		) String lrNo,
        @FormParam( "amount" 	) String amount,
        @FormParam( "remarks" 	) String remarks)		
    {
		AppResponse response            = null;
		LROthersService lrOthersService = new LROthersService();
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
		 		LROthers lrOthers = lrOthersService.newLROthers(llrNo,
		 														iamount,
		 														remarks);
		 								      
		 		if (lrOthers != null) {
		 			Set<LROthers> lrOtherExps = lr.getOtherExpenditures();
		 			
		 			if(null == lrOtherExps) {
		 				lrOtherExps = new HashSet();		 				
		 			}
		 			lrOtherExps.add(lrOthers);
		 			response = lrOthersService.createLROthersResponse(lrOtherExps);			
		 		} else {
		 			ErrorMessage errorMsg = new ErrorMessage("Issue while creating the lrOthers. Please try again", 500);
		 			response = new ErrorResponse(errorMsg);
		 		}
		     }
		}	
		
               		
		return response;
    }
	
	@POST
    @Path("/lr-service/removelrothers" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse removelrothers(
        @Context HttpHeaders httpHeaders,       
        @FormParam( "lrOtherExpenditureId" 		) String lrOtherExpenditureId,
        @FormParam( "lrNo" 		                ) String lrNo)       		
    {
		AppResponse response            = null;
		LROthersService lrOthersService = new LROthersService();
		LrService lrService             = new LrService();		

		long llrOtherExpenditureId = 0;
		long llrNo                 = 0;
		try {	llrOtherExpenditureId = Long.parseLong(lrOtherExpenditureId);		} 	catch (NumberFormatException ex) {	}
		try {	llrNo                 = Long.parseLong(lrNo);		                } 	catch (NumberFormatException ex) {	}	
				
		LR lr = null;
		if ( llrOtherExpenditureId > 0 && llrNo > 0 ) {		
				//Send to model using service              
				LROthers lrOthers = lrOthersService.findLROtherExpenditure(llrOtherExpenditureId);
			 								      
				if ( lrOthers != null ) {
					if ( lrOthersService.removeLROtherExpenditure(lrOthers) ) {
						lr  = lrService.findLR(lrNo);
						if ( null == lr ) {  
							ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from LR table", 500);
							response = new ErrorResponse(errorMsg);
						} else {
							Set<LROthers> lrOtherExps = lr.getOtherExpenditures();		 						 				
				 			response = lrOthersService.createLROthersResponse(lrOtherExps);	
						}
			 				
			 		} else {
			 			ErrorMessage errorMsg = new ErrorMessage("Issue while Removing LROtherExpenditure. Please try again", 500);
			 			response = new ErrorResponse(errorMsg);
			 		}		
			 			
			 	} else {
			 		ErrorMessage errorMsg = new ErrorMessage("Issue while getting LROtherExpenditure. Please try again", 500);
			 		response = new ErrorResponse(errorMsg);
			 	}	
			
		} else {
	 		ErrorMessage errorMsg = new ErrorMessage("LrExpenditureID is wrong.", 500);
	 		response = new ErrorResponse(errorMsg);
	 	}	
		
		return response;
    }
		

}
