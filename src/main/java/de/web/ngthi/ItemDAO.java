package de.web.ngthi;

import java.util.List;

import javax.sql.DataSource;

import org.joda.time.DateTime;

public interface ItemDAO {

	public void setDataSource(DataSource ds);
	
	public List<Item> getItems(String username);
	public List<Item> getItems(int userID);
	
	public List<Item> getItems(String user, int year, int month, int day);
	public List<Item> getItems(User user, DateTime date);
	
}
