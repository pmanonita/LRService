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

import com.lr.model.LRExpenditure;

import com.lr.response.AppResponse;
import com.lr.response.ErrorMessage;
import com.lr.response.ErrorResponse;
import com.lr.service.LRExpenditureService;




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
        @FormParam( "freightToBroker" ) String freightToBroker,
        @FormParam( "extraPayToBroker" ) String extraPayToBroker,
        @FormParam( "advance" ) String advance,
        @FormParam( "balanceFreight" ) String balanceFreight,
        @FormParam( "loadingCharges" ) String loadingCharges,
		@FormParam( "unloadingCharges" ) String unloadingCharges,
		@FormParam( "loadingDetBroker" ) String loadingDetBroker,
		@FormParam( "unloadingDetBroker" ) String unloadingDetBroker)		
    {
		AppResponse response    = null;
		LRExpenditureService lrExpenditureService = new LRExpenditureService();
		
		String serviceKey = httpHeaders.getHeaderString(LRHTTPHeaders.SERVICE_KEY);
		
		//validate Input
		lrExpenditureService.validateAuthData(lrNo);	
		
		//Convert View To Model object if any 
		long llrNo = 0;
		try {
			llrNo = Long.parseLong(lrNo);
		} catch (NumberFormatException ex) {					
			//Suppress the warning
		}
		
		int iferightToBroker = 0;
		try {
			iferightToBroker = Integer.parseInt(freightToBroker);
		} catch (NumberFormatException ex) {					
			//Suppress the warning
		}
		
		int iextraPayToBroker = 0;
		try {
			iextraPayToBroker = Integer.parseInt(extraPayToBroker);
		} catch (NumberFormatException ex) {					
			//Suppress the warning
		}
		
		
		
		int iadvance = 0;
		try {
			iadvance = Integer.parseInt(advance);
		} catch (NumberFormatException ex) {					
			//Suppress the warning
		}
		
		int ibalanceFreight = 0;
		try {
			ibalanceFreight = Integer.parseInt(balanceFreight);
		} catch (NumberFormatException ex) {					
			//Suppress the warning
		}
		
		int iloadingCharges = 0;
		try {
			iloadingCharges = Integer.parseInt(loadingCharges);
		} catch (NumberFormatException ex) {					
			//Suppress the warning
		}
		
		int iunloadingCharges = 0;
		try {
			iunloadingCharges = Integer.parseInt(unloadingCharges);
		} catch (NumberFormatException ex) {					
			//Suppress the warning
		}
		
		int iloadingDetBroker = 0;
		try {
			iloadingDetBroker = Integer.parseInt(loadingDetBroker);
		} catch (NumberFormatException ex) {					
			//Suppress the warning
		}
		
		int iunloadingDetBroker = 0;
		try {
			iunloadingDetBroker = Integer.parseInt(unloadingDetBroker);
		} catch (NumberFormatException ex) {					
			//Suppress the warning
		}
		
				
		
		//Send to model using service              
		LRExpenditure lrExpenditure = lrExpenditureService.newLRExpenditure(llrNo,
								iferightToBroker,
								iextraPayToBroker,
								iadvance,
								ibalanceFreight,
								iloadingCharges,
								iunloadingCharges,
								iloadingDetBroker,
								iunloadingDetBroker
								);        
		if (lrExpenditure != null) {
			response = lrExpenditureService.createLRExpenditureResponse(lrExpenditure);			
		} else {
			ErrorMessage errorMsg = new ErrorMessage("Issue while creating the lr. Please try again", 500);
			response = new ErrorResponse(errorMsg);
		}
               		
		return response;
    }
	

}
