package de.web.ngthi.item;

import org.joda.time.DateTime;
import org.springframework.hateoas.ResourceSupport;

public class Item extends ResourceSupport {

	private int userID;
	private int year, month, day;
	private String itemname;
	private double price;
	private String location;
	private int position;
	
	public Item() {
		
	}//JPA
	
	public Object[] getFields() {
		return new Object[] { userID, year, month, day, itemname, price, location, position };
	}
	
	public Item(int userID, int year, int month, int day, String name, double price, String location, int position) {
		this.userID = userID;
		this.year = year;
		this.month = month;
		this.day = day;
		this.itemname = name;
		this.price = price;
		this.location = location;
		this.position = position;
	}
	

	
	public int getYear() {
		return year;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getDay() {
		return day;
	}
	
	public String getName() {
		return itemname;
	}
	public void setName(String name) {
		this.itemname = name;
	}

	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	
}
