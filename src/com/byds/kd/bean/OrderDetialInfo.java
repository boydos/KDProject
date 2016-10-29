package com.byds.kd.bean;

public class OrderDetialInfo {
	public OrderDetialInfo() {
		
	}
	public OrderDetialInfo(String date,String location) {
		this.date =date;
		this.location =location;
	}
	private String date;
	private String location;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
