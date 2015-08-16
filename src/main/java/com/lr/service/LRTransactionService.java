package com.lr.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lr.db.HibernateSessionManager;
import com.lr.exceptions.AuthException;
import com.lr.exceptions.DataNotFoundException;
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
import com.lr.model.LRTransOtherExp;
import com.lr.model.LRTransOtherIncome;
import com.lr.model.LRTransaction;
import com.lr.model.LRTransaction.Controller;
import com.lr.response.AppResponse;
import com.lr.response.ErrorMessage;
import com.lr.response.ErrorResponse;
import com.lr.response.LRBillView;
import com.lr.response.LRChalanView;
import com.lr.response.LRListResponse;
import com.lr.response.LROtherIncomeView;
import com.lr.response.LROthersView;
import com.lr.response.LRResponse;
import com.lr.response.LRView;
import com.lr.response.lrtransaction.LRTransOtherExpView;
import com.lr.response.lrtransaction.LRTransOtherIncomeView;
import com.lr.response.lrtransaction.LRTransactioListResponse;
import com.lr.response.lrtransaction.LRTransactionListView;
import com.lr.response.lrtransaction.LRTransactionView;
import com.lr.response.lrtransaction.LRTrasactionResponse;

public class LRTransactionService {	
	
	
	private Controller createController(final Set<LR> lrs, 
										final String status, 
										final Date createDate,
										final int multiLoadCharge,
										final int freightToBroker,
										final int extraPayToBroker,
										final int advance,
										final int balanceFreight,
										final int loadingCharges,
										final int unloadingCharges,
										final int loadingDetBroker,
										final int unloadingDetBroker,
										final int multiLoadChargeBilling,
										final int freightToBrokerBilling,
										final int loadingChargesBilling,
										final int unloadingChargesBilling,
										final int loadingDetBrokerBilling,
										final int unloadingDetBrokerBilling,
										final LRChalan transChalanId,
										final LRBill transBillId,
										final Set<LRTransOtherExp> lrTransOtherExps,
										final Set<LRTransOtherIncome> lrTransOtherIncomes,
										final Billingname             billingnameId) 
	{
		return new LRTransaction.DefaultController() 					{
			public Set<LR> mLRs() 										{	return lrs;							}			
			public String mStatus() 									{	return status;						}			
			public Date mCreateDate() 									{	return createDate;					}			
			public int mMultiLoadCharge() 								{	return multiLoadCharge;				}			
			public int mFreightToBroker() 								{	return freightToBroker;				}			
			public int mExtraPayToBroker() 								{	return extraPayToBroker;			}			
			public int mAdvance() 										{	return advance;						}		
			public int mBalanceFreight() 								{	return balanceFreight;				}			
			public int mLoadingCharges() 								{	return loadingCharges;				}			
			public int mUnloadingCharges() 								{	return unloadingCharges;			}			
			public int mLoadingDetBroker() 								{	return loadingDetBroker;			}			
			public int mUnloadingDetBroker() 							{	return unloadingDetBroker;			}			
			public int mMultiLoadChargeBilling() 						{	return multiLoadChargeBilling;		}			
			public int mFreightToBrokerBilling() 						{	return freightToBrokerBilling;		}			
			public int mLoadingChargesBilling() 						{	return loadingChargesBilling;		}			
			public int mUnloadingChargesBilling() 						{	return unloadingChargesBilling;		}			
			public int mLoadingDetBrokerBilling() 						{	return loadingDetBrokerBilling;		}			
			public int mUnloadingDetBrokerBilling() 					{	return unloadingDetBrokerBilling;	}
			public LRChalan mTranschalanId()        					{	return transChalanId;	            }
			public LRBill mTransbillId()            					{	return transBillId;	                }
			public Set<LRTransOtherExp> mLRtransotherExpenditures()     {	return lrTransOtherExps;	        }
			public Set<LRTransOtherIncome> mLRtransotherIncomes()       {	return lrTransOtherIncomes;	        }
			public Billingname             mBillingname()               {   return billingnameId;               } 
		};
	}
	
	
	/**
	 *  
	 * Create LR Transaction (multi LR) 
	 * @param lrs
	 * @param status
	 * @return LRTransaction
	 * 
	 */

	public LRTransaction createLRTransaction(Set<LR> lrs, String status) {
		Session session             = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx              = null;
		LRTransaction lrTransaction = null;
		
		try {			
			tx = session.beginTransaction();	
			
			//Default daata
			Date createDate         		= new Date();
			int multiLoadCharge				= 0;
			int freightToBroker				= 0;
			int extraPayToBroker			= 0;
			int advance						= 0;
			int balanceFreight				= 0;
			int loadingCharges				= 0;
			int unloadingCharges			= 0;
			int loadingDetBroker			= 0;
			int unloadingDetBroker			= 0;
			int multiLoadChargeBilling		= 0;
			int freightToBrokerBilling		= 0;
			int loadingChargesBilling		= 0;
			int unloadingChargesBilling		= 0;
			int loadingDetBrokerBilling		= 0;
			int unloadingDetBrokerBilling	= 0;
			LRChalan transChalan            = null;
			LRBill transBill                = null;
			Set<LRTransOtherExp> lrTransOtherExp      = null;
			Set<LRTransOtherIncome> lrTransOtherIcome = null;
			Billingname             billingnameId     = null;      
			
			LRTransaction.Controller ctrl = createController(lrs, status, createDate, multiLoadCharge,
															 freightToBroker, extraPayToBroker, advance,
															 balanceFreight, loadingCharges, unloadingCharges,
															 loadingDetBroker, unloadingDetBroker, multiLoadChargeBilling,
															 freightToBrokerBilling, loadingChargesBilling, unloadingChargesBilling,
															 loadingDetBrokerBilling, unloadingDetBrokerBilling,
															 transChalan,transBill,lrTransOtherExp,lrTransOtherIcome,billingnameId);

			lrTransaction = new LRTransaction(ctrl);
			
			session.save(lrTransaction);			
			session.flush();
			
			tx.commit();		

		} catch(HibernateException  ex) {
			lrTransaction = null;
			if (tx != null) 	{ tx.rollback(); }
			ex.printStackTrace();			
		} finally {
			session.close();
		}
		
		return lrTransaction;
	}


	/**	 
	 * 
	 * @param trans
	 * @return
	 * 
	 */
	
	public LRTrasactionResponse createTransactionResponse(LRTransaction lrTransaction) 
	{		
		LRTrasactionResponse response = null;
		if (null != lrTransaction) {
			response = new LRTrasactionResponse();
			LRTransactionView lrTransactionView = new LRTransactionView();
			lrTransactionView.setId(lrTransaction.getId());		
			lrTransactionView.setMultiLoadCharge(lrTransaction.getMultiLoadCharge());
			lrTransactionView.setFreightToBroker(lrTransaction.getFreightToBroker());
			lrTransactionView.setExtraPayToBroker(lrTransaction.getExtraPayToBroker());
			lrTransactionView.setAdvance(lrTransaction.getAdvance());		
			lrTransactionView.setBalanceFreight(lrTransaction.getBalanceFreight());		
			lrTransactionView.setLoadingCharges(lrTransaction.getLoadingCharges());
			lrTransactionView.setUnloadingCharges(lrTransaction.getUnloadingCharges());
			lrTransactionView.setLoadingDetBroker(lrTransaction.getLoadingDetBroker());	
			lrTransactionView.setUnloadingDetBroker(lrTransaction.getUnloadingDetBroker());
			
			lrTransactionView.setMultiLoadChargeBilling(lrTransaction.getMultiLoadChargeBilling());
			lrTransactionView.setFreightToBrokerBilling(lrTransaction.getFreightToBrokerBilling());
			lrTransactionView.setLoadingChargesBilling(lrTransaction.getLoadingChargesBilling());
			lrTransactionView.setUnloadingChargesBilling(lrTransaction.getUnloadingChargesBilling());
			lrTransactionView.setLoadingDetBrokerBilling(lrTransaction.getLoadingDetBrokerBilling());
			lrTransactionView.setUnloadingDetBrokerBilling(lrTransaction.getUnloadingDetBrokerBilling());
			
			lrTransactionView.setStatus(lrTransaction.getStatus());
			lrTransactionView.setCreateDate(lrTransaction.getCreateDate());
			lrTransactionView.setBillingname(lrTransaction.getBillingnameId());
			
			String lrIds=getLRids(lrTransaction.getLrs());
			lrTransactionView.setLrs(lrIds);
			
			String poNos=getPoNos(lrTransaction.getLrs());
			lrTransactionView.setPoNos(poNos);
			
			String doNos=getDoNos(lrTransaction.getLrs());
			lrTransactionView.setDoNos(doNos);
			
			
			response.setTransaction(lrTransactionView);		
					
		}
		return response;
	}
	
	private String getLRids(Set<LR> lrs) {
		// TODO Auto-generated method stub
		String lrids = "";
		if (lrs != null && lrs.size() > 0) {
			Iterator<LR> lrIterator = lrs.iterator();
			while(lrIterator.hasNext()) {
				LR lr = lrIterator.next();
				lrids= lrids +","+lr.getId();
			}
		}
		if (lrids.startsWith(",")) {
			lrids =lrids.replaceFirst(",","");
		}
		return lrids;
	}
	
	private String getPoNos(Set<LR> lrs) {
		// TODO Auto-generated method stub
		String poNos= "";
		if (lrs != null && lrs.size() > 0) {
			Iterator<LR> lrIterator = lrs.iterator();
			while(lrIterator.hasNext()) {
				LR lr = lrIterator.next();
				if (lr.getPoNo() != null && lr.getPoNo().trim().length()>0) {
					poNos= poNos +","+lr.getPoNo();
				}				
			}
		}
		if (poNos.startsWith(",")) {
			poNos =poNos.replaceFirst(",","");
		}
		return poNos;
	}
	
	private String getDoNos(Set<LR> lrs) {
		// TODO Auto-generated method stub
		String doNos = "";
		if (lrs != null && lrs.size() > 0) {
			Iterator<LR> lrIterator = lrs.iterator();
			while(lrIterator.hasNext()) {
				LR lr = lrIterator.next();
				if (lr.getDoNo() != null && lr.getDoNo().trim().length()>0) {
					doNos= doNos +","+lr.getDoNo();
				}				
			}
		}
		if (doNos.startsWith(",")) {
			doNos = doNos.replaceFirst(",","");
		}
		return doNos;
	}


	/**
	 * 
	 * @param strTransactionId
	 * @return
	 */
	
	public LRTransaction findTransaction( String strTransactionId ) {

		if ( strTransactionId == null || strTransactionId.equals("") ) return null;
		
		Long lTransactionId = null;		
		try {
			lTransactionId = Long.parseLong(strTransactionId);
		}	catch (NumberFormatException ex) { 
			return null;
		}

		Session session  			= HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   			= null;
		LRTransaction lrTransaction = null;
	
		try {
			tx = session.beginTransaction();        	
			lrTransaction = LRTransaction.findById(session, lTransactionId);
		
			if (null == lrTransaction) {
				tx.rollback();
				session.close();    			
				throw new DataNotFoundException("Transaction Not found"); 
			}		
			tx.commit();    		
		
		} catch (HibernateException e) {
			lrTransaction = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}	
		return lrTransaction;        
	}


	public LRTransaction editTransaction(int multiLoadCharge,
			int freightToBroker, int extraPayToBroker, int advance,
			int balanceFreight, int loadingCharges, int unloadingCharges,
			int loadingDetBroker, int unloadingDetBroker,
			int multiLoadChargeBilling, int freightToBrokerBilling,
			int loadingChargesBilling, int unloadingChargesBilling,
			int loadingDetBrokerBilling, int unloadingDetBrokerBilling,
			Billingname billingnameId,
			LRTransaction tranasaction)
			
	{		
		Session session = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx 	= null;

				
		try {
			
			tx = session.beginTransaction();
			
			//Note - we haven't supported LR attach and detach yet. So can't edit them
			// Status can't be edited time being
			Set<LR> lrs     = tranasaction.getLrs();
			String status   = tranasaction.getStatus();
			Date createDate = tranasaction.getCreateDate();			
			
			LRTransaction.Controller ctrl = createController(lrs, status, createDate, multiLoadCharge,
															 freightToBroker, extraPayToBroker, advance,
															 balanceFreight, loadingCharges, unloadingCharges,
															 loadingDetBroker, unloadingDetBroker, multiLoadChargeBilling,
															 freightToBrokerBilling, loadingChargesBilling, unloadingChargesBilling,
															 loadingDetBrokerBilling, unloadingDetBrokerBilling,
															 tranasaction.getTranschalanId(),tranasaction.getTransbillId(),
															 tranasaction.getLrtransotherExpenditures(),tranasaction.getLrtransotherIncomes(),billingnameId);
			//Update Data
			tranasaction.changeTo(ctrl);			
			session.saveOrUpdate(tranasaction);			
			session.flush();    		
    		tx.commit();

		} catch(RuntimeException  ex) {
			tranasaction = null;
			if (tx != null) 	{ tx.rollback(); }
			ex.printStackTrace();			
		} finally {
			session.close();
		}

		return tranasaction;
	}	
	
	
	public LRTransaction updateChalanToTransaction(LRChalan lrChalan,LRTransaction tranasaction) {
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   = null;
	
		try {
			tx = session.beginTransaction();  
			//Create Controller
			LRTransaction.Controller ctrl = createController(tranasaction.getLrs(),
															 tranasaction.getStatus(),
															 tranasaction.getCreateDate(),
															 tranasaction.getMultiLoadCharge(),
															 tranasaction.getFreightToBroker(),
															 tranasaction.getExtraPayToBroker(),
															 tranasaction.getAdvance(),
															 tranasaction.getBalanceFreight(),
															 tranasaction.getLoadingCharges(),
															 tranasaction.getUnloadingCharges(),
															 tranasaction.getLoadingDetBroker(),
															 tranasaction.getUnloadingDetBroker(),
															 tranasaction.getMultiLoadChargeBilling(),
															 tranasaction.getFreightToBrokerBilling(),
															 tranasaction.getLoadingChargesBilling(), 
															 tranasaction.getUnloadingChargesBilling(),
															 tranasaction.getLoadingDetBrokerBilling(), 
															 tranasaction.getUnloadingDetBrokerBilling(),
															 lrChalan,
															 tranasaction.getTransbillId(),
															 tranasaction.getLrtransotherExpenditures(),
															 tranasaction.getLrtransotherIncomes(),
															 tranasaction.getBillingnameId());
								
			//Update Data
			tranasaction.changeTo(ctrl);			
			
			session.saveOrUpdate(tranasaction);			
			session.flush();
					
			tx.commit();    		
		
		} catch (HibernateException e) {
			tranasaction = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}
	
		return tranasaction; 
	}
	
	public LRTransaction updateBillToTransaction(LRBill lrBill,LRTransaction tranasaction) {
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   = null;
	
		try {
			tx = session.beginTransaction();  
			//Create Controller
			LRTransaction.Controller ctrl = createController(tranasaction.getLrs(),
															 tranasaction.getStatus(),
															 tranasaction.getCreateDate(),
															 tranasaction.getMultiLoadCharge(),
															 tranasaction.getFreightToBroker(),
															 tranasaction.getExtraPayToBroker(),
															 tranasaction.getAdvance(),
															 tranasaction.getBalanceFreight(),
															 tranasaction.getLoadingCharges(),
															 tranasaction.getUnloadingCharges(),
															 tranasaction.getLoadingDetBroker(),
															 tranasaction.getUnloadingDetBroker(),
															 tranasaction.getMultiLoadChargeBilling(),
															 tranasaction.getFreightToBrokerBilling(),
															 tranasaction.getLoadingChargesBilling(), 
															 tranasaction.getUnloadingChargesBilling(),
															 tranasaction.getLoadingDetBrokerBilling(), 
															 tranasaction.getUnloadingDetBrokerBilling(),
															 tranasaction.getTranschalanId(),
															 lrBill,
															 tranasaction.getLrtransotherExpenditures(),
															 tranasaction.getLrtransotherIncomes(),
															 tranasaction.getBillingnameId());
								
			//Update Data
			tranasaction.changeTo(ctrl);			
			
			session.saveOrUpdate(tranasaction);			
			session.flush();
					
			tx.commit();    		
		
		} catch (HibernateException e) {
			tranasaction = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}
	
		return tranasaction; 
	}
	
	public List<LRTransaction> listTranslr( String strTransDate, String strStatus ) {
		
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx   = null;
		List<LRTransaction> transList  = null;
	
		try {
			tx = session.beginTransaction();
			
			Date lrTransDate           = null;
    		boolean usedateFilter = false;
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");		
    		if (null != strTransDate && !strTransDate.equals("") ) {
    			try {
    				lrTransDate = formatter.parse(strTransDate);
    				usedateFilter = true;
    			}
    			catch (ParseException e) {}
    		}
    		
    		boolean useStatusFilter = false;
    		String status = "";
    		if (null != strStatus && !strStatus.equals("") ) {
    			status = strStatus;
    			useStatusFilter = true;
    		}
    		
    		//Create Query
    		if (usedateFilter && useStatusFilter) {
    			transList = LRTransaction.findByDateStatus(session, lrTransDate,status); 			
    		} else {//To do : Rare (Could be removed)
    			transList = LRTransaction.findLRTransByDate(session, lrTransDate);
    		}			
			
    		
			if (null == transList) {
				tx.rollback();
				session.close();
				System.err.println("ERROR ERROR : Not able to list transactions");
    			throw new DataNotFoundException("No transaction found with given input filters" ); 
			}
			
			tx.commit();    		
		
		} catch (HibernateException e) {
			transList = null;
	        if (tx != null) tx.rollback();
	        e.printStackTrace();
        
		} finally {
	    	if (session.isOpen()) {
	    		session.close();
	    	}
		}	
		return transList;        
	}
	
	public AppResponse createLRTransListResponse(List<LRTransaction> lrTransList,String message) {
		
		LRTransactionListView lrTransListView     = null;
		LRTransOtherExpView lrTransOtherExpView = null;
		List<LRTransactionListView> lrTransViews  = new ArrayList<LRTransactionListView>();
		LRTransOtherIncomeView lrTransOtherIncomeView = null;
		LRChalanView lrChalanView = null;
		LRBillView lrBillView = null;
		
		

		//LR	
		if(lrTransList != null) {
			for (LRTransaction lrTransaction : lrTransList) {
				if(lrTransaction != null) {
					lrTransListView = new LRTransactionListView();
					
					lrTransListView.setId(lrTransaction.getId());		
					lrTransListView.setMultiLoadCharge(lrTransaction.getMultiLoadCharge());
					lrTransListView.setFreightToBroker(lrTransaction.getFreightToBroker());
					lrTransListView.setExtraPayToBroker(lrTransaction.getExtraPayToBroker());
					lrTransListView.setAdvance(lrTransaction.getAdvance());		
					lrTransListView.setBalanceFreight(lrTransaction.getBalanceFreight());		
					lrTransListView.setLoadingCharges(lrTransaction.getLoadingCharges());
					lrTransListView.setUnloadingCharges(lrTransaction.getUnloadingCharges());
					lrTransListView.setLoadingDetBroker(lrTransaction.getLoadingDetBroker());	
					lrTransListView.setUnloadingDetBroker(lrTransaction.getUnloadingDetBroker());
					
					lrTransListView.setMultiLoadChargeBilling(lrTransaction.getMultiLoadChargeBilling());
					lrTransListView.setFreightToBrokerBilling(lrTransaction.getFreightToBrokerBilling());
					lrTransListView.setLoadingChargesBilling(lrTransaction.getLoadingChargesBilling());
					lrTransListView.setUnloadingChargesBilling(lrTransaction.getUnloadingChargesBilling());
					lrTransListView.setLoadingDetBrokerBilling(lrTransaction.getLoadingDetBrokerBilling());
					lrTransListView.setUnloadingDetBrokerBilling(lrTransaction.getUnloadingDetBrokerBilling());
					
					lrTransListView.setStatus(lrTransaction.getStatus());
					lrTransListView.setCreateDate(lrTransaction.getCreateDate());
					lrTransListView.setBillingname(lrTransaction.getBillingnameId());
					
					String lrIds=getLRids(lrTransaction.getLrs());
					lrTransListView.setLrs(lrIds);		
					
					String poNos=getPoNos(lrTransaction.getLrs());
					lrTransListView.setPoNos(poNos);
					
					String doNos=getDoNos(lrTransaction.getLrs());
					lrTransListView.setDoNos(doNos);
					
					// use a list of others exp view
					Set<LRTransOtherExp> lrTransOtherExps = lrTransaction.getLrtransotherExpenditures();
					List<LRTransOtherExpView> lrTransOtherExpViews = new ArrayList<LRTransOtherExpView>();
					if (null != lrTransOtherExps && lrTransOtherExps.size() > 0) {
						for (LRTransOtherExp lrTransOtherExp : lrTransOtherExps) {
							if(lrTransOtherExp != null) {
								lrTransOtherExpView = new LRTransOtherExpView();
								lrTransOtherExpView.setId(lrTransOtherExp.getId());
								lrTransOtherExpView.setTransid(lrTransOtherExp.getTransid());
								lrTransOtherExpView.setAmount(lrTransOtherExp.getAmount());
								lrTransOtherExpView.setRemarks(lrTransOtherExp.getRemarks());
								lrTransOtherExpViews.add(lrTransOtherExpView);
							}
						}
						lrTransListView.setLrTransOtherExp(lrTransOtherExpViews);
					}
					
					// use a list of otherincomes exp view
					Set<LRTransOtherIncome> lrTransOtherIncomes = lrTransaction.getLrtransotherIncomes();
					List<LRTransOtherIncomeView> lrTransOtherIncomeViews = new ArrayList<LRTransOtherIncomeView>();
					if (null != lrTransOtherIncomes && lrTransOtherIncomes.size() > 0) {
						for (LRTransOtherIncome lrTransOtherIncome : lrTransOtherIncomes) {
							if(lrTransOtherIncome != null) {
								lrTransOtherIncomeView = new LRTransOtherIncomeView();
								lrTransOtherIncomeView.setId(lrTransOtherIncome.getId());
								lrTransOtherIncomeView.setTransid(lrTransOtherIncome.getTransid());
								lrTransOtherIncomeView.setAmount(lrTransOtherIncome.getAmount());
								lrTransOtherIncomeView.setRemarks(lrTransOtherIncome.getRemarks());
								lrTransOtherIncomeViews.add(lrTransOtherIncomeView);
							}
						}
						lrTransListView.setLrTransOtherIncome(lrTransOtherIncomeViews);
					}
					
										
					//Get Chalan
					LRChalan lrChalan = lrTransaction.getTranschalanId();
					if (lrChalan != null) {		
						lrChalanView= new LRChalanView();
						lrChalanView.setId(lrChalan.getId());
						lrChalanView.setLrIds(lrChalan.getLrIds());
						lrChalanView.setChalanDetails(lrChalan.getChalanDetails());			
						lrChalanView.setTotalCost();
						lrTransListView.setChalan(lrChalanView);
							
					}
					
					//Get Bill
					LRBill lrBill = lrTransaction.getTransbillId();
					if (lrBill != null) {		
						lrBillView= new LRBillView();
						lrBillView.setId(lrBill.getId());
						lrBillView.setLrIds(lrBill.getLrIds());
						lrBillView.setBillDetails(lrBill.getBillDetails());			
						lrBillView.setTotalCost();
						lrTransListView.setBill(lrBillView);
							
					}
					
					
				}
				
				lrTransViews.add(lrTransListView);
			}
		}		
	

		LRTransactioListResponse response = new LRTransactioListResponse();
		if (lrTransViews            != null )		{	
			response.setLrTransactions(lrTransViews);
			response.setMessage(message);
		}		
		return response;
	}



}
