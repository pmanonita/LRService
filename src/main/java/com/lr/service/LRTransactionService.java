package com.lr.service;

import java.util.Date;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lr.db.HibernateSessionManager;
import com.lr.exceptions.AuthException;
import com.lr.exceptions.DataNotFoundException;
import com.lr.model.Expense;
import com.lr.model.LR;
import com.lr.model.LRTransaction;
import com.lr.model.LRTransaction.Controller;
import com.lr.response.AppResponse;
import com.lr.response.ErrorMessage;
import com.lr.response.ErrorResponse;
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
										final int unloadingDetBrokerBilling) 
	{
		return new LRTransaction.DefaultController() {
			public Set<LR> mLRs() 					{	return lrs;							}			
			public String mStatus() 				{	return status;						}			
			public Date mCreateDate() 				{	return createDate;					}			
			public int mMultiLoadCharge() 			{	return multiLoadCharge;				}			
			public int mFreightToBroker() 			{	return freightToBroker;				}			
			public int mExtraPayToBroker() 			{	return extraPayToBroker;			}			
			public int mAdvance() 					{	return advance;						}		
			public int mBalanceFreight() 			{	return balanceFreight;				}			
			public int mLoadingCharges() 			{	return loadingCharges;				}			
			public int mUnloadingCharges() 			{	return unloadingCharges;			}			
			public int mLoadingDetBroker() 			{	return loadingDetBroker;			}			
			public int mUnloadingDetBroker() 		{	return unloadingDetBroker;			}			
			public int mMultiLoadChargeBilling() 	{	return multiLoadChargeBilling;		}			
			public int mFreightToBrokerBilling() 	{	return freightToBrokerBilling;		}			
			public int mLoadingChargesBilling() 	{	return loadingChargesBilling;		}			
			public int mUnloadingChargesBilling() 	{	return unloadingChargesBilling;		}			
			public int mLoadingDetBrokerBilling() 	{	return loadingDetBrokerBilling;		}			
			public int mUnloadingDetBrokerBilling() {	return unloadingDetBrokerBilling;	}
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
			
			LRTransaction.Controller ctrl = createController(lrs, status, createDate, multiLoadCharge,
															 freightToBroker, extraPayToBroker, advance,
															 balanceFreight, loadingCharges, unloadingCharges,
															 loadingDetBroker, unloadingDetBroker, multiLoadChargeBilling,
															 freightToBrokerBilling, loadingChargesBilling, unloadingChargesBilling,
															 loadingDetBrokerBilling, unloadingDetBrokerBilling);

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

	public LRTrasactionResponse createTransactionResponse(LRTransaction trans) {
		LRTrasactionResponse response = null;
		if (null != trans) {
			response = new LRTrasactionResponse();
			response.setTransaction(trans);			
		}
		return response;
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
															 loadingDetBrokerBilling, unloadingDetBrokerBilling);

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



}
