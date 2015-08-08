package com.lr.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class LR implements Serializable {	

	private static final long serialVersionUID = -6779738051490200702L;
	
	private long          _id;
	private LRTransaction _transaction;	
	private String        _vehicleNo;
	private String         _vehicleOwner;
	private String        _billingToParty;
	private Date          _lrDate;	
	private String        _multiLoad;
	private String        _userName;
	private Date          _updatetimestamp;	
	private LRExpenditure _lrexpenditureId;
	private LRIncome      _lrincomeId;
	private LRChalan 	  _lrchalanId;
	private LRBill        _lrbillId;
	private Consigner     _consignerId;
	private Consignee     _consigneeId;
	private Set<LROthers> _otherExpenditures;
	private Set<LROtherIncome> _otherIncomes;
	private Billingname  _billingnameId;
	private String         _poNo;
	private String         _doNo;
	private String         _status;
	
	public LR() {	}
	
	public LR (Controller ctrl) {
		createFrom(ctrl);		
	}
	
	/*
	private void validate(Controller ctrl) throws InsufficientDataException {
		//Model level validation
		String errorMsg = "";
		if ((null == ctrl.mVehicleNo() || (null != ctrl.mMultiLoad() && ctrl.mUserName().equals("")))) 
		{
			errorMsg = "vechile no can't be null or empty";
			throw new InsufficientDataException(errorMsg);
		}	
		
	}*/
	
	private void createFrom(Controller ctrl) {
		//validate(ctrl);
		
		_transaction 	 	= ctrl.mTransid();		
		_vehicleNo 		 	= ctrl.mVehicleNo();	
		_consignerId 	 	= ctrl.mConsignerId();
		_consigneeId 	 	= ctrl.mConsigneeId();		
		_vehicleOwner 	 	= ctrl.mVehicleOwner();
		_billingToParty  	= ctrl.mBillingToParty();
		_lrDate 		 	= ctrl.mLrDate();		
		_userName 		 	= ctrl.mUserName();	
		_lrexpenditureId 	= ctrl.mLrexpenditureId();
		_lrchalanId 		=  ctrl.mLrchalanId();
		_lrbillId       	= ctrl.mLrbillId();
		_lrincomeId 		= ctrl.mLrincomeId();
		_otherExpenditures 	= ctrl.mOtherExpenditures();
		_otherIncomes 		= ctrl.mOtherIncomes();
		_poNo 				= ctrl.mPONo();
		_doNo 				= ctrl.mDONo();
		_billingnameId 		= ctrl.mBillingname();
		_status 			= ctrl.mStatus();
		_multiLoad 			= ctrl.mMultiLoad();
	}
	
	public void changeTo(Controller ctrl) {		
		//validate(ctrl);
		
		_transaction	 	= ctrl.mTransid();		
		_vehicleNo 			= ctrl.mVehicleNo();	
		_consignerId 		= ctrl.mConsignerId();
		_consigneeId 		= ctrl.mConsigneeId();		
		_vehicleOwner 		= ctrl.mVehicleOwner();
		_billingToParty 	= ctrl.mBillingToParty();
		_lrDate 			= ctrl.mLrDate();		
		_userName 			= ctrl.mUserName();
		_lrexpenditureId 	= ctrl.mLrexpenditureId();
		_lrchalanId 		=  ctrl.mLrchalanId();
		_lrbillId       	= ctrl.mLrbillId();
		_lrincomeId 		= ctrl.mLrincomeId();
		_otherExpenditures	= ctrl.mOtherExpenditures();
		_otherIncomes 		= ctrl.mOtherIncomes();
		_poNo 				= ctrl.mPONo();
		_doNo 				= ctrl.mDONo();
		_billingnameId 		= ctrl.mBillingname();
		_status 			= ctrl.mStatus();
		_multiLoad 			= ctrl.mMultiLoad();
		
	}

	public interface Controller {
				
		LRTransaction mTransid();
		void mTransid(LRTransaction transid);		

		String mVehicleNo();
		void mVehicleNo(String vehicleNo);

		Consigner mConsignerId();
		void mConsignerId(Consigner consignerId);

		Consignee mConsigneeId();
		void mConsigneeId(Consignee consigneeId);
		
		LRExpenditure mLrexpenditureId();
		void mLrexpenditureId(LRExpenditure lrexpenditureId);
		
		LRChalan mLrchalanId();
		void mLrchalanId(LRChalan lrchalanId);
		
		LRBill mLrbillId();
		void mLrbillId(LRBill lrbillId);
		
		LRIncome mLrincomeId();
		void mLrincomeId(LRIncome lrincomeId);	

		String mVehicleOwner();
		void mVehicleOwner(String vehicleOwner);

		String mBillingToParty();
		void mBillingToParty(String billingToParty);

		Date mLrDate();
		void mLrDate(Date lrDate);		

		String mMultiLoad();
		
		void mMultiLoad(String multiLoad);
		
		String mUserName();
		void mUserName(String userName);
		
		Set<LROthers> mOtherExpenditures();
		void mOtherExpenditures(Set<LROthers> otherExpenditures);
		
		Set<LROtherIncome> mOtherIncomes();
		void mOtherIncomes(Set<LROtherIncome> otherIncomes);
		
		String mPONo();
		void mPONo(String poNo);
		
		String mDONo();
		void mDONo(String doNo);
		
		Billingname mBillingname();
		void mBillingname(Billingname billingnameId);
		
		String mStatus();
		void mStatus(String status);
	}
	
	public abstract static class DefaultController implements Controller {
		public void mTransid(LRTransaction transid) 					{	}
		public void mVehicleNo(String vehicleNo) 						{	}
		public void mConsignerId(Consigner consignerId)					{	}
		public void mConsigneeId(Consignee consigneeId) 				{	}	
		public void mLrexpenditureId(LRExpenditure lrexpenditureId)		{	}
		public void mLrchalanId(LRChalan lrchalanId) 					{	}
		public void mLrbillId(LRBill lrbillId) 							{	}
		public void mLrincomeId(LRIncome lrincomeId) 					{	}
		public void mVehicleOwner(String vehicleOwner) 					{	}
		public void mBillingToParty(String billingToParty) 				{	}
		public void mLrDate(Date lrDate)	 							{	}
		public void mMultiLoad(String multiLoad) 						{	}
		public void mUserName(String userName) 							{	}		
		public void mOtherExpenditures(Set<LROthers> otherExpenditures)	{	}
		public void mOtherIncomes(Set<LROtherIncome> otherIncomes)		{	}
		public void mPONo(String poNo) 									{	}
		public void mDONo(String doNo) 									{	}
		public void mBillingname(Billingname _billingnameId) 			{	}
		public void mStatus(String status) 	{	}
	}

	
	//getter and setter
	
	public long getId() {
		return _id;
	}

	protected void setId(long id) {
		this._id = id;
	}

	public LRTransaction getTransaction() {
		return _transaction;
	}

	public void setTransaction(LRTransaction _transaction) {
		this._transaction = _transaction;
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
	
	public LRChalan getLrchalanId() {
		return _lrchalanId;
	}

	public void setLrchalanId(LRChalan lrchalanId) {
		this._lrchalanId = lrchalanId;
	}
	
	public LRBill getLrbillId() {
		return _lrbillId;
	}

	public void setLrbillId(LRBill lrbillId) {
		this._lrbillId = lrbillId;
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
	
	public Set<LROtherIncome> getOtherIncomes() {
		return _otherIncomes;
	}
	public void setOtherIncomes(Set<LROtherIncome> otherIncomes){
		this._otherIncomes = otherIncomes;
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
	
	
	private static final String QUERY_FOR_LR_BY_ID =
		LR.class.getName() + ".findLRById";	
	public static LR findLRById(Session session, Long id)
		throws HibernateException
	{
	 	if (id == null) {
	 		return null;
	 	}
	 	
	 	Query qry = session.getNamedQuery(QUERY_FOR_LR_BY_ID);
	 	qry.setLong("id", id);		 	
 
	 	qry.setMaxResults(1);
 
	 	final LR lr = (LR)(qry.uniqueResult());
	 	return lr;
	}
	
	private static final String QUERY_FOR_LR_BY_DATE =
		LR.class.getName() + ".findLRByDate";	
	@SuppressWarnings("unchecked")
	public static List<LR> findLRByDate(Session session, Date lrDate)
		throws HibernateException
	{
	 	if (lrDate == null) {
	 		return null;
	 	}
	 	
	 	Query qry = session.getNamedQuery(QUERY_FOR_LR_BY_DATE);
	 	qry.setDate("lrDate", lrDate); 
	 	final List<LR> lrList = qry.list();
	 	return lrList;
	}

	private static final String QUERY_FOR_LR_BY_DATE_MULTILOAD_STATUS =
			LR.class.getName() + ".findLRByDateMultiLoadStatus";	
	@SuppressWarnings("unchecked")
	public static List<LR> findByDateMultiLoadStatus(Session session, Date lrDate, 
			String multiLoad, String status)
	{
		if (lrDate == null) {
	 		return null;
	 	}
	 	
	 	Query qry = session.getNamedQuery(QUERY_FOR_LR_BY_DATE_MULTILOAD_STATUS);
	 	qry.setDate("lrDate", lrDate);
	 	qry.setString("multiLoad", multiLoad);
	 	qry.setString("status", status);

	 	final List<LR> lrList = qry.list();
	 	return lrList;
	}

	private static final String QUERY_FOR_LR_BY_DATE_MULTILOAD =
			LR.class.getName() + ".findLRByDateMultiLoad";	
	@SuppressWarnings("unchecked")
	public static List<LR> findByDateMultiLoad(Session session, Date lrDate, String multiLoad) {
		if (lrDate == null) {
	 		return null;
	 	}	 	
	 	Query qry = session.getNamedQuery(QUERY_FOR_LR_BY_DATE_MULTILOAD);
	 	qry.setDate("lrDate", lrDate);
	 	qry.setString("multiLoad", multiLoad);

	 	final List<LR> lrList = qry.list();
	 	return lrList;
	}

	private static final String QUERY_FOR_LR_BY_DATE_STATUS =
			LR.class.getName() + ".findLRByDateStatus";	
	@SuppressWarnings("unchecked")
	public static List<LR> findByDateStatus(Session session, Date lrDate, String status) {
		if (lrDate == null) {
	 		return null;
	 	}
	 	
	 	Query qry = session.getNamedQuery(QUERY_FOR_LR_BY_DATE_STATUS);
	 	qry.setDate("lrDate", lrDate);
	 	qry.setString("status", status);
	 	
	 	final List<LR> lrList = qry.list();
	 	return lrList;
	}
	
	private static final String QUERY_FOR_LR_LIMIT_50 =
    		Expense.class.getName() + ".findFirstFifty";
	@SuppressWarnings("unchecked")
	public static List<LR> findFirstFifty(Session session) {		
		Query qry = session.getNamedQuery(QUERY_FOR_LR_LIMIT_50);	
		qry.setMaxResults(50);
		
	 	final List<LR> lrList = qry.list();
	 	return lrList;
	}

	private static final String QUERY_FOR_LR_BY_DATE_MULTILOAD_STATUS_NOATTACHED =
			LR.class.getName() + ".findByDateMultiLoadStatusNoAttach";	
	@SuppressWarnings("unchecked")
	public static List<LR> findByDateMultiLoadStatusNoAttach(Session session,
			Date lrDate, String multiLoad, String status)
	{
		if (lrDate == null) {
	 		return null;
	 	}
	 	
	 	Query qry = session.getNamedQuery(QUERY_FOR_LR_BY_DATE_MULTILOAD_STATUS_NOATTACHED);
	 	qry.setDate("lrDate", lrDate);
	 	qry.setString("multiLoad", multiLoad);
	 	qry.setString("status", status);
	 	
	 	final List<LR> lrList = qry.list();
	 	return lrList;
		
	}
	
	private static final String QUERY_FOR_LR_BY_DATE_MULTILOAD_STATUS_ATTACHED =
			LR.class.getName() + ".findByDateMultiLoadStatusAttach";	
	@SuppressWarnings("unchecked")
	public static List<LR> findByDateMultiLoadStatusAttach(Session session,
			Date lrDate, String multiLoad, String status) 
	{
		if (lrDate == null) {
	 		return null;
	 	}
		
	 	Query qry = session.getNamedQuery(QUERY_FOR_LR_BY_DATE_MULTILOAD_STATUS_ATTACHED);
	 	qry.setDate("lrDate", lrDate);
	 	qry.setString("multiLoad", multiLoad);
	 	qry.setString("status", status);

	 	
	 	final List<LR> lrList = qry.list();
	 	return lrList;
	}

	private static final String QUERY_FOR_LR_BY_DATE_MULTILOAD_ATTACHED =
			LR.class.getName() + ".findByDateMultiLoadAttach";	
	@SuppressWarnings("unchecked")
	public static List<LR> findByDateMultiLoadAttach(Session session,
			Date lrDate, String multiLoad)
	{
		if (lrDate == null) {
	 		return null;
	 	}
	 	
	 	Query qry = session.getNamedQuery(QUERY_FOR_LR_BY_DATE_MULTILOAD_ATTACHED);
	 	qry.setDate("lrDate", lrDate);
	 	qry.setString("multiLoad", multiLoad);
	 	
	 	final List<LR> lrList = qry.list();
	 	return lrList;
	}

	private static final String QUERY_FOR_LR_BY_DATE_MULTILOAD_NOATTACHED =
			LR.class.getName() + ".findByDateMultiLoadNoAttach";	
	@SuppressWarnings("unchecked")
	public static List<LR> findByDateMultiLoadNoAttach(Session session,
			Date lrDate, String multiLoad)
	{
	
		if (lrDate == null) {
	 		return null;
	 	}
	 	
	 	Query qry = session.getNamedQuery(QUERY_FOR_LR_BY_DATE_MULTILOAD_NOATTACHED);
	 	qry.setDate("lrDate", lrDate);
	 	qry.setString("multiLoad", multiLoad);
	 	
	 	final List<LR> lrList = qry.list();
	 	return lrList;
	}
	
}
