package de.web.ngthi.item;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

public interface ItemDAO {

	public void setDataSource(DataSource ds);
	
	public List<Item> getItems(
			int userID, 
			Optional<Integer> year, 
			Optional<Integer> month, 
			Optional<Integer> day,
			Optional<String> name, 
			Optional<Double> price, 
			Optional<String> location,
			Optional<Integer> limit);
	
	public List<Item> getAllItems(int userID);
}
