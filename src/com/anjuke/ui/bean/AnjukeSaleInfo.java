package com.anjuke.ui.bean;
/*
 * bean for sale or rent
 */
public class AnjukeSaleInfo {
	//经济人姓名
	private String userName ;
	//小区名称
	private String communityName;
	//放我类型
	private String houseType;
	//当前楼层
	private String floorCur;
	//总共楼层
	private String floorTotal;
	//房屋面积
	private String houseArea;
	//房型-室
	private String houseType_S;
	//房型-厅
	private String houseType_T;	
	//房型-卫
	private String houseType_W;
	//税费自理价
	private String priceTaxe;
	//房东到手价
	private String priceGet;
	//建筑时间
	private String buildYear;
	//装修情况
	private String fitmentInfo;
	//房屋朝向
	private String orientations;
	//房屋标题
	private String houseTitle;
	//房屋描述
	private String houseDescribe;
	//房屋单价
	private String price;
	//租金
	private String rental;
	//租金类型-付
	private String payType_pay;
	//租金类型-押
	private String payType_case;
	//出租方式
	private String rentType;
	//房屋配置情况
	private String configuration;
	
	public String getRentType() {
		return rentType;
	}
	public void setRentType(String rentType) {
		this.rentType = rentType;
	}
	
	public String getRental() {
		return rental;
	}
	public void setRental(String rental) {
		this.rental = rental;
	}
	public String getPayType_pay() {
		return payType_pay;
	}
	public void setPayType_pay(String payType_pay) {
		this.payType_pay = payType_pay;
	}
	public String getPayType_case() {
		return payType_case;
	}
	public void setPayType_case(String payType_case) {
		this.payType_case = payType_case;
	}
	public String getConfiguration() {
		return configuration;
	}
	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public String getHouseType() {
		return houseType;
	}
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	public String getFloorCur() {
		return floorCur;
	}
	public void setFloorCur(String floorCur) {
		this.floorCur = floorCur;
	}
	public String getFloorTotal() {
		return floorTotal;
	}
	public void setFloorTotal(String floorTotal) {
		this.floorTotal = floorTotal;
	}
	public String getHouseArea() {
		return houseArea;
	}
	public void setHouseArea(String houseArea) {
		this.houseArea = houseArea;
	}
	public String getHouseType_S() {
		return houseType_S;
	}
	public void setHouseType_S(String houseType_S) {
		this.houseType_S = houseType_S;
	}
	public String getHouseType_T() {
		return houseType_T;
	}
	public void setHouseType_T(String houseType_T) {
		this.houseType_T = houseType_T;
	}
	public String getHouseType_W() {
		return houseType_W;
	}
	public void setHouseType_W(String houseType_W) {
		this.houseType_W = houseType_W;
	}
	public String getPriceTaxe() {
		return priceTaxe;
	}
	public void setPriceTaxe(String priceTaxe) {
		this.priceTaxe = priceTaxe;
	}
	public String getPriceGet() {
		return priceGet;
	}
	public void setPriceGet(String priceGet) {
		this.priceGet = priceGet;
	}
	public String getBuildYear() {
		return buildYear;
	}
	public void setBuildYear(String buildYear) {
		this.buildYear = buildYear;
	}
	public String getFitmentInfo() {
		return fitmentInfo;
	}
	public void setFitmentInfo(String fitmentInfo) {
		this.fitmentInfo = fitmentInfo;
	}
	public String getOrientations() {
		return orientations;
	}
	public void setOrientations(String orientations) {
		this.orientations = orientations;
	}
	public String getHouseTitle() {
		return houseTitle;
	}
	public void setHouseTitle(String houseTitle) {
		this.houseTitle = houseTitle;
	}
	public String getHouseDescribe() {
		return houseDescribe;
	}
	public void setHouseDescribe(String houseDescribe) {
		this.houseDescribe = houseDescribe;
	}
}
