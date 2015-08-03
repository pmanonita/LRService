package com.lr.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.lr.exceptions.InsufficientDataException;


public class LR implements Serializable {	

	private static final long serialVersionUID = -6779738051490200702L;
	
	private long          _id;
	private long          _transid;	
	private String        _vehicleNo;
	private String         _vehicleOwner;
	private String        _billingToParty;
	private Date          _lrDate;	
	private String        _multiLoad;
	private String        _userName;
	private Date          _updatetimestamp;	
	private LRExpenditure _lrexpenditureId;
	private LRIncome      _lrincomeId;
	private Consigner     _consignerId;
	private Consignee     _consigneeId;
	private Set<LROthers> _otherExpenditures;
	private Billingname  _billingnameId;
	private String         _poNo;
	private String         _doNo;
	private String         _status;
	
	public LR() {	}
	
	public LR (Controller ctrl) {
		createFrom(ctrl);		
	}
	
	private void validate(Controller ctrl) throws InsufficientDataException {
		//Model level validation
		String errorMsg = "";
		if ((null == ctrl.mVehicleNo() || (null != ctrl.mMultiLoad() && ctrl.mUserName().equals("")))) 
		{
			errorMsg = "vechile no can't be null or empty";
			throw new InsufficientDataException(errorMsg);
		}	
		
	}
	
	private void createFrom(Controller ctrl) {
		//validate(ctrl);
		
		_transid = ctrl.mTransid();		
		_vehicleNo = ctrl.mVehicleNo();	
		_consignerId = ctrl.mConsignerId();
		_consigneeId = ctrl.mConsigneeId();		
		_vehicleOwner = ctrl.mVehicleOwner();
		_billingToParty = ctrl.mBillingToParty();
		_lrDate = ctrl.mLrDate();		
		_userName = ctrl.mUserName();	
		_lrexpenditureId = ctrl.mLrexpenditureId();
		_lrincomeId = ctrl.mLrincomeId();
		_otherExpenditures = ctrl.mOtherExpenditures();
		_poNo = ctrl.mPONo();
		_doNo = ctrl.mDONo();
		_billingnameId = ctrl.mBillingname();
		_status = ctrl.mStatus();
	}
	
	public void changeTo(Controller ctrl) {		
		//validate(ctrl);
		
		_transid = ctrl.mTransid();		
		_vehicleNo = ctrl.mVehicleNo();	
		_consignerId = ctrl.mConsignerId();
		_consigneeId = ctrl.mConsigneeId();		
		_vehicleOwner = ctrl.mVehicleOwner();
		_billingToParty = ctrl.mBillingToParty();
		_lrDate = ctrl.mLrDate();		
		_userName = ctrl.mUserName();
		_lrexpenditureId = ctrl.mLrexpenditureId();
		_lrincomeId = ctrl.mLrincomeId();
		_otherExpenditures = ctrl.mOtherExpenditures();
		_poNo = ctrl.mPONo();
		_doNo = ctrl.mDONo();
		_billingnameId = ctrl.mBillingname();
		_status = ctrl.mStatus();
		
	}

	public interface Controller {
				
		long mTransid();
		void mTransid(long transid);		

		String mVehicleNo();
		void mVehicleNo(String _vehicleNo);

		Consigner mConsignerId();
		void mConsignerId(Consigner _consignerId);

		Consignee mConsigneeId();
		void mConsigneeId(Consignee _consigneeId);
		
		LRExpenditure mLrexpenditureId();
		void mLrexpenditureId(LRExpenditure _lrexpenditureId);
		
		LRIncome mLrincomeId();
		void mLreincomeId(LRIncome _lrincomeId);	

		String mVehicleOwner();
		void mVehicleOwner(String _vehicleOwner);

		String mBillingToParty();
		void mBillingToParty(String _billingToParty);

		Date mLrDate();
		void mLrDate(Date _lrDate);		

		String mMultiLoad();
		void mMultiLoad(String _multiLoad);
		
		String mUserName();
		void mUserName(String _userName);
		
		Set<LROthers> mOtherExpenditures();
		void mOtherExpenditures(Set<LROthers> _otherExpenditures);
		
		String mPONo();
		void mPONo(String _poNo);
		
		String mDONo();
		void mDONo(String _doNo);
		
		Billingname mBillingname();
		void mBillingname(Billingname _billingnameId);
		
		String mStatus();
		void mStatus(String _status);
		
	}
	
	public abstract static class DefaultController implements Controller {
		public void mTransid(long transid) 		{	}
		public void mVehicleNo(String vehicleNo) 		{	}
		public void mConsignerId(Consigner consignerId)	{	}
		public void mConsigneeId(Consignee consigneeId) 		{	}	
		public void mLrexpenditureId(LRExpenditure lrexpenditureId) 		{	}	
		public void mLreincomeId(LRIncome lrincomeId) {	}
		public void mVehicleOwner(String vehicleOwner) 	{	}
		public void mBillingToParty(String billingToParty) 		{	}
		public void mLrDate(Date lrDate)	 			{	}
		public void mMultiLoad(String multiLoad) 	{	}
		public void mUserName(String userName) 	{	}		
		public void mOtherExpenditures(Set otherExpenditures)	{	}
		public void mPONo(String poNo) 	{	}
		public void mDONo(String doNo) 	{	}
		public void mBillingname(Billingname _billingnameId) {	}
		public void mStatus(String status) 	{	}
	}

	
	//getter and setter
	
	public long getId() {
		return _id;
	}

	protected void setId(long id) {
		this._id = id;
	}

	public long getTransid() {
		return _transid;
	}

	void setTransid(long transid) {
		this._transid = transid;
	}

	public String getVehicleNo() {
		return _vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this._vehicleNo = vehicleNo;
	}

	public Consigner getConsignerId() {
		return _consignerId;
	}

	public void setConsignerId(Consigner consignerId) {
		this._consignerId = consignerId;
	}

	public Consignee getConsigneeId() {
		return _consigneeId;
	}

	public void setConsigneeId(Consignee consigneeId) {
		this._consigneeId = consigneeId;
	}	

	public String getVehicleOwner() {
		return _vehicleOwner;
	}

	public void setVehicleOwner(String vehicleOwner) {
		this._vehicleOwner = vehicleOwner;
	}

	public String getBillingToParty() {
		return _billingToParty;
	}

	public void setBillingToParty(String billingToParty) {
		this._billingToParty = billingToParty;
	}

	public Date getLrDate() {
		return _lrDate;
	}

	public void setLrDate(Date lrDate) {
		this._lrDate = lrDate;
	}

	public String getMultiLoad() {
		return _multiLoad;
	}

	public void setMultiLoad(String multiLoad) {
		this._multiLoad = multiLoad;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		this._userName = userName;
	}	

	public Date getUpdatetimestamp() {
		return _updatetimestamp;
	}

	public void setUpdatetimestamp(Date updatetimestamp) {
		this._updatetimestamp = updatetimestamp;
	}

	//To-do : Method name shouldn't have Id in it. Confusing. It should be "getLRExpenditure" 
	public LRExpenditure getLrexpenditureId() {
		return _lrexpenditureId;
	}

	public void setLrexpenditureId(LRExpenditure lrexpenditureId) {
		this._lrexpenditureId = lrexpenditureId;
	}
	
	public LRIncome getLrincomeId() {
		return _lrincomeId;
	}

	public void setLrincomeId(LRIncome lrincomeId) {
		this._lrincomeId = lrincomeId;
	}
	
	public Set<LROthers> getOtherExpenditures() {
		return _otherExpenditures;
	}
	public void setOtherExpenditures(Set<LROthers> otherExpenditures){
		this._otherExpenditures = otherExpenditures;
	}
	public String getPoNo() {
		return _poNo;
	}

	public void setPoNo(String poNo) {
		this._poNo = poNo;
	}
	public String getDoNo() {
		return _doNo;
	}

	public void setDoNo(String doNo) {
		this._doNo = doNo;
	}
	
	public Billingname getBillingnameId() {
		return _billingnameId;
	}

	public void setBillingnameId(Billingname billingnameId) {
		this._billingnameId = billingnameId;
	}
	
	public String getStatus() {
		return _status;
	}

	public void setStatus(String status) {
		this._status = status;
	}

	public static long mSerialversionuid() 		{ return serialVersionUID;		}
	
	
	private static final String QUERY_FOR_LR_BY_ID_SKEY =
		LR.class.getName() + ".findLRById";	
	public static LR findLRById(Session session, Long id)
		throws HibernateException
	{
	 	if (id == null) {
	 		return null;
	 	}
	 	
	 	Query qry = session.getNamedQuery(QUERY_FOR_LR_BY_ID_SKEY);
	 	qry.setLong("id", id);		 	
 
	 	qry.setMaxResults(1);
 
	 	final LR lr = (LR)(qry.uniqueResult());
	 	return lr;
	}
	
	private static final String QUERY_FOR_LR_BY_DATE_SKEY =
		LR.class.getName() + ".findLRByDate";	
	public static List<LR> findLRByDate(Session session, String lrDate)
		throws HibernateException
	{
	 	if (lrDate == null) {
	 		return null;
	 	}
	 	
	 	Query qry = session.getNamedQuery(QUERY_FOR_LR_BY_DATE_SKEY);
	 	qry.setString("lrDate", lrDate);		 	
 
	 	//qry.setMaxResults(1);
 
	 	final List<LR> lrList = qry.list();
	 	return lrList;
	}
	
}
