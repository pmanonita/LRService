package com.lr.resources;


import java.util.HashSet;
import java.util.List;





import java.util.Set;

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
import com.lr.model.Consignee;
import com.lr.model.Consigner;
import com.lr.model.LR;
import com.lr.model.LRExpenditure;
import com.lr.model.LRIncome;
import com.lr.model.LROthers;
import com.lr.model.User;
import com.lr.response.AppResponse;
import com.lr.response.ErrorMessage;
import com.lr.response.ErrorResponse;
import com.lr.response.UserResponse;
import com.lr.response.Result;
import com.lr.service.AutheticationService;
import com.lr.service.LrService;
import com.lr.service.UserService;



/*
 * Version 1 Services for the Scraper
 */
@Path("/v1")
public class LRResource {	
		
	//Create LR	
	@POST
    @Path("/lr-service/newlr" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse newlr(
        @Context HttpHeaders httpHeaders,       
        @FormParam( "vehileNo" )     String vehileNo,
        @FormParam( "vehicleOwner" ) String vehicleOwner,
        @FormParam( "consignerId" )  String consignerId,
        @FormParam( "consigneeId" )  String consigneeId,        
		@FormParam( "billingParty" ) String billingParty)		
    {
		AppResponse response = null;
		LrService lrService  = new LrService();		

		String serviceKey = httpHeaders.getHeaderString(LRHTTPHeaders.SERVICE_KEY);
		
		//get Consigner object
		Consigner consigner = null;
		Consignee consignee = null;
		if (consignerId!=null && !consignerId.equals("")) {
			consigner  = lrService.getConsigner(consignerId);
			if (null == consigner) {  
				ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from consigner table", 500);
				response = new ErrorResponse(errorMsg);
			}
		}
        //get Consignee object
		if(consigneeId!=null && !consigneeId.equals("")){
			consignee  = lrService.getConsignee(consigneeId);
			if(null == consignee) {  
				ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from consignee table", 500);
				response = new ErrorResponse(errorMsg);
		     }		      
		}
              

    	//Send to model using service              
		LR lr = lrService.newLR(serviceKey, vehileNo, vehicleOwner, consigner, consignee, billingParty);        
		if (lr != null) {
			response = lrService.createLRResponse(lr);			
		} else {
			ErrorMessage errorMsg = new ErrorMessage("Issue while creating the lr. Please try again", 500);
			response = new ErrorResponse(errorMsg);
		}               		
		return response;
    }	

	@POST
    @Path("/lr-service/searchlr" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse searchlr(
        @Context HttpHeaders httpHeaders,       
        @FormParam( "lrNo" ) String lrId)		
    {
		AppResponse response = null;
		LrService lrService  = new LrService();
		              
       	//Send to model using service              
  		LR lr = lrService.findLR(lrId);
    		
  		if (lr != null) {  			
  			//To-do : Create response
  			response = lrService.createLRSearchResponse(lr);    					
    	} else {
    		ErrorMessage errorMsg = new ErrorMessage("Issue while getting LR data. Please try again", 500);
    		response = new ErrorResponse(errorMsg);
    	}
  		
  		return response;
    }

	//Update LR	
	@POST
    @Path("/lr-service/updatelr" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse updatelr(
        @Context HttpHeaders httpHeaders, 
        @FormParam( "lrNo" 			) 	String lrNo,
        @FormParam( "vehileNo" 		) 	String vehileNo,
        @FormParam( "vehicleOwner" 	) 	String vehicleOwner,
        @FormParam( "consignerId" 	) 	String consignerId,
        @FormParam( "consigneeId" 	) 	String consigneeId,        
		@FormParam( "billingParty" 	) 	String billingParty)		
    {
		AppResponse response    = null;
		LrService lrService = new LrService();
		
		lrService.validateAuthData(lrNo);		
		
		LR lr = null;
		if (lrNo != null && !lrNo.equals("") ) {
			lr  = lrService.findLR(lrNo);
			
			if (null == lr) {  
				 ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from LR table", 500);
				 response = new ErrorResponse(errorMsg);
		     } else {
		    	 
		    	//get Consigner object
		 		Consigner consigner = null;
		 		Consignee consignee = null;
		 		if (consignerId!=null && !consignerId.equals("")) {
		 			consigner  = lrService.getConsigner(consignerId);
		 			 if(null == consigner) {  
		 				 ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from consigner table", 500);
		 				 response = new ErrorResponse(errorMsg);
		 		     }
		 		}
		         //get Consinee object
		 		if (consigneeId!=null && !consigneeId.equals("")) {
		 			consignee  = lrService.getConsignee(consigneeId);
		 			 if(null == consignee) {  
		 				 ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from consignee table", 500);
		 				 response = new ErrorResponse(errorMsg);
		 		     }		 		      
		 		}
		 		
		 		lr = lrService.updateLR(vehileNo,
		 								vehicleOwner,
		 								consigner,
		 								consignee,						
		 								billingParty,
		 								lr);
		 		if (null == lr) {
		 			ErrorMessage errorMsg = new ErrorMessage("Issue while updating the lr. Please try again", 500);
	    			response = new ErrorResponse(errorMsg);
	 			} else {
	 				response = lrService.createLRResponse(lr);		
	 			}
		    	 
		     }
		}		

		return response;
    }
	
	@GET
    @Path("/listconsinors" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse listConsinors()
    {		
		AppResponse response;
		LrService lrService = new LrService();		

        List<Consigner> consignerList  = lrService.listConsigner();
      
        if (null != consignerList) {           
        	response = lrService.createListConsignerResponse(consignerList);
        } else {
        	ErrorMessage errorMsg = new ErrorMessage("Not able to list consigners", 500);
			response = new ErrorResponse(errorMsg);
        }
              		
		return response;
    }
	
	@GET
    @Path("/listconsinees" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse listConsinees()
    {		
		AppResponse response;
		LrService lrService = new LrService();		

        List<Consignee> consigneeList  = lrService.listConsignee();
      
        if (null != consigneeList) {           
        	response = lrService.createListConsigneeResponse(consigneeList);
        } else {
        	ErrorMessage errorMsg = new ErrorMessage("Not able to list consignees", 500);
			response = new ErrorResponse(errorMsg);
        }
              		
		return response;
    }
	

}
