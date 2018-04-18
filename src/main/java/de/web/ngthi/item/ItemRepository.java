package de.web.ngthi.item;

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
public class ItemRepository implements ItemDAO {

	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	@Override
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public List<Item> getAllItems(int userID) {
		String SQL = "SELECT * FROM ITEM WHERE userID = ?";
		List<Item> items = jdbcTemplateObject.query(SQL, new Object[] { userID }, new ItemMapper());
		return items;
	}

	@Override
	public List<Item> getItems( int userID, 
			Optional<Integer> year, Optional<Integer> month, Optional<Integer> day,  
			Optional<String> name, Optional<Double> price, Optional<String> location,
			Optional<Integer> limit) {
		
		StringBuilder SQL = new StringBuilder("SELECT * FROM ITEM WHERE userID = ?");
		List<Object> params = new LinkedList<>();
		params.add(userID);
		
		if(year.isPresent()) {
			SQL.append(" AND YEAR = ?");
			params.add(year.get());
		} if(month.isPresent()) {
			SQL.append(" AND MONTH = ?");
			params.add(month);
		} if(day.isPresent()) {
			SQL.append(" AND DAY = ?");
			params.add(day.get());
		} if(name.isPresent()) {
			SQL.append(" AND ITEMNAME = ?");
			params.add(name.get());
		} if(price.isPresent()) {
			SQL.append(" AND PRICE = ?");
			params.add(price.get());
		} if(location.isPresent()) {
			SQL.append(" AND LOCATION = ?");
			params.add(location.get());
		}
		
		if(limit.isPresent()) {
			SQL.append(" LIMIT ?");
			params.add(limit.get());
		}
		
		
		List<Item> items = jdbcTemplateObject.query(SQL.toString(), params.toArray(), new ItemMapper());
		return items;
	}



}
