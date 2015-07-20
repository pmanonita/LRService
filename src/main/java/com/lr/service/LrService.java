package com.lr.service;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lr.db.HibernateSessionManager;
import com.lr.exceptions.AuthException;
import com.lr.exceptions.InsufficientDataException;
import com.lr.exceptions.SignupException;
import com.lr.model.LR;
import com.lr.model.User;
import com.lr.model.LR.Controller;
import com.lr.response.LRResponse;
import com.lr.response.LRView;
import com.lr.response.UserResponse;
import com.lr.response.UserView;

public class LrService {
	private final static int successCode = 1;
	//private final static int errorCode   = 0;
	
	//view level validation
	public void validateAuthData(String vehicleNo)
		throws InsufficientDataException
	{
		String errorMsg = "";
		if (null != vehicleNo && vehicleNo.equals("")) 
		{
			errorMsg = "Vehicle Number can't be null or empty";
			throw new InsufficientDataException(errorMsg);
		}		
	}
	
	private LR.DefaultController createControllerFromView(final String serviceKey,						  
			  											final String vehileNo,
			  											final String vehicleOwner,
			  											final String consignor,
			  											final String consignee,
			  											final String servTaxConsigner,
			  											final String servTaxConsignee,
			  											final String billingParty) 
	{
		
		return new LR.DefaultController() {
			
			@Override
			public int mTransid() {	return 0;	}
			
			@Override
			public String mVehicleNo() {	return vehileNo;	}
			
			@Override
			public String mConsignor() {	return consignor;	}
			
			@Override
			public String mConsignee() {	return consignee;	}
			
			@Override
			public String mConsignerServtax() {		return servTaxConsigner;	}
			
			@Override
			public String mConsigneeServtax() {		return servTaxConsignee;	}
			
			@Override
			public String mVehicleOwner() {		return vehicleOwner;	}
			
			@Override
			public String mBillingToParty() {	return billingParty;	}
			
			@Override
			public Date mLrDate() {		return new Date();	}
			
			@Override
			public String mMultiLoad() {	return null;	}
			
			@Override
			public String mUserid() {	return "";	}
			
			
		};
	}
	
	
	
	public LR newLR(final String serviceKey,						  
						  final String vehileNo,
						  final String vehicleOwner,
						  final String consignor,
						  final String consignee,
						  final String servTaxConsigner,
						  final String servTaxConsignee,
						  final String billingParty)
	{
		//validateAuthData(userName, password);
		
		//Get hibernate session manager
		Session session = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx  = null;
		LR lr       = null;
		
		try {
			
			tx = session.beginTransaction();
			
			//check if user already exists
			/*user = User.findByUserNameAndServiceKey(session, userName, serviceKey);
			if (user != null && user.mUserName().equalsIgnoreCase(userName)) {
				tx.rollback();
    			session.close();    			
    			throw new SignupException("Signup failure. User already exists");				
			}*/
			

			LR.Controller ctrl = createControllerFromView(serviceKey,
															vehileNo,
															vehicleOwner,
															consignor,
															consignee,
															servTaxConsigner,
															servTaxConsignee,
															billingParty);
						
			//Create user object using controller
			lr = new LR(ctrl);
			
			session.save(lr);			
			session.flush();
			
			tx.commit();		

		} catch(RuntimeException  ex) {
			lr = null;
			if (tx != null) 	{ tx.rollback(); }
			ex.printStackTrace();			
		} finally {
			session.close();
		}
		
		return lr;
	}
	
	
	
	

	

	public LRResponse createLRResponse(LR lr) 
	{
		
		/** User data visible to UI **/		
		LRView lrView = new LRView();
		lrView.setId(lr.getId());
		lrView.setVehicleNo(lr.getVehicleNo());
		lrView.setVehicleOwner(lr.getVehicleOwner());
		lrView.setConsignor(lr.getConsignor());
		lrView.setConsignee(lr.getConsignee());
		lrView.setServTaxConsigner(lr.getConsignerServtax());
		lrView.setServTaxConsignee(lr.getConsigneeServtax());
		lrView.setBillingParty(lr.getBillingToParty());		
		
		LRResponse response = new LRResponse(lrView);		
		
		return response;
	}
	
}
