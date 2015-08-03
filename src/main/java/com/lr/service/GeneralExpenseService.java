package com.lr.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lr.db.HibernateSessionManager;
import com.lr.exceptions.DataNotFoundException;
import com.lr.exceptions.InsufficientDataException;
import com.lr.model.AccountSource;
import com.lr.model.Expense;
import com.lr.model.User;
import com.lr.response.AppResponse;
import com.lr.response.UserListResponse;
import com.lr.response.UserView;
import com.lr.response.generalexpense.ExpenseListResponse;
import com.lr.response.generalexpense.ExpenseResponse;
import com.lr.response.generalexpense.ExpenseView;

public class GeneralExpenseService {	
	
	//view level validation
	public void validateData(String amount, String accountId, String date)
		throws InsufficientDataException
	{
		String errorMsg = "";
		if (null == amount ||(null != amount && amount.equals(""))) {
			errorMsg = "Amount can't empty";
			throw new InsufficientDataException(errorMsg);
		}
		
		if (null == accountId ||(null != accountId && accountId.equals(""))) {
			errorMsg = "Account name can't be empty";
			throw new InsufficientDataException(errorMsg);
		}
		
		if (null == date ||(null != date && date.equals(""))) {
			errorMsg = "Date can't be empty";
			throw new InsufficientDataException(errorMsg);
		}
	}
	
	
	private Expense.DefaultController createControllerFromView(final int           amount, 
															   final AccountSource accountSource,
															   final Date   	   date, 
															   final String        status,
															   final String        remark) 
	{
		//Create controller object and populate data
		return new Expense.DefaultController() {
			
			public int    			mAmount()			{ return amount;	}
			public AccountSource 	mAccountSource() 	{ return accountSource;}
			public Date   			mDate() 			{ return date;		}
			public String 			mStatus() 			{ return status;	}
			public String 			mRemark() 			{ return remark;	}						
		};
			
	}
	
	//Create new expense
	public Expense createExpense(final String strAmount, 						  
								 final AccountSource accountSource,
								 final String strDate,
								 final String strStatus,								 						  
								 final String strRemark)
	{		
		int iAmount = 0;
		try {
			iAmount = Integer.parseInt(strAmount);} 
		catch (NumberFormatException ex) {
			return null;
		}
		
		if (null == accountSource) {
			return null;
		}
		
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = formatter.parse(strDate);			
		} catch (ParseException e) {
			System.out.println("ERROR ERROR : Not able to convert to date object from date string");
			return null;
		}
	
		//Get hibernate session manager
		Session session = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx  = null;
		Expense expense = null;
				
		try {
			
			tx = session.beginTransaction();			

			Expense.Controller ctrl = createControllerFromView(iAmount, accountSource, date,
															   strStatus, strRemark);

			expense = new Expense(ctrl);

			session.save(expense);
			session.flush();
			
			tx.commit();

		} catch(RuntimeException  ex) {
			expense = null;
			if (tx != null) 	{ tx.rollback(); }
			ex.printStackTrace();			
		} finally {
			session.close();
		}
		
		return expense;
	}
	
	public Expense editExpense(String strExpenseId, String strAmount, AccountSource accountSource,
			String strDate, String strStatus, String strRemark)
	{
		if ( strExpenseId == null || strExpenseId.equals("") ) return null;
		
		Long id = null;
		try {
			id = Long.parseLong(strExpenseId);} 
		catch (NumberFormatException ex) {
			return null;
		}
		
		int iAmount = 0;
		try {
			iAmount = Integer.parseInt(strAmount);} 
		catch (NumberFormatException ex) {
			return null;
		}
		
		if (null == accountSource) {
			return null;
		}
		
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = formatter.parse(strDate);			
		} catch (ParseException e) {
			System.out.println("ERROR ERROR : Not able to convert to date object from date string");
			return null;
		}
	
		//Get hibernate session manager
		Session session = HibernateSessionManager.getSessionFactory().openSession();
		Transaction tx  = null;
		Expense expense = null;
				
		try {
			
			tx = session.beginTransaction();
			
			expense = Expense.findById(session, id);    		
    		if (null == expense) {
    			tx.rollback();
    			session.close();
    			System.out.println("ERROR ERROR : Expense not found");
    			throw new DataNotFoundException("Expense not found.");
    		}			

			Expense.Controller ctrl = createControllerFromView(iAmount, accountSource, date,
															   strStatus, strRemark);

			//Update Data
			expense.changeTo(ctrl);			
			session.saveOrUpdate(expense);			
			session.flush();    		
    		tx.commit();

		} catch(RuntimeException  ex) {
			expense = null;
			if (tx != null) 	{ tx.rollback(); }
			ex.printStackTrace();			
		} finally {
			session.close();
		}
		
		return expense;

	}
	
	public ExpenseResponse createExpenseResponse(Expense expense) 
	{			
		ExpenseView expenseView = new ExpenseView();
		expenseView.setId(expense.getId());
		expenseView.setAmount(expense.getAmount());
		expenseView.setAccount(expense.getAccountSource());
		expenseView.setDate(expense.getDate());
		expenseView.setStatus(expense.getStatus());
		expenseView.setRemark(expense.getRemark());		
		
		ExpenseResponse response = new ExpenseResponse(expenseView);		
		
		return response;
	}


	public List<Expense> listExpense(String accountId, String fromDate,
									 String toDate, String status)
	{
		Session session  = HibernateSessionManager.getSessionFactory().openSession();
    	Transaction tx   = null;
		List<Expense> expenseList = null;
		
		try {
    		tx = session.beginTransaction();
    	   	
    		Integer iAccountId    = null;
    		boolean useAcctFilter = false;
    		if (null != accountId && !accountId.equals("") ) {
    			try {	
    				iAccountId = Integer.parseInt(accountId);
    				useAcctFilter = true;
    			}
    			catch (NumberFormatException ex) {}
    		}
    		
    		Date dFromDate            = null;
    		Date dToDate              = null;
    		boolean useFromDateFilter = false;
    		boolean useToDateFilter   = false;
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");		
    		if (null != fromDate && !fromDate.equals("") ) {
    			try {
    					dFromDate = formatter.parse(fromDate);
    					useFromDateFilter = true;
    			}
    			catch (ParseException e) {}
    		}
    		if (null != toDate && !toDate.equals("") ) {
    			try {
    					dToDate = formatter.parse(toDate);
    					useToDateFilter = true;
    				}
    			catch (ParseException e) {}
    		}
    		
    		boolean useStatusFilter = false;
    		String strStatus = "";
    		if (null != status && !status.equals("") ) {
    			strStatus = status;
    			useStatusFilter = true;
    		}
    		
    		//Create Query
    		if (useAcctFilter && useFromDateFilter
    				&& useToDateFilter && useStatusFilter) {
    			expenseList = Expense.findByAcctDateStatus(session, iAccountId, dFromDate, dToDate, strStatus);
    		} else if(useAcctFilter && useFromDateFilter && useToDateFilter) {
    			expenseList = Expense.findByAcctNDate(session, iAccountId, dFromDate, dToDate);
    		} else if(useFromDateFilter && useToDateFilter && useStatusFilter) {
    			expenseList = Expense.findByStatusNDate(session, dFromDate, dToDate, strStatus);
    		} else if(useFromDateFilter && useToDateFilter) {
    			expenseList = Expense.findByDate(session, dFromDate, dToDate);
    		} else {
    			expenseList = Expense.findFirstFifty(session);
    		}   		
    		
    		tx.commit();
    		
    		if (null == expenseList) {
    			System.err.println("ERROR ERROR : Not able to list expenses");
    			throw new DataNotFoundException("No expenses found with given input filters" );
    		}
    		
    	} catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            
        } finally {
        	if (session.isOpen()) {
        		session.close();
        	} 
        }
				
		
		return expenseList;
	}


	public AppResponse createListExpenseResponse(List<Expense> expenses) {
		List<ExpenseView> lExpenseView  = new ArrayList<ExpenseView>();
		for (Expense expense : expenses) {
			if (null != expense) {
				ExpenseView expenseView = new ExpenseView();
				expenseView.setId(expense.getId());
				expenseView.setAmount(expense.getAmount());
				expenseView.setAccount(expense.getAccountSource());
				expenseView.setDate(expense.getDate());
				expenseView.setStatus(expense.getStatus());
				expenseView.setRemark(expense.getRemark());
				
				lExpenseView.add(expenseView);
			}			
		}
		ExpenseListResponse response = new ExpenseListResponse(lExpenseView);
		return response;
	}			
}
