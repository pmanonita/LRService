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
import com.lr.model.Expense;
import com.lr.model.LR;
import com.lr.model.LRBill;
import com.lr.model.LRChalan;
import com.lr.model.LRExpenditure;
import com.lr.model.LRIncome;
import com.lr.model.LROthers;
import com.lr.model.LRTransOtherExp;
import com.lr.model.LRTransOtherIncome;
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
import com.lr.service.LRExpenditureService;
import com.lr.service.LROtherIncomeService;
import com.lr.service.LROthersService;
import com.lr.service.LRTransOtherExpService;
import com.lr.service.LRTransOtherIncomeService;
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
        @FormParam( "chalanDetails" )     String chalanDetails,
        @FormParam( "transId" )           String transId)		
    {
		AppResponse response = null;
		LRChalanService lrChalanService  = new LRChalanService();
		LrService lrService              = new LrService();
		
		
		//validate Input
		lrChalanService.validateAuthData(lrNos);
		long ltransid = 0;
		try {	ltransid = Long.parseLong(transId); } 	catch (NumberFormatException ex) {	}	
		if (ltransid > 0) {
			System.out.println("multichalan");
			LRTransactionService transactionService = new LRTransactionService();
			LRTransaction lrTransaction = null;
			lrTransaction  = transactionService.findTransaction(transId);
			if (null == lrTransaction) {  
				 ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from Transaction table for "+lrNos, 500);
				 response = new ErrorResponse(errorMsg);
				 return response;
			}else {
				 LRChalan lrChalan = lrTransaction.getTranschalanId();
		    	 if(lrChalan != null) {
		    		 //Chalan should be updated
		    		 lrChalan = lrChalanService.updateLRChalan(lrNos,chalanDetails,lrChalan); 
		    		
		    	 }else{
		    		 //Expenditure should be newly added
		    		 lrChalan = lrChalanService.newLRChalan(lrNos,chalanDetails);
		    		
		    	 }
		    	 
		    	 if (null != lrChalan ) {
		    		 lrTransaction = transactionService.updateChalanToTransaction(lrChalan,lrTransaction);
			 		if (null == lrTransaction) {
			 			ErrorMessage errorMsg = new ErrorMessage("Issue while updating Transaction with chalan. Please try again", 500);
				 		response = new ErrorResponse(errorMsg);
				 		return response;
			 		} else {
			 			response = lrChalanService.createLRChalanResponse(lrChalan);	
			 		}
		 			
		 		} else {
		 			ErrorMessage errorMsg = new ErrorMessage("Issue In saving chalan details", 500);
		 			response = new ErrorResponse(errorMsg);
		 		}
				
			}
		}else {
			if(lrNos.indexOf(",")>-1) {
				ErrorMessage errorMsg = new ErrorMessage("Issue In saving chalan details,Transaction Id is missing", 500);
	 			response = new ErrorResponse(errorMsg);
	 			return response;
			}
			long llrNo = 0;
			try {	llrNo = Long.parseLong(lrNos); } 	catch (NumberFormatException ex) {	}	
			if ( llrNo > 0) {
				LR lr = null;
				lr  = lrService.findLR(lrNos);
				if (null == lr) {  
					 ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from LR table for "+lrNos, 500);
					 response = new ErrorResponse(errorMsg);
				}else {
					 LRChalan lrChalan = lr.getLrchalanId();
			    	 if(lrChalan != null) {
			    		 //Chalan should be updated
			    		 lrChalan = lrChalanService.updateLRChalan(lrNos,chalanDetails,lrChalan); 
			    		
			    	 }else{
			    		 //Chalan should be newly added
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
			}else {
				ErrorMessage errorMsg = new ErrorMessage("Issue In saving chalan details", 500);
	 			response = new ErrorResponse(errorMsg);
	 			return response;
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
        @FormParam( "billDetails" )     String billDetails,
        @FormParam( "transId" )           String transId)		
    {
		AppResponse response = null;
		LRBillService lrBillService  = new LRBillService();
		LrService lrService              = new LrService();
		
		
		//validate Input
		lrBillService.validateAuthData(lrNos);
		long ltransid = 0;
		try {	ltransid = Long.parseLong(transId); } 	catch (NumberFormatException ex) {	}	
		if (ltransid > 0) {
			System.out.println("multichalan");
			LRTransactionService transactionService = new LRTransactionService();
			LRTransaction lrTransaction = null;
			lrTransaction  = transactionService.findTransaction(transId);
			if (null == lrTransaction) {  
				 ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from Transaction table for "+lrNos, 500);
				 response = new ErrorResponse(errorMsg);
				 return response;
			}else {
				 LRBill lrBill = lrTransaction.getTransbillId();
		    	 if(lrBill != null) {
		    		 //Bill should be updated
		    		 lrBill = lrBillService.updateLRBill(lrNos,billDetails,lrBill); 
		    		
		    	 }else{
		    		 //Bill should be newly added
		    		 lrBill = lrBillService.newLRBill(lrNos,billDetails);
		    		
		    	 }
		    	 
		    	 if (null != lrBill ) {
		    		 lrTransaction = transactionService.updateBillToTransaction(lrBill,lrTransaction);
			 		if (null == lrTransaction) {
			 			ErrorMessage errorMsg = new ErrorMessage("Issue while updating Transaction with bill. Please try again", 500);
				 		response = new ErrorResponse(errorMsg);
				 		return response;
			 		} else {
			 			response = lrBillService.createLRBillResponse(lrBill);	
			 		}
		 			
		 		} else {
		 			ErrorMessage errorMsg = new ErrorMessage("Issue In saving bill details", 500);
		 			response = new ErrorResponse(errorMsg);
		 		}
				
			}
		}else {
			if(lrNos.indexOf(",")>-1) {
				ErrorMessage errorMsg = new ErrorMessage("Issue In saving bill details,Transaction Id is missing", 500);
	 			response = new ErrorResponse(errorMsg);
	 			return response;
			}
			long llrNo = 0;
			try {	llrNo = Long.parseLong(lrNos); } 	catch (NumberFormatException ex) {	}	
			if ( llrNo > 0) {
				LR lr = null;
				lr  = lrService.findLR(lrNos);
				if (null == lr) {  
					 ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from LR table for "+lrNos, 500);
					 response = new ErrorResponse(errorMsg);
				}else {
					 LRBill lrBill = lr.getLrbillId();
			    	 if(lrBill != null) {
			    		 //Bill should be updated
			    		 lrBill = lrBillService.updateLRBill(lrNos,billDetails,lrBill); 
			    		
			    	 }else{
			    		 //Bill should be newly added
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
			}else {
				ErrorMessage errorMsg = new ErrorMessage("Issue In saving bill details", 500);
	 			response = new ErrorResponse(errorMsg);
	 			return response;
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
	
	@POST
    @Path("/lr-service/edittransaction" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse addlrexpenditure(
        @Context HttpHeaders httpHeaders,       
        @FormParam( "id" 				 		) 	String transId,
        @FormParam( "multiLoadCharge" 	 		) 	String multiLoadCharge,
        @FormParam( "freightToBroker" 	 		) 	String freightToBroker,
        @FormParam( "extraPayToBroker" 	 		) 	String extraPayToBroker,
        @FormParam( "advance" 			 		)	String advance,
        @FormParam( "balanceFreight" 	 		) 	String balanceFreight,
        @FormParam( "loadingCharges" 	 		) 	String loadingCharges,
		@FormParam( "unloadingCharges" 	 		) 	String unloadingCharges,
		@FormParam( "loadingDetBroker" 	 		)	String loadingDetBroker,
		@FormParam( "unloadingDetBroker" 		)  	String unloadingDetBroker,
		@FormParam( "multiLoadChargeBilling" 	) 	String multiLoadChargeBilling,
        @FormParam( "freightToBrokerBilling" 	) 	String freightToBrokerBilling,
        @FormParam( "loadingChargesBilling"		)	String loadingChargesBilling,
        @FormParam( "unloadingChargesBilling" 	) 	String unloadingChargesBilling,
        @FormParam( "loadingDetBrokerBilling" 	) 	String loadingDetBrokerBilling,
        @FormParam( "unloadingDetBrokerBilling" ) 	String unloadingDetBrokerBilling,
        @FormParam( "billingnameId"             )   String billingnameId)		
    {
		AppResponse response = null;		

		int imultiLoadCharge = 0, ifreightToBroker = 0, iextraPayToBroker = 0, iadvance = 0, ibalanceFreight = 0;
		int iloadingCharges = 0, iloadingDetBroker = 0, iunloadingCharges = 0, iunloadingDetBroker = 0;
		int imultiLoadChargeBilling = 0, ifreightToBrokerBilling = 0, iloadingChargesBilling =0;
		int iunloadingChargesBilling = 0, iloadingDetBrokerBilling = 0, iunloadingDetBrokerBilling = 0;
		LrService lrService  = new LrService();
		Billingname billingname = null;
		
		
		
		try { imultiLoadCharge    = Integer.parseInt(multiLoadCharge);	  } catch (NumberFormatException ex) {	}
		try { ifreightToBroker    = Integer.parseInt(freightToBroker);	  } catch (NumberFormatException ex) {	}				
		try { iextraPayToBroker   = Integer.parseInt(extraPayToBroker);	  } catch (NumberFormatException ex) {	}
		try { iadvance 		      = Integer.parseInt(advance);			  } catch (NumberFormatException ex) {	}		
		try { ibalanceFreight     = Integer.parseInt(balanceFreight);	  } catch (NumberFormatException ex) {	}		
		try { iloadingCharges     = Integer.parseInt(loadingCharges);	  } catch (NumberFormatException ex) {	}
		try { iunloadingCharges   = Integer.parseInt(unloadingCharges);	  } catch (NumberFormatException ex) {	}
		try { iloadingDetBroker   = Integer.parseInt(loadingDetBroker);	  } catch (NumberFormatException ex) {	}			
		try { iunloadingDetBroker = Integer.parseInt(unloadingDetBroker); } catch (NumberFormatException ex) {	}
		
		try { imultiLoadChargeBilling    = Integer.parseInt(multiLoadChargeBilling);	} catch (NumberFormatException ex) {	}
		try { ifreightToBrokerBilling    = Integer.parseInt(freightToBrokerBilling);	} catch (NumberFormatException ex) {	}				
		try { iloadingChargesBilling   	 = Integer.parseInt(loadingChargesBilling);		} catch (NumberFormatException ex) {	}
		try { iunloadingChargesBilling 	 = Integer.parseInt(unloadingChargesBilling);	} catch (NumberFormatException ex) {	}		
		try { iloadingDetBrokerBilling   = Integer.parseInt(loadingDetBrokerBilling);	} catch (NumberFormatException ex) {	}		
		try { iunloadingDetBrokerBilling = Integer.parseInt(unloadingDetBrokerBilling); } catch (NumberFormatException ex) {	}
		
		//get partyname object
		if(billingnameId !=null && !billingnameId.equals("")){
			billingname  = lrService.getBillingname(billingnameId);
			if(null == billingname) {  
				ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from billingname table", 500);
				response = new ErrorResponse(errorMsg);
				return response;
		     }		      
		}
		
		
		LRTransactionService lrTransactionService = new LRTransactionService();
		LRTransaction tranasaction  = lrTransactionService.findTransaction(transId);

		if (null == tranasaction) {  
			 ErrorMessage errorMsg = new ErrorMessage("Tranasaction not found", 500);
			 response = new ErrorResponse(errorMsg);
			 return response;
		} 
		
		String status = tranasaction.getStatus();
		 
		LRTransaction         updatedTranasaction = null;
		updatedTranasaction = lrTransactionService.editTransaction(imultiLoadCharge, ifreightToBroker, iextraPayToBroker,
																   iadvance, ibalanceFreight, iloadingCharges,
															       iunloadingCharges, iloadingDetBroker, iunloadingDetBroker,
															       imultiLoadChargeBilling, ifreightToBrokerBilling,
															       iloadingChargesBilling, iunloadingChargesBilling,
															       iloadingDetBrokerBilling, iunloadingDetBrokerBilling,
															       billingname,status,
															       tranasaction);        
		if (updatedTranasaction != null) {
			response = lrTransactionService.createTransactionResponse(updatedTranasaction);			
		} else {
			ErrorMessage errorMsg = new ErrorMessage("Issue while editing Multi LR. Please try again", 500);
			response = new ErrorResponse(errorMsg);
		}   	 
		   		
		return response;
    }
	//Create LRTransOtherExpenditure	
	@POST
    @Path("/lr-service/addLRTransOtherExp" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse addLRTransOtherExp(
        @Context HttpHeaders httpHeaders,       
        @FormParam( "transId" 		) String transId,
        @FormParam( "amount" 	) String amount,
        @FormParam( "remarks" 	) String remarks)		
    {
		AppResponse response            = null;
		LRTransOtherExpService lrTransOtherExpService = new LRTransOtherExpService();
		LRTransactionService lrTransService             = new LRTransactionService();		

		long ltransId = 0;
		int iamount = 0;		
		try {	ltransId = Long.parseLong(transId);		} 	catch (NumberFormatException ex) {	}			
		try {	iamount = Integer.parseInt(amount);	} 	catch (NumberFormatException ex) {	}
		
		LRTransaction lrTransaction = null;
		if (ltransId > 0) { 
			lrTransaction  = lrTransService.findTransaction(transId);

			if (null == lrTransaction) {  
				 ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from LRTransaction table", 500);
				 response = new ErrorResponse(errorMsg);
		     } else {
		    	//Send to model using service              
		 		LRTransOtherExp lrTransOtherExp = lrTransOtherExpService.newLRTransOtherExp(ltransId,
		 														iamount,
		 														remarks);
		 								      
		 		if (lrTransOtherExp != null) {
		 			Set<LRTransOtherExp> lrTransOtherExps = lrTransaction.getLrtransotherExpenditures();
		 			
		 			if(null == lrTransOtherExps) {
		 				lrTransOtherExps = new HashSet();		 				
		 			}
		 			lrTransOtherExps.add(lrTransOtherExp);
		 			response = lrTransOtherExpService.createLRTransOtherExpResponse(lrTransOtherExps);			
		 		} else {
		 			ErrorMessage errorMsg = new ErrorMessage("Issue while creating the LRTransOtherExpenditure. Please try again", 500);
		 			response = new ErrorResponse(errorMsg);
		 		}
		     }
		}	
		
               		
		return response;
    }
	
	@POST
    @Path("/lr-service/removeLRTransOtherExp" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse removeLRTransOtherExp(
        @Context HttpHeaders httpHeaders,       
        @FormParam( "lrTransOtherExpenditureId" 		) String lrTransOtherExpenditureId,
        @FormParam( "transId" 		                    ) String transId)       		
    {
		AppResponse response            = null;
		LRTransOtherExpService lrTransOtherExpService = new LRTransOtherExpService();
		LRTransactionService lrTransService             = new LRTransactionService();	

		long llrOtherExpenditureId = 0;
		long ltransId                 = 0;
		try {	llrOtherExpenditureId = Long.parseLong(lrTransOtherExpenditureId);		} 	catch (NumberFormatException ex) {	}
		try {	ltransId                = Long.parseLong(transId);		                } 	catch (NumberFormatException ex) {	}	
				
		LRTransaction lrTransaction = null;
		if ( llrOtherExpenditureId > 0 && ltransId > 0 ) {		
				//Send to model using service              
				LRTransOtherExp lrTransOtherExp = lrTransOtherExpService.findLROtherExpenditure(llrOtherExpenditureId);
			 								      
				if ( lrTransOtherExp != null ) {
					if ( lrTransOtherExpService.removeLRTransOtherExpenditure(lrTransOtherExp) ) {
						lrTransaction  = lrTransService.findTransaction(transId);
						if ( null == lrTransaction ) {  
							ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from Transaction table", 500);
							response = new ErrorResponse(errorMsg);
						} else {
							Set<LRTransOtherExp> lrTransOtherExps = lrTransaction.getLrtransotherExpenditures();	 						 				
				 			response = lrTransOtherExpService.createLRTransOtherExpResponse(lrTransOtherExps);	
						}
			 				
			 		} else {
			 			ErrorMessage errorMsg = new ErrorMessage("Issue while Removing MultiLR OtherExpenditure. Please try again", 500);
			 			response = new ErrorResponse(errorMsg);
			 		}		
			 			
			 	} else {
			 		ErrorMessage errorMsg = new ErrorMessage("Issue while getting Multi LR OtherExpenditure. Please try again", 500);
			 		response = new ErrorResponse(errorMsg);
			 	}	
			
		} else {
	 		ErrorMessage errorMsg = new ErrorMessage("LrExpenditureID is wrong.", 500);
	 		response = new ErrorResponse(errorMsg);
	 	}	
		
		return response;
    }
	
	//Create LRTransOtherIncome	
	@POST
    @Path("/lr-service/addLRTransOtherIncome" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse addLRTransOtherIncome(
        @Context HttpHeaders httpHeaders,       
        @FormParam( "transId" 		) String transId,
        @FormParam( "amount" 	) String amount,
        @FormParam( "remarks" 	) String remarks)		
    {
		AppResponse response            = null;
		LRTransOtherIncomeService lrTransOtherIncomeService = new LRTransOtherIncomeService();
		LRTransactionService lrTransService             = new LRTransactionService();		

		long ltransId = 0;
		int iamount = 0;		
		try {	ltransId = Long.parseLong(transId);		} 	catch (NumberFormatException ex) {	}			
		try {	iamount = Integer.parseInt(amount);	} 	catch (NumberFormatException ex) {	}
		
		LRTransaction lrTransaction = null;
		if (ltransId > 0) { 
			lrTransaction  = lrTransService.findTransaction(transId);

			if (null == lrTransaction) {  
				 ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from LRTransaction Other Income table", 500);
				 response = new ErrorResponse(errorMsg);
		     } else {
		    	//Send to model using service              
		 		LRTransOtherIncome lrTransOtherIncome = lrTransOtherIncomeService.newLRTransOtherIncome(ltransId,
		 														iamount,
		 														remarks);
		 								      
		 		if (lrTransOtherIncome != null) {
		 			Set<LRTransOtherIncome> lrTransOtherIncomes = lrTransaction.getLrtransotherIncomes();	 			
		 			if(null == lrTransOtherIncomes) {
		 				lrTransOtherIncomes = new HashSet();		 				
		 			}
		 			lrTransOtherIncomes.add(lrTransOtherIncome);
		 			response = lrTransOtherIncomeService.createLRTransOtherIncomeResponse(lrTransOtherIncomes);			
		 		} else {
		 			ErrorMessage errorMsg = new ErrorMessage("Issue while creating the LRTransOtherIncome. Please try again", 500);
		 			response = new ErrorResponse(errorMsg);
		 		}
		     }
		}	
		
               		
		return response;
    }
	
	@POST
    @Path("/lr-service/removeLRTransOtherIncome" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse removeLRTransOtherIncome(
        @Context HttpHeaders httpHeaders,       
        @FormParam( "lrTransOtherIncomeId" 		        ) String lrTransOtherIncomeId,
        @FormParam( "transId" 		                    ) String transId)       		
    {
		AppResponse response            = null;
		LRTransOtherIncomeService lrTransOtherIncomeService = new LRTransOtherIncomeService();
		LRTransactionService lrTransService                 = new LRTransactionService();	

		long llrTransOtherIncomeId = 0;
		long ltransId                 = 0;
		try {	llrTransOtherIncomeId = Long.parseLong(lrTransOtherIncomeId);		    } 	catch (NumberFormatException ex) {	}
		try {	ltransId                = Long.parseLong(transId);		                } 	catch (NumberFormatException ex) {	}	
				
		LRTransaction lrTransaction = null;
		if ( llrTransOtherIncomeId > 0 && ltransId > 0 ) {		
				//Send to model using service              
				LRTransOtherIncome lrTransOtherIncome = lrTransOtherIncomeService.findLRTransOtherIncome(llrTransOtherIncomeId);
			 								      
				if ( lrTransOtherIncome != null ) {
					if ( lrTransOtherIncomeService.removeLRTransOtherIncome(lrTransOtherIncome) ) {
						lrTransaction  = lrTransService.findTransaction(transId);
						if ( null == lrTransaction ) {  
							ErrorMessage errorMsg = new ErrorMessage("Issue In getting record from Transaction table", 500);
							response = new ErrorResponse(errorMsg);
						} else {
							Set<LRTransOtherIncome> lrTransOtherIncomes = lrTransaction.getLrtransotherIncomes();	 						 				
				 			response = lrTransOtherIncomeService.createLRTransOtherIncomeResponse(lrTransOtherIncomes);	
						}
			 				
			 		} else {
			 			ErrorMessage errorMsg = new ErrorMessage("Issue while Removing MultiLR OtherIcome Please try again", 500);
			 			response = new ErrorResponse(errorMsg);
			 		}		
			 			
			 	} else {
			 		ErrorMessage errorMsg = new ErrorMessage("Issue while getting Multi LR OtherExpenditure. Please try again", 500);
			 		response = new ErrorResponse(errorMsg);
			 	}	
			
		} else {
	 		ErrorMessage errorMsg = new ErrorMessage("LrExpenditureID is wrong.", 500);
	 		response = new ErrorResponse(errorMsg);
	 	}	
		
		return response;
    }
	
	@POST
    @Path("/lr-service/listTransactions" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse lrTransactionList (
        @Context HttpHeaders httpHeaders,       
        @FormParam( "lrTransDate" ) String lrTransDate,        
        @FormParam( "status" )      String status)        
    {
		AppResponse response = null;
		LRTransactionService lrTransService                 = new LRTransactionService();              
              
  		List<LRTransaction> transList = lrTransService.listTranslr(lrTransDate,status);
  		System.out.println("list size "+transList.size());
    		
  		if (transList != null) {
  			response = lrTransService.createLRTransListResponse(transList,"");    					
    	} else {
    		ErrorMessage errorMsg = new ErrorMessage("Issue while getting Transaction List data. Please try again", 500);
    		response = new ErrorResponse(errorMsg);
    	}
  		
  		return response;
    }
	
	@POST
    @Path("/lr-service/updateStatusInLRTransList" )
    @Produces( MediaType.APPLICATION_JSON )
    public AppResponse updateStatusInLRTransList(
        @Context HttpHeaders httpHeaders, 
        @FormParam( "lrTransDate" ) String lrTransDate,        
        @FormParam( "status" ) String status,        
        @FormParam( "lrTransIds" ) String lrTransIds)
    {
		AppResponse response = null;
		LRTransactionService lrTransService  = new LRTransactionService();
		
		if(null == status || (null != status && status.equals(""))) {
			ErrorMessage errorMsg = new ErrorMessage("Invalid status", 500);
    		response = new ErrorResponse(errorMsg);
    		return response;
		}
		if(null == lrTransIds || (null != lrTransIds && lrTransIds.equals(""))) {
			ErrorMessage errorMsg = new ErrorMessage("No Transaction ids found for updating status. Please check", 500);
    		response = new ErrorResponse(errorMsg);
    		return response;
		}
		
		String[] transIds = lrTransIds.split(",");		
		List<String> errorTransIds =new ArrayList<String>();
		for (String transid : transIds) {			
			LRTransaction lrTrans = lrTransService.findTransaction(transid);
			if (null != lrTrans) {
				lrTrans = lrTransService.editTransaction(lrTrans.getMultiLoadCharge(), lrTrans.getFreightToBroker(), lrTrans.getExtraPayToBroker(),
														 lrTrans.getAdvance(), lrTrans.getBalanceFreight(), lrTrans.getLoadingCharges(),
														 lrTrans.getUnloadingCharges(), lrTrans.getLoadingDetBroker(), lrTrans.getUnloadingDetBroker(),
														 lrTrans.getMultiLoadChargeBilling(), lrTrans.getFreightToBrokerBilling(),
														 lrTrans.getLoadingChargesBilling(), lrTrans.getUnloadingChargesBilling(),
														 lrTrans.getLoadingDetBrokerBilling(), lrTrans.getUnloadingDetBrokerBilling(),
														 lrTrans.getBillingnameId(),status,lrTrans);   
			
				if (null == lrTrans) {
					ErrorMessage errorMsg = new ErrorMessage("Issue while updating the Transaction with status. Please try again", 500);
					response = new ErrorResponse(errorMsg);
					errorTransIds.add(transid);
				}
			} else {
				ErrorMessage errorMsg = new ErrorMessage("Not able to find transaction with id "+ transid, 500);
	    		response = new ErrorResponse(errorMsg);
	    		errorTransIds.add(transid);	    		
			}
		}		
		
		List<LRTransaction> lrTransList = lrTransService.listTranslr(lrTransDate, status);
  		
    		
  		if (lrTransList != null) {
  			String message = "";
  			if (errorTransIds.size() > 0) {
  				message = "Issue in updating status for transaction ids "+errorTransIds;
  			}else{
  				message = "All transactions are updated with status successfully";
  			}
  			response = lrTransService.createLRTransListResponse(lrTransList,message);    					
    	} else {
    		ErrorMessage errorMsg = new ErrorMessage("Issue while getting Transaction List data. Please try again", 500);
    		response = new ErrorResponse(errorMsg);
    	}
  		
  		
  		return response;
    }

}
