package de.web.ngthi.item;

import org.joda.time.DateTime;
import org.springframework.hateoas.ResourceSupport;

public class Item extends ResourceSupport {

	private int userID;
	private DateTime date;
	private int position;
	private String name;
	private double price;
	private String location;
	
	public Item() {
		
	}//JPA
	
	public Item(int userID, DateTime date, String name, double price, String location, int position) {
		this.userID = userID;
		this.date = date;
		this.name = name;
		this.price = price;
		this.location = location;
		this.position = position;
	}
	
	public DateTime getDate() {
		return date;
	}
	public void setDate(DateTime date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	public String toString() {
		return String.format("%s: %s \t%s \t%d \t%s", getClass().getSimpleName(), date.toString(), name, price, location);
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	
}
