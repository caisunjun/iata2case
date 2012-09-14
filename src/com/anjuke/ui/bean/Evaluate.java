package com.anjuke.ui.bean;
/*
 * bean for sale or rent
 */
public class Evaluate {
	
	private String userName ;//普通用户
	private String communityName;//小区名称
	private String houseType_S;//房型-室
	private String houseType_T;	//房型-厅
	private String houseType_W;//房型-卫
	private String houseArea;//房屋面积
	private String floorCur;//当前楼层
	private String floorTotal;//总共楼层
	private String buildYear;//建筑时间
	private String houseType;//房屋类型
	private String fitmentInfo;//装修情况
	private String orientations;//房屋朝向
	private String price;//房屋单价
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
}
