package com.lr.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.lr.model.LR.Controller;

public class LRTransaction implements Serializable {

	private static final long serialVersionUID = -1277884600127776153L;

	private long     _id;
	private Set<LR>  _lrs;
	
	
	public LRTransaction() {	}
	
	public LRTransaction (Controller ctrl) {
		createFrom(ctrl);		
	}
	
	

	private void createFrom(Controller ctrl) {		
		_lrs	= ctrl.mLRs();
		
	}
	
	public void changeTo(Controller ctrl) {		
		_lrs	= ctrl.mLRs();		
	}

	public interface Controller {				
				
		Set<LR> mLRs();
		void mLRs(Set<LR> lrs);	
		
	}
	
	public abstract static class DefaultController implements Controller {
		public void mLRs(Set<LR> lrs)		{	}
		
	}
	
	
	
	//get and set
	public long getId() {
		return _id;
	}	
	protected void setId(long _id) {
		this._id = _id;
	}
	public Set<LR> getLrs() {
		return _lrs;
	}
	private void setLrs(Set<LR> lrs) {
		this._lrs = lrs;
	}
	
	
	
}
