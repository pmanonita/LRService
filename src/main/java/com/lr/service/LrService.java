package com.lr.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;












import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lr.db.HibernateSessionManager;
import com.lr.exceptions.AuthException;
import com.lr.exceptions.DataNotFoundException;
import com.lr.exceptions.InsufficientDataException;
import com.lr.model.Billingname;
import com.lr.model.Consignee;
import com.lr.model.Consigner;
import com.lr.model.Expense;
import com.lr.model.LR;
import com.lr.model.LRBill;
import com.lr.model.LRChalan;
import com.lr.model.LRExpenditure;
import com.lr.model.LRIncome;
import com.lr.model.LROtherIncome;
import com.lr.model.LROthers;
import com.lr.model.User;
import com.lr.response.AppResponse;
import com.lr.response.BillingnameListResponse;
import com.lr.response.BillingnameView;
import com.lr.response.ConsigneeListResponse;
import com.lr.response.ConsigneeView;
import com.lr.response.ConsignerListResponse;
import com.lr.response.ConsignerView;
import com.lr.response.LRExpeditureView;
import com.lr.response.LRIncomeView;
import com.lr.response.LRListResponse;
import com.lr.response.LRListView;
import com.lr.response.LROtherIncomeView;
import com.lr.response.LROthersView;
import com.lr.response.LRResponse;
import com.lr.response.LRSearchResponse;
import com.lr.response.LRView;


public class LrService {
	
	//view level validation
	public void validateAuthData(String lrNo)
		throws InsufficientDataException
	{
		String errorMsg = "";
		if (null != lrNo && lrNo.equals("")) 
		{
			errorMsg = "LR Number can't be null or empty";
			throw new InsufficientDataException(errorMsg);
		}		
	}
	
	private LR.DefaultController createControllerFromView(final String vehileNo,
			  											  final String vehicleOwner,
			  											  final Consigner consigner,
			  											  final Consignee consignee,			  											
			  											  final String billingParty,
			  											  final String poNo,
			  											  final String doNo,
			  											  final Billingname billingname,
			  											  final String status,
			  											  final String multiLoad,
			  											  final String userName)
	{
		
		return new LR.DefaultController() {
			public long mTransid() 						{	return 0;				}			
			public String mVehicleNo() 					{	return vehileNo;		}
			public Consigner mConsignerId() 			{	return consigner;		}
			public Consignee mConsigneeId() 			{	return consignee;		}
			public String mVehicleOwner() 				{	return vehicleOwner;	}
			public String mBillingToParty()	 			{	return billingParty;	}
			public Date mLrDate() 						{	return new Date();		}
			public String mMultiLoad() 					{	return multiLoad;		}
			public String mUserName() 					{ 	return userName;		}
			public LRExpenditure mLrexpenditureId()		{ 	return null; 			}
			public LRChalan mLrchalanId()				{ 	return null; 			}
			public LRBill mLrbillId()					{ 	return null; 			}
			public LRIncome mLrincomeId()				{ 	return null; 			}
			public Set<LROthers> mOtherExpenditures()	{ 	return null; 			}	
			public Set<LROtherIncome> mOtherIncomes()	{ 	return null; 			}	
			public String        mPONo() 	            {	return poNo;	        }
			public String        mDONo() 	            {	return doNo;            }
			public Billingname   mBillingname()         {	return billingname;     }
			public String        mStatus() 	            {	return status;            }
			
		};
	}

	private LR.Controller CreateContoller(final String vehileNo,
										  final String vehicleOwner,
										  final Consigner consigner,
										  final Consignee consignee,			  											
										  final String billingParty,
										  final String poNo,
										  final String doNo,										  
										  final Billingname billingname,
										  final String multiLoad,
										  final String userName,
										  final String status,
										  final Date lrDate,
										  final LRExpenditure lrExpenditure,
										  final LRChalan lrChalan,
										  final LRBill lrBill,
										  final LRIncome lrIncome,
										  final Set<LROthers> otherexpeditures,
										  final Set<LROtherIncome> otherincomes) 
	{
		return new LR.DefaultController() {		
			public long mTransid() 						{	return 0;				}
			public String mVehicleNo() 					{	return vehileNo;		}		
			public Consigner mConsignerId() 			{	return consigner;		}
			public Consignee mConsigneeId() 			{	return consignee;		}
			public String mVehicleOwner() 				{	return vehicleOwner;	}
			public String mBillingToParty() 			{	return billingParty;	}
			public Date mLrDate() 						{	return lrDate;	}
			public String mMultiLoad()					{	return multiLoad;		}
			public String mUserName() 					{	return userName;		}
			public LRExpenditure mLrexpenditureId()		{ 	return lrExpenditure; 	}
			public LRChalan mLrchalanId()				{ 	return lrChalan; 		}
			public LRBill mLrbillId()					{ 	return lrBill; 			}
			public LRIncome mLrincomeId()				{ 	return lrIncome; 		}
			public Set<LROthers> mOtherExpenditures()	{ 	return otherexpeditures;}
			public Set<LROtherIncome> mOtherIncomes()	{ 	return otherincomes; 			}
			public String        mPONo() 	            {	return poNo;	        }
			public String        mDONo() 	            {	return doNo;            }
			public Billingname   mBillingname()         {	return billingname;     }
			public String        mStatus() 	            {	return status;          }	
			
		};
		
	}

	//Create new LR
	public LR newLR(
					final String vehileNo,
					final String vehicleOwner,
					final Consigner consigner,
					final Consignee consignee,						  
					final String billingParty,
					final String poNo,
					final String doNo,
					final Billingname billingname,
					final String status,
					final String multiLoad,
					final String username)
	{		
		//Get hibernate session manager
		Session session = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx  = null;
		LR lr       = null;
		
		try {
			
			tx = session.beginTransaction();			
		

			LR.Controller ctrl = createControllerFromView(vehileNo,
														  vehicleOwner,
														  consigner,
														  consignee,															
														  billingParty,
														  poNo,
														  doNo,
														  billingname,
														  status,
														  multiLoad,
														  username);

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
	
	
	
	//Get LR from Id
	public LR findLR( String lrNo ) {

		if ( lrNo == null || lrNo.equals("") ) return null;
		
		Long lrId = null;		
		try {
			lrId = Long.parseLong(lrNo);
		}	catch (NumberFormatException ex) { 
			return null;
		}
		System.out.println("long is "+lrId);
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   = null;
		LR lr            = null;
	
		try {
			tx = session.beginTransaction();        	
			lr = LR.findLRById(session, lrId);
		
			if (null == lr) {
				tx.rollback();
				session.close();    			
				throw new AuthException("LR Not found"); 
			}		
			tx.commit();    		
		
		} catch (HibernateException e) {
			lr = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}	
		return lr;        
	}
	
	public List<LR> listlr( String strlrDate,  String strMultiLoad, String strStatus ) {
		
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   = null;
		List<LR> lrList            = null;
	
		try {
			tx = session.beginTransaction();
			
			Date lrDate           = null;
    		boolean usedateFilter = false;
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");		
    		if (null != strlrDate && !strlrDate.equals("") ) {
    			try {
    				lrDate = formatter.parse(strlrDate);
    				usedateFilter = true;
    			}
    			catch (ParseException e) {}
    		}
    		
    		boolean useMultiLoadFilter = false;
    		String multiLoad = "";
    		if (null != strMultiLoad && !strMultiLoad.equals("") ) {
    			multiLoad = strMultiLoad;
    			useMultiLoadFilter = true;
    		}
    		
    		boolean useStatusFilter = false;
    		String status = "";
    		if (null != strStatus && !strStatus.equals("") ) {
    			status = strStatus;
    			useStatusFilter = true;
    		}
    		
    		//Create Query
    		if (usedateFilter && useMultiLoadFilter && useStatusFilter) {
    			lrList = LR.findByDateMultiLoadStatus(session, lrDate, multiLoad, status);
    		} else if(usedateFilter && useMultiLoadFilter) {
    			lrList = LR.findByDateMultiLoad(session, lrDate, multiLoad);
    		} else if(usedateFilter && useStatusFilter) {
    			lrList = LR.findByDateStatus(session, lrDate, status);
    		} else if(usedateFilter) {
    			lrList = LR.findLRByDate(session, lrDate);
    		} else {
    			lrList = LR.findFirstFifty(session);
    		}			
			
    		
			if (null == lrList) {
				tx.rollback();
				session.close();
				System.err.println("ERROR ERROR : Not able to list lrs");
    			throw new DataNotFoundException("No lr found with given input filters" ); 
			}
			
			tx.commit();    		
		
		} catch (HibernateException e) {
			lrList = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}	
		return lrList;        
	}
	
	public LR updateExpenditureToLR(LRExpenditure lrExpenditure, LR lr) {
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   = null;
	
		try {
			tx = session.beginTransaction();  
			//Create Controller
			LR.Controller ctrl = CreateContoller(lr.getVehicleNo(),
												 lr.getVehicleOwner(),
												 lr.getConsignerId(),
												 lr.getConsigneeId(),
												 lr.getBillingToParty(),
												 lr.getPoNo(),
												 lr.getDoNo(),
												 lr.getBillingnameId(),												 
												 lr.getMultiLoad(),
												 lr.getUserName(),
												 lr.getStatus(),
												 lr.getLrDate(),
												 lrExpenditure,
												 lr.getLrchalanId(),
												 lr.getLrbillId(),
												 lr.getLrincomeId(),
												 lr.getOtherExpenditures(),
												 lr.getOtherIncomes());
								
			//Update Data
			lr.changeTo(ctrl);			
			
			session.saveOrUpdate(lr);			
			session.flush();
					
			tx.commit();    		
		
		} catch (HibernateException e) {
			lr = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}
	
		return lr; 
	}
	
	public LR updateChalanToLR(LRChalan lrChalan, LR lr) {
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   = null;
	
		try {
			tx = session.beginTransaction();  
			//Create Controller
			LR.Controller ctrl = CreateContoller(lr.getVehicleNo(),
												 lr.getVehicleOwner(),
												 lr.getConsignerId(),
												 lr.getConsigneeId(),
												 lr.getBillingToParty(),
												 lr.getPoNo(),
												 lr.getDoNo(),
												 lr.getBillingnameId(),												 
												 lr.getMultiLoad(),
												 lr.getUserName(),
												 lr.getStatus(),
												 lr.getLrDate(),
												 lr.getLrexpenditureId(),
												 lrChalan,
												 lr.getLrbillId(),
												 lr.getLrincomeId(),
												 lr.getOtherExpenditures(),
												 lr.getOtherIncomes());
								
			//Update Data
			lr.changeTo(ctrl);			
			
			session.saveOrUpdate(lr);			
			session.flush();
					
			tx.commit();    		
		
		} catch (HibernateException e) {
			lr = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}
	
		return lr; 
	}
	
	public LR updateBillToLR(LRBill lrBill, LR lr) {
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   = null;
	
		try {
			tx = session.beginTransaction();  
			//Create Controller
			LR.Controller ctrl = CreateContoller(lr.getVehicleNo(),
												 lr.getVehicleOwner(),
												 lr.getConsignerId(),
												 lr.getConsigneeId(),
												 lr.getBillingToParty(),
												 lr.getPoNo(),
												 lr.getDoNo(),
												 lr.getBillingnameId(),												 
												 lr.getMultiLoad(),
												 lr.getUserName(),
												 lr.getStatus(),
												 lr.getLrDate(),
												 lr.getLrexpenditureId(),
												 lr.getLrchalanId(),
												 lrBill,
												 lr.getLrincomeId(),
												 lr.getOtherExpenditures(),
												 lr.getOtherIncomes());
								
			//Update Data
			lr.changeTo(ctrl);			
			
			session.saveOrUpdate(lr);			
			session.flush();
					
			tx.commit();    		
		
		} catch (HibernateException e) {
			lr = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}
	
		return lr; 
	}
	
	public LR updateLR(final String vehileNo,
			  		   final String vehicleOwner,
			  		   final Consigner consigner,
			  		   final Consignee consignee,						  
			  		   final String billingParty,
			  		   final String poNo,
			  		   final String doNo,
			  		   final Billingname billingname,
			  		   final String multiLoad,
			  		   final String userName,			  		   
			  		   LR lr)
	{
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   = null;
		
	
		try {
			tx = session.beginTransaction();  			
			//Create Controller
			LR.Controller ctrl = CreateContoller(vehileNo,
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
												 lr.getLrDate(),
												lr.getLrexpenditureId(),
												lr.getLrchalanId(),
												lr.getLrbillId(),
												lr.getLrincomeId(),
												lr.getOtherExpenditures(),
												lr.getOtherIncomes());
								
			//Update Data
			lr.changeTo(ctrl);
			
			
			
			session.saveOrUpdate(lr);			
			session.flush();
					
			tx.commit();    		
		
		} catch (HibernateException e) {
			lr = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}
	
		return lr; 
	}
	
	public LR updateIncomeToLR(LRIncome lrIncome, LR lr) {
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   = null;
		
	
		try {
			tx = session.beginTransaction();  
			//Create Controller
			LR.Controller ctrl = CreateContoller(lr.getVehicleNo(),
												 lr.getVehicleOwner(),
												 lr.getConsignerId(),
												 lr.getConsigneeId(),
												 lr.getBillingToParty(),
												 lr.getPoNo(),
												 lr.getDoNo(),												 
												 lr.getBillingnameId(),
												 lr.getMultiLoad(),
												 lr.getUserName(),
												 lr.getStatus(),
												 lr.getLrDate(),
												 lr.getLrexpenditureId(),
												 lr.getLrchalanId(),
												 lr.getLrbillId(),
												 lrIncome,
												 lr.getOtherExpenditures(),
												 lr.getOtherIncomes());
								
			//Update Data
			lr.changeTo(ctrl);		
			
			session.saveOrUpdate(lr);			
			session.flush();
					
			tx.commit();    		
		
		} catch (HibernateException e) {
			lr = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}
	
		return lr; 
	}
	
	public LR updateOtherExpenditureToLR(LROthers lrOthers, LR lr) {
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   = null;
		
	
		try {
			tx = session.beginTransaction();  

			Set<LROthers> lrOtherExpeditures = null;
			if (lr.getOtherExpenditures() != null) {
				lrOtherExpeditures = lr.getOtherExpenditures();
			}
			
			lrOtherExpeditures.add(lrOthers);
			
			LR.Controller ctrl = CreateContoller(lr.getVehicleNo(),
												 lr.getVehicleOwner(),
												 lr.getConsignerId(),
												 lr.getConsigneeId(),
												 lr.getBillingToParty(),
												 lr.getPoNo(),
												 lr.getDoNo(),
												 lr.getBillingnameId(),
												 lr.getMultiLoad(),
												 lr.getUserName(),
												 lr.getStatus(),
												 lr.getLrDate(),
												 lr.getLrexpenditureId(),
												 lr.getLrchalanId(),
												 lr.getLrbillId(),
												 lr.getLrincomeId(),
												 lrOtherExpeditures,
												 lr.getOtherIncomes());
								
			//Update Data
			lr.changeTo(ctrl);			
			session.saveOrUpdate(lr);			
			session.flush();
					
			tx.commit();    		
		
		} catch (HibernateException e) {
			lr = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}
	
		return lr; 
	}
	
	
	public Consigner getConsigner( String consignerId ) {	
	
		Session session     = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx      = null;
		Consigner consigner = null;
		
		Integer iconsignerId = null;		
		try {
			iconsignerId = Integer.parseInt(consignerId);
		}	catch (NumberFormatException ex) { 
			return null;
		}
	
		try {
			tx = session.beginTransaction();        	
			consigner = Consigner.findConsignerById(session, iconsignerId);
		
		if (null == consigner) {
			tx.rollback();
			session.close();    			
			throw new AuthException("Consigner Not found"); 
		}	
		
		tx.commit();    		
		
		} catch (HibernateException e) {
			consigner = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}
	
		return consigner;        
	}
	
	public Consignee getConsignee( String consigneeId ) {	
	
		Session session     = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx      = null;
		Consignee consignee = null;
		
		Integer iconsigneeId = null;		
		try {
			iconsigneeId = Integer.parseInt(consigneeId);
		}	catch (NumberFormatException ex) { 
			return null;
		}
		
		try {
			tx = session.beginTransaction();        	
			consignee = Consignee.findConsigneeById(session, iconsigneeId);
		
		if (null == consignee) {
			tx.rollback();
			session.close();    			
			throw new DataNotFoundException("Consignee Not found");
		}
		
		tx.commit();    		
		
		} catch (HibernateException e) {
			consignee = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}
	
		return consignee;        
	}
	
	public Billingname getBillingname( String billingnameId ) {	
		
		Session session     = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx      = null;
		Billingname billingname = null;
		
		Integer ibillingnameId = null;		
		try {
			ibillingnameId = Integer.parseInt(billingnameId);
		}	catch (NumberFormatException ex) { 
			return null;
		}
		
		try {
			tx = session.beginTransaction();        	
			billingname = Billingname.findBillingnameById(session, ibillingnameId);
		
		if (null == billingname) {
			tx.rollback();
			session.close();    			
			throw new DataNotFoundException("Billingname Not found");
		}
		
		tx.commit();    		
		
		} catch (HibernateException e) {
			billingname = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}
	
		return billingname;        
	}
	
	//list all consigners
	public List<Consigner> listConsigner() {
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
    	Transaction tx   = null;
    	List<Consigner> consignerList = null;
    	try {
    		tx = session.beginTransaction();        	
    		consignerList = Consigner.findAllConsigners(session);    		
    		
    		if (null == consignerList) {
    			System.err.println("ERROR ERROR : Not able to list consigners");
    			throw new DataNotFoundException("Not able to list consigners" );
    		} 			
    		tx.commit();
    		
    	} catch (HibernateException e) {
    		consignerList = null;
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            
        } finally {
        	if (session.isOpen()) {
        		session.close();
        	} 
        }
		
		return consignerList;
	}
	
	//list all consigners
	public List<Consignee> listConsignee() {
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
    	Transaction tx   = null;
    	List<Consignee> consigneeList = null;
    	try {
    		tx = session.beginTransaction();        	
    		consigneeList = Consignee.findAllConsignees(session);
    		
    		if (null == consigneeList) {
    			System.err.println("ERROR ERROR : Not able to list consignere");
    			throw new DataNotFoundException("Not able to list consignere" );
    		} 			
    		tx.commit();
    		
    	} catch (HibernateException e) {
    		consigneeList = null;
            if (tx != null) tx.rollback();
            e.printStackTrace();
            
        } finally {
        	if (session.isOpen()) {
        		session.close();
        	} 
        }
		
		return consigneeList;
	}
	
	//list all billingnames
	public List<Billingname> listBillingname() {
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
    	Transaction tx   = null;
    	List<Billingname> billingnameList = null;
    	try {
    		tx = session.beginTransaction();        	
    		billingnameList = Billingname.findAllBillingname(session);    		
    		
    		if (null == billingnameList) {
    			System.err.println("ERROR ERROR : Not able to list billingnames");
    			throw new DataNotFoundException("Not able to list billingnames" );
    		} 			
    		tx.commit();
    		
    	} catch (HibernateException e) {
    		billingnameList = null;
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            
        } finally {
        	if (session.isOpen()) {
        		session.close();
        	} 
        }
		
		return billingnameList;
	}

	public LRResponse createLRResponse(LR lr) 
	{			
		LRView lrView = new LRView();
		lrView.setId(lr.getId());
		lrView.setTransid(lr.getTransid());
		lrView.setVehicleNo(lr.getVehicleNo());
		lrView.setVehicleOwner(lr.getVehicleOwner());
		lrView.setConsigner(lr.getConsignerId());
		lrView.setConsignee(lr.getConsigneeId());		
		lrView.setBillingParty(lr.getBillingToParty());		
		lrView.setPoNo(lr.getPoNo());
		lrView.setDoNo(lr.getDoNo());
		lrView.setBillingname(lr.getBillingnameId());	
		lrView.setStatus(lr.getStatus());
		lrView.setLrDate(lr.getLrDate().toString());
		lrView.setMultiLoad(lr.getMultiLoad());
		lrView.setUserName(lr.getUserName());
		
		
		LRResponse response = new LRResponse(lrView);	
		
		return response;
	}
	
	public ConsignerListResponse createListConsignerResponse(List<Consigner> consignerList) {				
		List<ConsignerView> lConsignerView  = new ArrayList<ConsignerView>();
		for (Consigner consigner : consignerList) {
			if (null != consigner) {
				ConsignerView consignerView = new ConsignerView();				
				consignerView.setId(consigner.getId());
				consignerView.setConsignerCode(consigner.getConsignerCode());
				consignerView.setConsignerName(consigner.getConsignerName());
				consignerView.setAddress(consigner.getAddress());
				consignerView.setServiceTax(consigner.getServiceTax());
				lConsignerView.add(consignerView);							
			}			
		}
		ConsignerListResponse response = new ConsignerListResponse(lConsignerView);
		return response;
	}
	
	public ConsigneeListResponse createListConsigneeResponse(List<Consignee> consigneeList) {				
		List<ConsigneeView> lConsigneeView  = new ArrayList<ConsigneeView>();
		for (Consignee consignee : consigneeList) {
			if (null != consignee) {
				ConsigneeView consigneeView = new ConsigneeView();				
				consigneeView.setId(consignee.getId());
				consigneeView.setConsigneeCode(consignee.getConsigneeCode());
				consigneeView.setConsigneeName(consignee.getConsigneeName());
				consigneeView.setAddress(consignee.getAddress());
				consigneeView.setServiceTax(consignee.getServiceTax());
				lConsigneeView.add(consigneeView);			
			}			
		}
		ConsigneeListResponse response = new ConsigneeListResponse(lConsigneeView);
		return response;
	}
	
	public BillingnameListResponse createListBillingnameResponse(List<Billingname> billingnameList) {				
		List<BillingnameView> lBillingnameiew  = new ArrayList<BillingnameView>();
		for (Billingname billingame : billingnameList) {
			if (null != billingame) {
				BillingnameView billingnameView = new BillingnameView();				
				billingnameView.setId(billingame.getId());
				billingnameView.setName(billingame.getName());
				billingnameView.setAddress(billingame.getAddress());				
				lBillingnameiew.add(billingnameView);			
			}			
		}
		BillingnameListResponse response = new BillingnameListResponse(lBillingnameiew);
		return response;
	}

	public AppResponse createLRSearchResponse(LR lr) {
		
		LRView lrView                      = null;
		LRExpeditureView lrExpenditureView = null;
		LROthersView lrOthersView          = null;
		LRIncomeView lrIncomeView		   = null;
		LROtherIncomeView lrOtherIncomeView =null ;

		//LR		
		if (lr != null) {
			lrView = new LRView();
			lrView.setId(lr.getId());
			lrView.setTransid(lr.getTransid());
			lrView.setVehicleNo(lr.getVehicleNo());
			lrView.setVehicleOwner(lr.getVehicleOwner());
			lrView.setBillingParty(lr.getBillingToParty());
			lrView.setLrDate(lr.getLrDate().toString());
			lrView.setPoNo(lr.getPoNo());
			lrView.setDoNo(lr.getDoNo());
			lrView.setBillingname(lr.getBillingnameId());
			lrView.setStatus(lr.getStatus());
			
			Consigner consigner = lr.getConsignerId();
			lrView.setConsigner(consigner);
			
			Consignee consignee = lr.getConsigneeId();
			lrView.setConsignee(consignee);		
			
		}
		
		//Exp
		LRExpenditure lrExpediture = lr.getLrexpenditureId();
		if (null != lrExpediture) {
			lrExpenditureView = new LRExpeditureView();
			lrExpenditureView.setFreightToBroker(lrExpediture.getFreightToBroker());
			lrExpenditureView.setExtraPayToBroker(lrExpediture.getExtraPayToBroker());
			lrExpenditureView.setAdvance(lrExpediture.getAdvance());
			lrExpenditureView.setBalanceFreight(lrExpediture.getBalanceFreight());
			lrExpenditureView.setLoadingCharges(lrExpediture.getLoadingCharges());
			lrExpenditureView.setUnloadingCharges(lrExpediture.getUnloadingCharges());	
			lrExpenditureView.setLoadingDetBroker(lrExpediture.getLoadingDetBroker());	
			lrExpenditureView.setUnloadingDetBroker(lrExpediture.getUnloadingDetBroker());
		}

		//Other Exp (to-do : rework, its not going to work in frontend)
		// use a list of others exp view
		Set<LROthers> lrOtherExps = lr.getOtherExpenditures();
		List<LROthersView> lrOthers = new ArrayList<LROthersView>();
		if (null != lrOtherExps && lrOtherExps.size() > 0) {
			for (LROthers lrOtherExp : lrOtherExps) {
				if(lrOtherExp != null) {
					lrOthersView = new LROthersView();
					lrOthersView.setId(lrOtherExp.getId());
					lrOthersView.setLrId(lrOtherExp.getLrId());
					lrOthersView.setAmount(lrOtherExp.getAmount());
					lrOthersView.setRemarks(lrOtherExp.getRemarks());
					lrOthers.add(lrOthersView);
				}
			}
		}
		
		//Get Income
		LRIncome lrIncome = lr.getLrincomeId();
		if (lrIncome != null) {
			lrIncomeView = new LRIncomeView();			
			lrIncomeView.setFreightToBroker(lrIncome.getFreightToBroker());
			lrIncomeView.setExtraPayToBroker(lrIncome.getExtraPayToBroker());		
			lrIncomeView.setLoadingCharges(lrIncome.getLoadingCharges());
			lrIncomeView.setUnloadingCharges(lrIncome.getUnloadingCharges());	
			lrIncomeView.setLoadingDetBroker(lrIncome.getLoadingDetBroker());	
			lrIncomeView.setUnloadingDetBroker(lrIncome.getUnloadingDetBroker());			
		}
		
		//Other Income (to-do : rework, its not going to work in frontend)
		// use a list of others income view
		Set<LROtherIncome> lrOtherIncomes = lr.getOtherIncomes();
		List<LROtherIncomeView> lrOtherIncomeViews = new ArrayList<LROtherIncomeView>();
		if (null != lrOtherIncomes && lrOtherIncomes.size() > 0) {
			for (LROtherIncome lrOtherIncome : lrOtherIncomes) {
				if(lrOtherIncome != null) {
					lrOtherIncomeView = new LROtherIncomeView();
					lrOtherIncomeView.setId(lrOtherIncome.getId());
					lrOtherIncomeView.setLrId(lrOtherIncome.getLrId());
					lrOtherIncomeView.setAmount(lrOtherIncome.getAmount());
					lrOtherIncomeView.setRemarks(lrOtherIncome.getRemarks());
					lrOtherIncomeViews.add(lrOtherIncomeView);
				}
			}
		}
		
		//Create Response (to-do: need to add lr exp others)
		LRSearchResponse response = new LRSearchResponse();
		if (lrView            != null )		{	response.setLr(lrView);							}
		if (lrExpenditureView != null )		{	response.setLrExpenditure(lrExpenditureView);	}
		if (lrIncomeView      != null )		{	response.setLrIncome(lrIncomeView);				}
		if (lrOthers      != null )			{	response.setLrOthers(lrOthers);				    }
		if (lrOtherIncomes      != null )	{	response.setLrOtherIncome(lrOtherIncomeViews);	}
		if (lrExpenditureView != null )		{	response.setLrExpenditure(lrExpenditureView);	}
		if (lr.getLrchalanId() != null )	{	response.setLrChalan(lr.getLrchalanId());		}
		if (lr.getLrbillId() != null )	    {	response.setLrBill(lr.getLrbillId());	        }
		
		return response;
	}
	
	public AppResponse createLRListResponse(List<LR> lrList) {
		
		LRListView lrListView     = null;
		LROthersView lrOthersView = null;
		List<LRListView> lrViews  = new ArrayList<LRListView>();
		

		//LR	
		if(lrList != null) {
			for (LR lr : lrList) {
				if(lr != null) {
					lrListView = new LRListView();
					lrListView.setId(lr.getId());
					lrListView.setVehicleNo(lr.getVehicleNo());
					lrListView.setVehicleOwner(lr.getVehicleOwner());
					lrListView.setBillingParty(lr.getBillingToParty());		
					lrListView.setPoNo(lr.getPoNo());
					lrListView.setDoNo(lr.getDoNo());
					lrListView.setBillingname(lr.getBillingnameId());
					lrListView.setStatus(lr.getStatus());
					
					
					Consigner consigner = lr.getConsignerId();
					lrListView.setConsigner(consigner);					
					Consignee consignee = lr.getConsigneeId();
					lrListView.setConsignee(consignee);
					
					//Exp
					LRExpenditure lrExpediture = lr.getLrexpenditureId();
					if (null != lrExpediture) {						
						lrListView.setFreightToBroker(lrExpediture.getFreightToBroker());
						lrListView.setExtraPayToBroker(lrExpediture.getExtraPayToBroker());
						lrListView.setAdvance(lrExpediture.getAdvance());
						lrListView.setBalanceFreight(lrExpediture.getBalanceFreight());
						lrListView.setLoadingCharges(lrExpediture.getLoadingCharges());
						lrListView.setUnloadingCharges(lrExpediture.getUnloadingCharges());	
						lrListView.setLoadingDetBroker(lrExpediture.getLoadingDetBroker());	
						lrListView.setUnloadingDetBroker(lrExpediture.getUnloadingDetBroker());
					}
					
					// use a list of others exp view
					Set<LROthers> lrOtherExps = lr.getOtherExpenditures();
					List<LROthersView> lrOthers = new ArrayList<LROthersView>();
					if (null != lrOtherExps && lrOtherExps.size() > 0) {
						for (LROthers lrOtherExp : lrOtherExps) {
							if(lrOtherExp != null) {
								lrOthersView = new LROthersView();
								lrOthersView.setId(lrOtherExp.getId());
								lrOthersView.setLrId(lrOtherExp.getLrId());
								lrOthersView.setAmount(lrOtherExp.getAmount());
								lrOthersView.setRemarks(lrOtherExp.getRemarks());
								lrOthers.add(lrOthersView);
							}
						}
						lrListView.setLrOthers(lrOthers);
					}
					
					//Get Income
					LRIncome lrIncome = lr.getLrincomeId();
					if (lrIncome != null) {								
						lrListView.setFreightToBrokerBilling(lrIncome.getFreightToBroker());
						lrListView.setExtraPayToBrokerBilling(lrIncome.getExtraPayToBroker());		
						lrListView.setLoadingChargesBilling(lrIncome.getLoadingCharges());
						lrListView.setUnloadingChargesBilling(lrIncome.getUnloadingCharges());	
						lrListView.setLoadingDetBrokerBilling(lrIncome.getLoadingDetBroker());	
						lrListView.setUnloadingDetBrokerBilling(lrIncome.getUnloadingDetBroker());			
					}
					
					
				}
				
				lrViews.add(lrListView);
			}
		}		
		
		//Create Response (to-do: need to add lr exp others)
		LRListResponse response = new LRListResponse();
		if (lrViews            != null )		{	response.setLrs(lrViews);						}		
		return response;
	}
	
}
