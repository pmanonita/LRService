package com.lr.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.lr.exceptions.InsufficientDataException;

public class AccountSource  implements Serializable {	

	private static final long serialVersionUID = 2659087404553275759L;

	private int    _id;
	private String _accountName;
	private Date   _createDate;
	
	//For hibernate
	public AccountSource() {	}
	
	public AccountSource (Controller ctrl) {
		createFrom(ctrl);		
	}
	
	private void createFrom(Controller ctrl) {
		
		_accountName = ctrl.mAccountName();
		_createDate  = ctrl.mCreateDate();		
	}
	
	public void changeTo(Controller ctrl) {
		
		_accountName = ctrl.mAccountName();
		_createDate  = ctrl.mCreateDate();		
	}
	
	public void populate(Controller ctrl) {
		ctrl.mAccountName(_accountName);
		ctrl.mCreateDate(_createDate);
	}

	public interface Controller {
		String mAccountName();
		void mAccountName(String accountName);
		
		Date mCreateDate();
		void mCreateDate(Date createDate);
		
	}
	
	public abstract static class DefaultController implements Controller {
		public void mAccountName(String username) 		{	}
		public void mCreateDate(Date createDate) 	{	}		
	}

	//getter and setter
	public int getId()								{ return _id; 						}
	protected void setId(int id) 					{ this._id = id; 					}
	
	public String getAccountName() 					{ return _accountName;	 			}
	private void setAccountName(String accountName)	{ this._accountName = accountName; 	}
	
	public Date getCreateDate() 					{ return _createDate;				}
	private void setCreateDate(Date createDate) 	{ this._createDate = createDate;	}

	public static long getSerialversionuid() 		{ return serialVersionUID;			}
	
	
	private static final String QUERY_FOR_ACCOUNT_BY_ID =
    		AccountSource.class.getName() + ".findById";
	public static AccountSource findById(Session session, Integer id)
		throws HibernateException
	{
		if (id == null) {
			return null;
		}
		
		Query qry = session.getNamedQuery(QUERY_FOR_ACCOUNT_BY_ID);
    	qry.setInteger("id", id);
    	
        qry.setMaxResults(1);
        
        final AccountSource account = (AccountSource)(qry.uniqueResult());
    	return account;	
	}
	
	private static final String QUERY_FOR_ACCOUNT_BY_NAME =
		AccountSource.class.getName() + ".findAccountByName";
	public static AccountSource findAccountByName(Session session, String accountName)
		throws HibernateException
	{
		if (accountName == null ||
				(accountName != null && accountName.equals("")))
		{
			return null;
		}
		
		Query qry = session.getNamedQuery(QUERY_FOR_ACCOUNT_BY_NAME);
		qry.setString("accountName", accountName);
		
		qry.setMaxResults(1);
		
		final AccountSource account = (AccountSource)(qry.uniqueResult());
		return account;	
	}

	
	private static final String QUERY_FOR_LIST_ACCOUNTS =
			AccountSource.class.getName() + ".findAllAccounts";
	public static List<AccountSource> findAllAccounts(Session session) {
		
    	Query qry = session.getNamedQuery(QUERY_FOR_LIST_ACCOUNTS);	
        
        @SuppressWarnings("unchecked")
		final List<AccountSource> accountlist = qry.list();
    	return accountlist;
	}
	
	
}
