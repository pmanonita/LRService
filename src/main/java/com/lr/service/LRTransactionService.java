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
	
	
	private Controller createControllerFromViewlrs(final Set<LR> lrs, final String status) {
		return new LRTransaction.DefaultController() {
			public Set<LR> mLRs() 		{	return lrs;			}			
			public String mStatus() 	{	return status;		}			
			public Date mCreateDate() 	{	return new Date();	}
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

			LRTransaction.Controller ctrl = createControllerFromViewlrs(lrs, status);

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
