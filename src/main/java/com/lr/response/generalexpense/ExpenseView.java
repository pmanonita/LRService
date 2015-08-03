package com.lr.response.generalexpense;

import java.util.Date;

import com.lr.model.AccountSource;

public class ExpenseView {

	private Long    	  id;
	private int    		  amount;
	private AccountSource account;
	private Date   		  date;	
	private String 		  status;
	private String 		  remark;	
    
    public ExpenseView() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public AccountSource getAccount() {
		return account;
	}

	public void setAccount(AccountSource account) {
		this.account = account;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}	   

}
