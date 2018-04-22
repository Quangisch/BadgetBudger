package de.web.ngthi.item;

import java.util.Optional;

import javax.sql.DataSource;

public interface IItemDAO {

	public void setDataSource(DataSource ds);
	
	public Iterable<Item> query(
			int userID, 
			Optional<Integer> year, 
			Optional<Integer> month, 
			Optional<Integer> day,
			Optional<String> name, 
			Optional<Double> price, 
			Optional<String> location,
			Optional<Integer> limit);
	
	public Item findByItemID(int itemID);
	public Iterable<Item> findByUserID(int userID);
	
	public Item save(Item item);
	public Iterable<Item> save(Iterable<Item> items);
	public void update(int itemID, Item modItem);
	
	
	public Item remove(int itemIDs);
	public Iterable<Item> remove(Iterable<Integer> itemIDs);
	public Iterable<Item> removeAll(int userID);
	
	public boolean exsists(int itemID);
	public long count(int usedID);
}
