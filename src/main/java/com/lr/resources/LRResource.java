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
import com.lr.model.LRBill;
import com.lr.model.LRChalan;
import com.lr.model.LRExpenditure;
import com.lr.model.LRIncome;
import com.lr.model.LROthers;
import com.lr.model.LRTransaction;
import com.lr.model.User;
import com.lr.response.AppResponse;
import com.lr.response.ErrorMessage;
import com.lr.response.ErrorResponse;
import com.lr.response.UserResponse;
import com.lr.response.Result;
import com.lr.service.AutheticationService;
import com.lr.service.LRBillService;
import com.lr.service.LRChalanService;
import com.lr.service.LRTransactionService;
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
		@FormParam( "billingnameId") String billingnameId,
		@FormParam( "multiLoad")     String multiLoad,
		@FormParam( "userName")      String userName,
		@FormParam( "status")        String status)		
    {
		AppResponse response = null;
		LrService lrService  = new LrService();
		
		if(null == status || (null != status && status.equals("") 
				|| (null != status && !status.equals("Open") ))) {
			ErrorMessage errorMsg = new ErrorMessage("Invalid status while creating the LR", 500);
    		response = new ErrorResponse(errorMsg);
    		return response;
		}
				
		//get Consigner object
		Consigner consigner = null;
		Consignee consignee = null;
		Billingname billingname = null;
		
		if (consignerId!=null && !consignerId.equals("")) {
			consigner  = lrService.getConsigner(consignerId);
			if (null == consigner) {  
				ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from consigner table", 500);
				response = new ErrorResponse(errorMsg);
				return response;
			}
		}
        //get Consignee object
		if(consigneeId!=null && !consigneeId.equals("")){
			consignee  = lrService.getConsignee(consigneeId);
			if(null == consignee) {  
				ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from consignee table", 500);
				response = new ErrorResponse(errorMsg);
				return response;
		     }		      
		}
		
		//get partyname object
		if(billingnameId !=null && !billingnameId.equals("")){
			billingname  = lrService.getBillingname(billingnameId);
			if(null == billingname) {  
				ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from billingname table", 500);
				response = new ErrorResponse(errorMsg);
				return response;
		     }		      
		}
          
//		String status = "OPENED";
//		if(multiLoad != null && !multiLoad.equals("true")) {
//			multiLoad = "false";
//		}

    	//Send to model using service              
		LR lr = lrService.newLR(vehileNo, vehicleOwner, consigner, consignee, billingParty,
				poNo, doNo, billingname, status, multiLoad, userName);
		
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
		LrService lrService              = new LrService();
		
		//validate Input
		lrChalanService.validateAuthData(lrNos);
		
		String[] lrIds = lrNos.split(",");
		
		for (int i=0; i < lrIds.length; i++) {
			long llrNo = 0;
			try {	llrNo = Long.parseLong(lrIds[i]); } 	catch (NumberFormatException ex) {	}	
			
			LR lr = null;
			if (llrNo > 0) {
				lr  = lrService.findLR(lrIds[i]);
				if (null == lr) {  
					 ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from LR table for "+lrIds[i], 500);
					 response = new ErrorResponse(errorMsg);
			     }else{
			    	 
			    	 LRChalan lrChalan = lr.getLrchalanId();
			    	 if(lrChalan != null) {
			    		 //Chalan should be updated
			    		 lrChalan = lrChalanService.updateLRChalan(lrIds[i],chalanDetails,lrChalan); 
			    		
			    	 }else{
			    		 //Expenditure should be newly added
			    		 lrChalan = lrChalanService.newLRChalan(lrNos,chalanDetails);
			    		
			    	 }
			    	 
			    	 if (null != lrChalan ) {
			    		 lr=lrService.updateChalanToLR(lrChalan,lr);
				 		if (null == lr) {
				 			ErrorMessage errorMsg = new ErrorMessage("Issue while updating LR with chalan. Please try again", 500);
					 		response = new ErrorResponse(errorMsg);
				 		} else {
				 			response = lrChalanService.createLRChalanResponse(lrChalan);	
				 		}
			 			
			 		} else {
			 			ErrorMessage errorMsg = new ErrorMessage("Issue In saving chalan details", 500);
			 			response = new ErrorResponse(errorMsg);
			 		}
			    	 
			     }
				
			}
			
		}		
		
		           		
		return response;
    }
	
	//Create BILL
	@POST
    @Path("/lr-service/createBill" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse createBill(
        @Context HttpHeaders httpHeaders,       
        @FormParam( "lrNos" )     String lrNos,
        @FormParam( "billDetails" )     String billDetails)		
    {
		AppResponse response = null;
		LRBillService lrBillService  = new LRBillService();
		LrService lrService                       = new LrService();
		
		//validate Input
		lrBillService.validateAuthData(lrNos);
		
		String[] lrIds = lrNos.split(",");
		
		for (int i=0;i<lrIds.length;i++) {
			long llrNo = 0;
			try {	llrNo = Long.parseLong(lrIds[i]);	} 	catch (NumberFormatException ex) {	}	
			LR lr = null;
			if (llrNo > 0) {
				lr  = lrService.findLR(lrIds[i]);
				if (null == lr) {  
					 ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from LR table for "+lrIds[i], 500);
					 response = new ErrorResponse(errorMsg);
			    }else{
			    	
			    	LRBill lrBill = lr.getLrbillId();
			    	if(lrBill != null) {
			    		//Bill should be updated
			    		lrBill = lrBillService.updateLRBill(lrIds[i],billDetails,lrBill); 
			    		
			    	 }else{
			    		 //Expenditure should be newly added
			    		 lrBill = lrBillService.newLRBill(lrNos,billDetails);
			    		
			    	 }
			    	 
			    	 if (null != lrBill ) {
			    		 lr=lrService.updateBillToLR(lrBill,lr);
				 		if (null == lr) {
				 			ErrorMessage errorMsg = new ErrorMessage("Issue while updating LR with bill. Please try again", 500);
					 		response = new ErrorResponse(errorMsg);
				 		} else {
				 			response = lrBillService.createLRBillResponse(lrBill);	
				 		}
			 			
			 		} else {
			 			ErrorMessage errorMsg = new ErrorMessage("Issue In saving bill details", 500);
			 			response = new ErrorResponse(errorMsg);
			 		}
			    	 
			    }
			}
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
    public AppResponse lrList (
        @Context HttpHeaders httpHeaders,       
        @FormParam( "lrDate" ) String lrDate,
        @FormParam( "multiLoad" ) String multiLoad,
        @FormParam( "status" ) String status,
        @FormParam( "isLRAttached" ) String isLRAttached)
    {
		AppResponse response = null;
		LrService lrService  = new LrService();              
              
  		List<LR> lrList = lrService.listlr(lrDate, multiLoad, status, isLRAttached);
  		System.out.println("list size "+lrList.size());
    		
  		if (lrList != null) {
  			response = lrService.createLRListResponse(lrList,"");    					
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
		@FormParam( "billingnameId" ) 	String billingnameId,
		@FormParam( "multiLoad"		)  	String multiLoad,
		@FormParam( "userName"		)  	String userName)		
    
    {
		AppResponse response    = null;
		LrService lrService = new LrService();
		
		lrService.validateAuthData(lrNo);		
		
		LR lr = null;
		if (lrNo != null && !lrNo.equals("") ) {
			lr  = lrService.findLR(lrNo);
			System.out.println("updating multilod "+multiLoad);
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
		 		
		 		if (multiLoad == null || ( multiLoad != null && !multiLoad.equals("true") )) {
					multiLoad = "false";
				}
		 		
		 		
		 		lr = lrService.updateLR(vehileNo,
		 								vehicleOwner,
		 								consigner,
		 								consignee,						
		 								billingParty,
		 								poNo,
		 								doNo,
		 								billingname,
		 								multiLoad,
		 								userName,
		 								lr.getStatus(),
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
	
	@POST
    @Path("/lr-service/createtransaction" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse createTransaction(       
        @FormParam( "lrIds" ) String  strLrIds,
        @FormParam( "status" ) String status)
    {
		AppResponse response = null;
		LrService lrService  = new LrService();
		
		if(null == status || (null != status && status.equals("") 
				|| (null != status && !status.equals("Open") ))) {
			ErrorMessage errorMsg = new ErrorMessage("Invalid status while creating the transaction", 500);
    		response = new ErrorResponse(errorMsg);
    		return response;
		}
		if(null == strLrIds || (null != strLrIds && strLrIds.equals(""))) {
			ErrorMessage errorMsg = new ErrorMessage("No LR ids found for attaching. Please check", 500);
    		response = new ErrorResponse(errorMsg);
    		return response;
		}
		
		String[] lrIds = null;
		if(strLrIds.indexOf(",") != -1) {
			lrIds = strLrIds.split(",");
		} else {
			ErrorMessage errorMsg = new ErrorMessage("No LR ids found for attaching. Please check", 500);
    		response = new ErrorResponse(errorMsg);
    		return response;
		}
		
		Set<LR> lrs = new HashSet<LR>();
		for (String lrid : lrIds) {			
			LR lr = lrService.findLR(lrid);
			if (null != lr) {
				lrs.add(lr);
			} else {
				ErrorMessage errorMsg = new ErrorMessage("Not able to find lr with id "+ lrid, 500);
	    		response = new ErrorResponse(errorMsg);
	    		return response;
			}
		}		
		
        LRTransactionService lrTransService = new LRTransactionService();
  		LRTransaction trans = lrTransService.createLRTransaction(lrs, status);
    		
  		if (trans != null) {
  			response = lrTransService.createTransactionResponse(trans);  			
    	} else {
    		ErrorMessage errorMsg = new ErrorMessage("Issue while creating Multi LR. Please try again", 500);
    		response = new ErrorResponse(errorMsg);
    	}
  		
  		return response;
    }
	
	@POST
    @Path("/lr-service/updateStatusInLRList" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse updateStatusInLRList(
        @Context HttpHeaders httpHeaders, 
        @FormParam( "lrDate" ) String lrDate,
        @FormParam( "multiLoad" ) String multiLoad,
        @FormParam( "status" ) String status,
        @FormParam( "isLRAttached" ) String isLRAttached,
        @FormParam( "lrIds" ) String strLrIds)
    {
		AppResponse response = null;
		LrService lrService  = new LrService();
		
		if(null == status || (null != status && status.equals(""))) {
			ErrorMessage errorMsg = new ErrorMessage("Invalid status", 500);
    		response = new ErrorResponse(errorMsg);
    		return response;
		}
		if(null == strLrIds || (null != strLrIds && strLrIds.equals(""))) {
			ErrorMessage errorMsg = new ErrorMessage("No LR ids found for updating status. Please check", 500);
    		response = new ErrorResponse(errorMsg);
    		return response;
		}
		
		String[] lrIds = strLrIds.split(",");		
		List<String> errorLRIds =new ArrayList<String>();
		for (String lrid : lrIds) {			
			LR lr = lrService.findLR(lrid);
			if (null != lr) {
				lr = lrService.updateLR(lr.getVehicleNo(),
										lr.getVehicleOwner(),
										lr.getConsignerId(),
										lr.getConsigneeId(),						
										lr.getBillingToParty(),
										lr.getPoNo(),
										lr.getDoNo(),
										lr.getBillingnameId(),
										lr.getMultiLoad(),
										lr.getUserName(),
										status,
										lr);
				if (null == lr) {
					ErrorMessage errorMsg = new ErrorMessage("Issue while updating the lr with status. Please try again", 500);
					response = new ErrorResponse(errorMsg);
					errorLRIds.add(lrid);
				} else {
					response = lrService.createLRResponse(lr);		
				}
			} else {
				ErrorMessage errorMsg = new ErrorMessage("Not able to find lr with id "+ lrid, 500);
	    		response = new ErrorResponse(errorMsg);
	    		errorLRIds.add(lrid);	    		
			}
		}		
		
		List<LR> lrList = lrService.listlr(lrDate, multiLoad, status, isLRAttached);
  		
    		
  		if (lrList != null) {
  			String message = "";
  			if (errorLRIds.size() > 0) {
  				message = "Issue in updating status for lrnos "+errorLRIds;
  			}else{
  				message = "All lrnos are updated with status successfully";
  			}
  			response = lrService.createLRListResponse(lrList,message);    					
    	} else {
    		ErrorMessage errorMsg = new ErrorMessage("Issue while getting LR List data. Please try again", 500);
    		response = new ErrorResponse(errorMsg);
    	}
  		
  		
  		return response;
    }

	

}
