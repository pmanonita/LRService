package com.lr.model;

import java.util.Date;

import com.lr.exceptions.InsufficientDataException;
import com.lr.model.LR.Controller;

public class LROthers {
	
	private static final long serialVersionUID = -6779738051490200702L;
	
	private long   _id;
	private long _lrId;	
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
		if (ctrl.mLRId() == 0 ) 
		{
			errorMsg = "LR No can't be null or empty";
			throw new InsufficientDataException(errorMsg);
		}	
		
	}
	
	private void createFrom(Controller ctrl) {
		

		validate(ctrl);
		
		_lrId = ctrl.mLRId();	
		_amount = ctrl.mAmount();
		_remarks = ctrl.mRemarks();
			
			
	}
	
	public void changeTo(Controller ctrl) {
		
		validate(ctrl);
		
		_lrId = ctrl.mLRId();	
		_amount = ctrl.mAmount();
		_remarks = ctrl.mRemarks();
		
	}

	public interface Controller {
				
		long mLRId();
		void mLRId(long lrId);		

		int mAmount();
		void mAmount(int amount);

		String mRemarks();
		void mRemarks(String remarks);

				
	}
	
	public abstract static class DefaultController implements Controller {

		
		@Override
		public void mLRId(long lrId) { }
		
		@Override
		public void mAmount(int freightToBroker) { }

		@Override
		public void mRemarks(String remarks) { }
		
		
				
	}

	//getter and setter
	
	public long getId() {
		return _id;
	}

	protected void setId(long id) {
		this._id = id;
	}
	
	
	public long getLrId() {
		return _lrId;
	}

	void setLrId(long lrId) {
		this._lrId = lrId;
	}

	public int getAmount(){ return _amount; }
	void setAmount(int amount){
		this._amount = amount;
	}

	public String getRemarks(){ return _remarks; }
	void setRemarks(String remarks){
		this._remarks = remarks;
	}

	

	
	public static long mSerialversionuid() 		{ return serialVersionUID;		}
	
	
	

}
