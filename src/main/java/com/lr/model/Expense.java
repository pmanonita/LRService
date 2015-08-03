package com.lr.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class Expense implements Serializable {	

	private static final long serialVersionUID = -4936493255620571406L;
	
	private Long           _id;
	private int           _amount;
	private AccountSource _accountSource;
	private Date   		  _date;	
	private String 		  _status;
	private String 		  _remark;


	public Expense() {	}
	
	public Expense (Controller ctrl) {
		createFrom(ctrl);		
	}
	
	/*
	private void validate(Controller ctrl) throws InsufficientDataException {
		//Model level validation			
	}*/
	
	private void createFrom(Controller ctrl) {		
		//validate(ctrl);
		
		_amount 		= ctrl.mAmount(); 
		_accountSource  = ctrl.mAccountSource();
		_date   		= ctrl.mDate();	
		_status 		= ctrl.mStatus();
		_remark 		= ctrl.mRemark();		
	}
	
	public void changeTo(Controller ctrl) {		
		//validate(ctrl);
		
		_amount 		= ctrl.mAmount(); 
		_accountSource  = ctrl.mAccountSource();
		_date   		= ctrl.mDate();	
		_status 		= ctrl.mStatus();
		_remark 		= ctrl.mRemark();		
	}
	
	public void populate(Controller ctrl) {
		ctrl.mAmount(_amount);
		ctrl.mAccountSource(_accountSource);
		ctrl.mDate(_date);
		ctrl.mStatus(_status);
		ctrl.mRemark(_remark);
	}

	public interface Controller {
		int mAmount();
		void mAmount(int amount);
		
		AccountSource mAccountSource();
		void mAccountSource(AccountSource actSource);
		
		Date mDate();
		void mDate(Date date);
		
		String mStatus();
		void mStatus(String status);
		
		String mRemark();
		void mRemark(String remark);		
	}
	
	public abstract static class DefaultController implements Controller {
		public void mAmount(int amount) 						{	}
		public void mAccountSource(AccountSource accountSource)	{	}
		public void mDate(Date date)							{	}
		public void mStatus(String status) 						{	}
		public void mRemark(String remark)						{	}			
	}

	//getter and setter
	public Long getId() {
		return _id;
	}
	protected void setId(Long id) {
		this._id = id;
	}

	public int getAmount() {
		return _amount;
	}
	public void setAmount(int amount) {
		this._amount = amount;
	}

	public AccountSource getAccountSource() {
		return _accountSource;
	}

	public void setAccountSource(AccountSource accountSource) {
		this._accountSource = accountSource;
	}

	public Date getDate() {
		return _date;
	}
	public void setDate(Date date) {
		this._date = date;
	}

	public String getStatus() {
		return _status;
	}
	public void setStatus(String _status) {
		this._status = _status;
	}

	public String getRemark() {
		return _remark;
	}
	public void setRemark(String _remark) {
		this._remark = _remark;
	}

	public static long getSerialversionuid()	{ return serialVersionUID;	}
	
	
	
	private static final String QUERY_FOR_EXPENSE_BY_ID =
    		Expense.class.getName() + ".findById";
	public static Expense findById(Session session, Long id)
		throws HibernateException
	{
		if (id == null) {
			return null;
		}
		
		Query qry = session.getNamedQuery(QUERY_FOR_EXPENSE_BY_ID);
    	qry.setLong("id", id);
    	
        qry.setMaxResults(1);
        
        final Expense expense = (Expense)(qry.uniqueResult());
    	return expense;	
	}
	
	private static final String QUERY_FOR_EXPENSE_BY_ACCT_DATE_STATUS =
    		Expense.class.getName() + ".findByAcctDateStatus";
	public static List<Expense> findByAcctDateStatus(Session session, Integer iAccountId,
			Date dFromDate, Date dToDate, String strStatus) {
		
		Query qry = session.getNamedQuery(QUERY_FOR_EXPENSE_BY_ACCT_DATE_STATUS);
		qry.setInteger("accountSourceId", iAccountId);
		qry.setDate("fromDate", dFromDate);
		qry.setDate("toDate", dToDate);
		qry.setString("status", strStatus);
        
        @SuppressWarnings("unchecked")
		final List<Expense> expenselist = qry.list();
    	return expenselist;
	}

	private static final String QUERY_FOR_EXPENSE_BY_ACCT_DATE =
    		Expense.class.getName() + ".findByAcctNDate";
	public static List<Expense> findByAcctNDate(Session session, Integer iAccountId,
			Date dFromDate, Date dToDate) {

		Query qry = session.getNamedQuery(QUERY_FOR_EXPENSE_BY_ACCT_DATE);	
        
		qry.setInteger("accountSourceId", iAccountId);
		qry.setDate("fromDate", dFromDate);
		qry.setDate("toDate", dToDate);
		
        @SuppressWarnings("unchecked")
		final List<Expense> expenselist = qry.list();
    	return expenselist;
	}

	private static final String QUERY_FOR_EXPENSE_BY_STATUS_DATE =
    		Expense.class.getName() + ".findByStatusNDate";
	public static List<Expense> findByStatusNDate(Session session, Date dFromDate, Date dToDate,
			String strStatus) {

		Query qry = session.getNamedQuery(QUERY_FOR_EXPENSE_BY_STATUS_DATE);	
        
		qry.setDate("fromDate", dFromDate);
		qry.setDate("toDate", dToDate);
		qry.setString("status", strStatus);
		
        @SuppressWarnings("unchecked")
		final List<Expense> expenselist = qry.list();
    	return expenselist;
	}
	
	private static final String QUERY_FOR_EXPENSE_BY_DATE =
    		Expense.class.getName() + ".findByDate";
	public static List<Expense> findByDate(Session session, Date dFromDate,
			Date dToDate) {
		
		Query qry = session.getNamedQuery(QUERY_FOR_EXPENSE_BY_DATE);	
        
		qry.setDate("fromDate", dFromDate);
		qry.setDate("toDate", dToDate);		
		
        @SuppressWarnings("unchecked")
		final List<Expense> expenselist = qry.list();
    	return expenselist;
	}

	private static final String QUERY_FOR_EXPENSE_LIMIT_50 =
    		Expense.class.getName() + ".findFirstFifty";
	public static List<Expense> findFirstFifty(Session session) {
		// TODO Auto-generated method stub
		Query qry = session.getNamedQuery(QUERY_FOR_EXPENSE_LIMIT_50);	
		qry.setMaxResults(50);
		
        @SuppressWarnings("unchecked")
		final List<Expense> expenselist = qry.list();
    	return expenselist;
	}

		

	
	
}
