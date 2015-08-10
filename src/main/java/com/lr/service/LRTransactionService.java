package com.lr.service;

import java.util.Date;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lr.db.HibernateSessionManager;
import com.lr.model.LR;
import com.lr.model.LRTransaction;
import com.lr.model.LRTransaction.Controller;
import com.lr.response.AppResponse;
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
			
			LRTransaction.Controller ctrl = createController(lrs,
															 status,
															 createDate,
															 multiLoadCharge,
															 freightToBroker,
															 extraPayToBroker,
															 advance,
															 balanceFreight,
															 loadingCharges,
															 unloadingCharges,
															 loadingDetBroker,
															 unloadingDetBroker,
															 multiLoadChargeBilling,
															 freightToBrokerBilling,
															 loadingChargesBilling,
															 unloadingChargesBilling,
															 loadingDetBrokerBilling,
															 unloadingDetBrokerBilling);

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


}
