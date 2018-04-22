package de.web.ngthi.item;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Item extends ResourceSupport {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int itemID;
	private int userID;
	private int year, month, day;
	private String itemname;
	private double price;
	private String location;
	private int position;
	
	public Item() {
		
	}//JPA
	
	public Item(Item item) {
		this.userID = item.userID;
		this.year = item.year;
		this.month = item.month;
		this.day = item.day;
		this.itemname = item.itemname;
		this.price = item.price;
		this.location = item.location;
		this.position = item.position;
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
	
	@JsonIgnore
	public Object[] getFields() {
		return new Object[] { userID, year, month, day, itemname, price, location, position };
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}

	public int getMonth() {
		return month;
	}

	public void setDay(int day) {
		this.day = day;
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
