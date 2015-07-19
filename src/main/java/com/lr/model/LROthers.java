package com.lr.model;

import java.util.Date;

import com.lr.exceptions.InsufficientDataException;
import com.lr.model.LR.Controller;

public class LROthers {
	
	private static final long serialVersionUID = -6779738051490200702L;
	
	private long   _id;
	private long _lrNo;	
	private int _amount;	
	private String _remarks;
	
	
	//For hibernate
	public LROthers() {
		// TODO Auto-generated constructor stub
	}
	
	public LROthers (Controller ctrl) {
		createFrom(ctrl);
		
	}
	
	private void validate(Controller ctrl) throws InsufficientDataException {
		//Model level validation
		String errorMsg = "";
		if (ctrl.mLRNo() == 0 ) 
		{
			errorMsg = "LR No can't be null or empty";
			throw new InsufficientDataException(errorMsg);
		}	
		
	}
	
	private void createFrom(Controller ctrl) {
		

		validate(ctrl);
		
		_lrNo = ctrl.mLRNo();	
		_amount = ctrl.mAmount();
		_remarks = ctrl.mRemarks();
			
			
	}
	
	public void changeTo(Controller ctrl) {
		
		validate(ctrl);
		
		_lrNo = ctrl.mLRNo();	
		_amount = ctrl.mAmount();
		_remarks = ctrl.mRemarks();
		
	}

	public interface Controller {
				
		long mLRNo();
		void mLRNo(long lrNo);		

		int mAmount();
		void mAmount(int amount);

		String mRemarks();
		void mRemarks(String remarks);

				
	}
	
	public abstract static class DefaultController implements Controller {

		
		@Override
		public void mLRNo(long lrNo) { }
		
		@Override
		public void mAmount(int freightToBroker) { }

		@Override
		public void mRemarks(String remarks) { }
		
		
				
	}

	//mter and mter
	public long mId(){ 
		return _id; 	
	}
	protected void mId(int id){
		this._id = id;
	}
	
	
	public long mLRNo(){return _lrNo;}
	void mLRNo(long lrNo) {
		this._lrNo = lrNo;
	}

	public int mAmount(){ return _amount; }
	void mAmount(int amount){
		this._amount = amount;
	}

	public String mRemarks(){ return _remarks; }
	void mRemarks(String remarks){
		this._remarks = remarks;
	}

	

	
	public static long mSerialversionuid() 		{ return serialVersionUID;		}
	
	
	

}
