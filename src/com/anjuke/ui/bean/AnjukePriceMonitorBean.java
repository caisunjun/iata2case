package com.anjuke.ui.bean;

public class AnjukePriceMonitorBean {
	//城市名称
	private String City = null;
	//城市名称
	private String Community = null;
	//时间
	private String DATE = null;
	//均价趋势
	private String Thrend = null;
	//价格
	private String Price = null;
	//小区ID
	private String CommunityID= null;
	
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getCommunity() {
		return Community;
	}
	public void setCommunity(String community) {
		Community = community;
	}
	public String getDATE() {
		return DATE;
	}
	public void setDATE(String dATE) {
		DATE = dATE;
	}
	public String getThrend() {
		return Thrend;
	}
	public void setThrend(String thrend) {
		Thrend = thrend;
	}
	public String getPrice() {
		return Price;
	}
	public void setPrice(String price) {
		Price = price;
	}
	public String getCommunityID() {
		return CommunityID;
	}
	public void setCommunityID(String communityID) {
		CommunityID = communityID;
	}

}
