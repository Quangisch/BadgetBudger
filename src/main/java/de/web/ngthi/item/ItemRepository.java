package de.web.ngthi.item;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Repository
public class ItemRepository implements IItemDAO {

	/*
	 * private int userID;
	private DateTime date;
	private int position;
	private String name;
	private double price;
	private String location;
	 */
	
	private final String TABLE = "ITEM"; 
	
	
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	@Override
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Item> query( int userID, 
			Optional<Integer> year, Optional<Integer> month, Optional<Integer> day,  
			Optional<String> name, Optional<Double> price, Optional<String> location,
			Optional<Integer> limit) {
		
		StringBuilder SQL = new StringBuilder("SELECT * FROM ITEM WHERE userID = ?");
		List<Object> params = new LinkedList<>();
		params.add(userID);
		
		if(year.isPresent()) {
			SQL.append(" AND " + ItemTable.YEAR + " = ?");
			params.add(year.get());
		} if(month.isPresent()) {
			SQL.append(" AND " + ItemTable.MONTH + " = ?");
			params.add(month);
		} if(day.isPresent()) {
			SQL.append(" AND " + ItemTable.DAY + " = ?");
			params.add(day.get());
		} if(name.isPresent()) {
			SQL.append(" AND " + ItemTable.ITEMNAME + " = ?");
			params.add(name.get());
		} if(price.isPresent()) {
			SQL.append(" AND " + ItemTable.PRICE + " = ?");
			params.add(price.get());
		} if(location.isPresent()) {
			SQL.append(" AND " + ItemTable.LOCATION + " = ?");
			params.add(location.get());
		}
		
		if(limit.isPresent()) {
			SQL.append(" LIMIT ?");
			params.add(limit.get());
		}
		
		
		List<Item> items = jdbcTemplateObject.query(SQL.toString(), params.toArray(), new ItemMapper());
		return items;
	}
	

	@Override
	public Item save(Item item) {
		return save(Collections.singletonList(item)).iterator().next();
	}
	
	@Override
	public List<Item> save(Iterable<Item> items) {
		List<Object[]> params = new LinkedList<>();
		StringBuilder SQL = new StringBuilder("INSERT INTO " + TABLE);
		StringBuilder paramNames = new StringBuilder(" (");
		StringBuilder placeholder = new StringBuilder(" (");
		for(int i = 1; i < ItemTable.values().length; i++) {
			paramNames.append(ItemTable.values()[i]);
			placeholder.append("?");
			if(i < ItemTable.values().length - 1) {
				paramNames.append(", ");
				placeholder.append(", ");
			} else {
				placeholder.append(")");
				paramNames.append(")");
			}
		}
		
		for(Item i : items)
			params.add(i.getFields());
		
		SQL.append(paramNames).append(" values ").append(placeholder);
		jdbcTemplateObject.batchUpdate(SQL.toString(), params);
		
		List<Item> saved = new LinkedList<>();
		items.forEach(u -> saved.add(u));
		return saved;
	}

	@Override
	public void update(int itemID, Item modItem) {
		if(!exsists(itemID))
			throw new ItemNotFoundException(itemID);
		
		List<Object> params = new LinkedList<>();
		StringBuilder SQL = new StringBuilder(String.format("update %s set ", TABLE));
		for(int i = 1; i < ItemTable.values().length; i++) {
			SQL.append(ItemTable.values()[i] +  " = ? ");
			params.add(modItem.getFields()[i - 1]);
			if(i < ItemTable.values().length - 1)
				SQL.append(", ");
		}
		
		SQL.append(String.format(" where %s = ? ", "ITEMID"));
		params.add(itemID);
		jdbcTemplateObject.update(SQL.toString(), params.toArray());
	}

	@Override
	public boolean exsists(int itemID) {
		String SQL = "SELECT * FROM ITEM WHERE itemID = ?";
		return !jdbcTemplateObject.query(SQL, new Object[] {itemID}, new ItemMapper()).isEmpty();
	}

	@Override
	public Item findByItemID(int itemID) {
		String SQL = "SELECT * FROM ITEM WHERE itemID = ?";
		List<Item> items = jdbcTemplateObject.query(SQL, new Object[] { itemID }, new ItemMapper());
		return items.get(0);
	}

	@Override
	public List<Item> findByUserID(int userID) {
		String SQL = "SELECT * FROM ITEM WHERE userID = ?";
		List<Item> items = jdbcTemplateObject.query(SQL, new Object[] { userID }, new ItemMapper());
		return items;
	}


	@Override
	public Item remove(int itemID) {
		List<Item> items = remove(Collections.singletonList(itemID));
		if(items.isEmpty())
			throw new ItemNotFoundException(itemID);
		return items.get(0);
	}
	
	@Override
	public List<Item> remove(Iterable<Integer> itemIDs) {
		List<Item> items = new LinkedList<>();
		
		for(int itemID : itemIDs) {
			String SQL = "SELECT * FROM ITEM WHERE itemID = ?";
			items.addAll(jdbcTemplateObject.query(SQL, new Object[] {itemID}, new ItemMapper()));
			
			SQL = String.format("delete from %s where %s = ?", TABLE, "ITEMID");
			jdbcTemplateObject.update(SQL, itemID);
		}
		
		return items;
	}


	@Override
	public List<Item> removeAll(int userID) {
		List<Item> remove = findByUserID(userID);
		String SQL = String.format("delete from %s where %s = ?", TABLE, "userID");
		jdbcTemplateObject.update(SQL, userID);
		return remove;
	}

	@Override
	public long count(int userID) {
		String SQL = "SELECT COUNT(*) FROM ITEM WHERE userID = ?";
		return jdbcTemplateObject.queryForObject(SQL, new Object[] { userID }, Integer.class);
	}

}
