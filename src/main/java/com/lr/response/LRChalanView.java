package com.lr.response;

public class LRChalanView {

	private long    id;
	private String lrIds;
	private String chalanDetails;
	private int totalCost;
	
    
    public LRChalanView() {}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getLrIds() {
		return lrIds;
	}


	public void setLrIds(String lrIds) {
		this.lrIds = lrIds;
	}


	public String getChalanDetails() {
		return chalanDetails;
	}


	public void setChalanDetails(String chalanDetails) {
		this.chalanDetails = chalanDetails;
	}


	public int getTotalCost() {
		return totalCost;
	}


	public void setTotalCost() {
		//calculation of totalcost
		int totalExpenditure = 0;
		if (chalanDetails != null) {
			String[] expenditureArr = chalanDetails.split(",");
			int amount = 0;
			for (int i=0;i<expenditureArr.length;i++){
				expenditureArr[i] = expenditureArr[i].replaceAll("\"","");
				String[] expeditureColumnArr = expenditureArr[i].split("-");
				if( expeditureColumnArr.length>1 ){					
					if (expeditureColumnArr[0].contains("EXTRA PAY TO BROKER") || expeditureColumnArr[0].contains("ADVANCE") || expeditureColumnArr[0].contains("BALANCE FREIGHT") ) {
						continue;
					} else {
						try{
							amount = Integer.parseInt(expeditureColumnArr[1].trim());
							totalExpenditure = totalExpenditure+amount;
							
						}catch(Exception e){	}
						
					}
					
				}
				
			}
		}
		this.totalCost = totalExpenditure;
	}
	
	


	

	
   

}
