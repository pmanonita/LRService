package com.lr.model;



import com.lr.exceptions.InsufficientDataException;


public class LRIncome {
	
	private static final long serialVersionUID = -6779738051490200702L;
	
	private long   _id;
	private long _lrId;	
	private int _freightToBroker;	
	private int _extraPayToBroker;	
	private int _loadingCharges;
	private int _unloadingCharges;
	private int   _loadingDetBroker;
	private int   _unloadingDetBroker;
	
	//For hibernate
	public LRIncome() {
		// TODO Auto-generated constructor stub
	}
	
	public LRIncome (Controller ctrl) {
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
		_freightToBroker = ctrl.mFreightToBroker();
		_extraPayToBroker = ctrl.mExtraPayToBroker();		
		_loadingCharges = ctrl.mLoadingCharges();
		_unloadingCharges = ctrl.mUnloadingCharges();
		_loadingDetBroker = ctrl.mLoadingDetBroker();
		_unloadingDetBroker = ctrl.mUnloadingDetBroker();		
			
	}
	
	public void changeTo(Controller ctrl) {
		
		validate(ctrl);
		
		_lrId = ctrl.mLRId();	
		_freightToBroker = ctrl.mFreightToBroker();
		_extraPayToBroker = ctrl.mExtraPayToBroker();		
		_loadingCharges = ctrl.mLoadingCharges();
		_unloadingCharges = ctrl.mUnloadingCharges();
		_loadingDetBroker = ctrl.mLoadingDetBroker();
		_unloadingDetBroker = ctrl.mUnloadingDetBroker();
		
	}

	public interface Controller {
				
		long mLRId();
		void mLRId(long lrNo);		

		int mFreightToBroker();
		void mFreightToBroker(int freightToBroker);

		int mExtraPayToBroker();
		void mExtraPayToBrokersignor(int extraPayToBroker);	
		 
		int mLoadingCharges();
		void mLoadingCharges(int loadingCharges);

		int mUnloadingCharges();
		void mUnloadingCharges(int unloadingCharges);

		int mLoadingDetBroker();
		void mLoadingDetBroker(int loadingDetBroker);

		int mUnloadingDetBroker();
		void mUnloadingDetBroker(int unloadingDetBroker);		

		

		
		
	}
	
	public abstract static class DefaultController implements Controller {

		
		@Override
		public void mLRId(long lrId) { }
		
		@Override
		public void mFreightToBroker(int freightToBroker) { }

		@Override
		public void mExtraPayToBrokersignor(int extraPayToBroker) { }			
		

		@Override
		public void mLoadingCharges(int loadingCharges) { }
		
		@Override
		public void mUnloadingCharges(int unloadingCharges) { }
		
		@Override
		public void mLoadingDetBroker(int loadingDetBroker) { }
		
		@Override
		public void mUnloadingDetBroker(int unloadingDetBroker) { }
				
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

	public int getFreightToBroker() {
		return _freightToBroker;
	}

	void setFreightToBroker(int freightToBroker) {
		this._freightToBroker = freightToBroker;
	}

	public int getExtraPayToBroker() {
		return _extraPayToBroker;
	}

	void setExtraPayToBroker(int extraPayToBroker) {
		this._extraPayToBroker = extraPayToBroker;
	}	

	public int getLoadingCharges() {
		return _loadingCharges;
	}

	void setLoadingCharges(int loadingCharges) {
		this._loadingCharges = loadingCharges;
	}

	public int getUnloadingCharges() {
		return _unloadingCharges;
	}

	void setUnloadingCharges(int unloadingCharges) {
		this._unloadingCharges = unloadingCharges;
	}

	public int getLoadingDetBroker() {
		return _loadingDetBroker;
	}

	void setLoadingDetBroker(int loadingDetBroker) {
		this._loadingDetBroker = loadingDetBroker;
	}

	public int getUnloadingDetBroker() {
		return _unloadingDetBroker;
	}

	void setUnloadingDetBroker(int unloadingDetBroker) {
		this._unloadingDetBroker = unloadingDetBroker;
	}

	
	
	public static long mSerialversionuid() 		{ return serialVersionUID;		}
	
	
	

}
