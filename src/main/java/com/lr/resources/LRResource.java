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
import com.lr.model.Billingname;
import com.lr.model.Consignee;
import com.lr.model.Consigner;
import com.lr.model.LR;
import com.lr.model.LRChalan;
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
import com.lr.service.LRChalanService;
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
		@FormParam( "billingParty" ) String billingParty,
		@FormParam( "poNo" )         String poNo,
		@FormParam( "doNo" )         String doNo,
		@FormParam( "billingnameId") String billingnameId)		
    {
		AppResponse response = null;
		LrService lrService  = new LrService();		

		String serviceKey = httpHeaders.getHeaderString(LRHTTPHeaders.SERVICE_KEY);
		
		//get Consigner object
		Consigner consigner = null;
		Consignee consignee = null;
		Billingname billingname = null;
		
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
		
		//get partyname object
		if(billingnameId!=null && !billingnameId.equals("")){
			billingname  = lrService.getBillingname(billingnameId);
			if(null == billingnameId) {  
				ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from billingname table", 500);
				response = new ErrorResponse(errorMsg);
		     }		      
		}
          
		String status = "OPENED";

    	//Send to model using service              
		LR lr = lrService.newLR(serviceKey, vehileNo, vehicleOwner, consigner, consignee, billingParty,poNo,doNo,billingname,status);        
		if (lr != null) {
			response = lrService.createLRResponse(lr);			
		} else {
			ErrorMessage errorMsg = new ErrorMessage("Issue while creating the lr. Please try again", 500);
			response = new ErrorResponse(errorMsg);
		}               		
		return response;
    }
	
	//Create CHALAN
	@POST
    @Path("/lr-service/createChalan" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse createChalan(
        @Context HttpHeaders httpHeaders,       
        @FormParam( "lrNos" )     String lrNos,
        @FormParam( "chalanDetails" )     String chalanDetails)		
    {
		AppResponse response = null;
		LRChalanService lrChalanService  = new LRChalanService();
		System.out.println("lrno "+lrNos);
		System.out.println("chalanDetails "+chalanDetails);
		//validate Input
		lrChalanService.validateAuthData(lrNos);
		
		LRChalan lrChalan = null;
		lrChalan = lrChalanService.newLRChalan(lrNos,chalanDetails);
		
		if (null != lrChalan ) {
			response = lrChalanService.createLRChalanResponse(lrChalan);	
		} else {
			ErrorMessage errorMsg = new ErrorMessage("Issue In saving chalan details", 500);
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
	
	@POST
    @Path("/lr-service/list" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse getLRByDate(
        @Context HttpHeaders httpHeaders,       
        @FormParam( "lrDate" ) String lrDate,
        @FormParam( "multiLoad" ) String multiLoad,
        @FormParam( "status" ) String status,
        @FormParam( "isLRAttached" ) String isLRAttached)
    {
		AppResponse response = null;
		LrService lrService  = new LrService();              
              
  		List<LR> lrList = lrService.listlr(lrDate, multiLoad, status, isLRAttached);
    		
  		if (lrList != null) {
  			response = lrService.createLRListResponse(lrList);    					
    	} else {
    		ErrorMessage errorMsg = new ErrorMessage("Issue while getting LR List data. Please try again", 500);
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
		@FormParam( "billingParty" 	) 	String billingParty,
		@FormParam( "poNo" 	        ) 	String poNo,
		@FormParam( "doNo" 	        ) 	String doNo,
		@FormParam( "billingnameId" ) 	String billingnameId)		
    {
		AppResponse response    = null;
		LrService lrService = new LrService();
		
		lrService.validateAuthData(lrNo);		
		
		LR lr = null;
		if (lrNo != null && !lrNo.equals("") ) {
			lr  = lrService.findLR(lrNo);
			System.out.println("getting LR for LR No "+lrNo+ " value "+lr);
			if (null == lr) {  
				 ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from LR table", 500);
				 response = new ErrorResponse(errorMsg);
		     } else {
		    	 
		    	//get Consigner object
		 		Consigner consigner = null;
		 		Consignee consignee = null;
		 		Billingname billingname = null;
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
		 		
		 		 //get billingname object
		 		if (billingnameId !=null && !billingnameId.equals("")) {
		 			billingname  = lrService.getBillingname(billingnameId);
		 			 if(null == billingname) {  
		 				 ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from billingname table", 500);
		 				 response = new ErrorResponse(errorMsg);
		 		     }		 		      
		 		}
		 		
		 		
		 		
		 		lr = lrService.updateLR(vehileNo,
		 								vehicleOwner,
		 								consigner,
		 								consignee,						
		 								billingParty,
		 								poNo,
		 								doNo,
		 								billingname,
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
	
	@GET
    @Path("/listbillingnames" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse listBillingnames()
    {		
		AppResponse response;
		LrService lrService = new LrService();		

        List<Billingname> billingnameList  = lrService.listBillingname();
      
        if (null != billingnameList) {           
        	response = lrService.createListBillingnameResponse(billingnameList);
        } else {
        	ErrorMessage errorMsg = new ErrorMessage("Not able to list billingnames", 500);
			response = new ErrorResponse(errorMsg);
        }
              		
		return response;
    }
	

}
