package de.web.ngthi;

import java.util.List;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Repository
public class ItemDAO_JDBC implements ItemDAO {

	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	@Override
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Item> getItems(int userID) {
		String SQL = "SELECT * FROM ITEM WHERE userID = ?";
		List<Item> items = jdbcTemplateObject.query(SQL, new Object[] { userID }, new ItemMapper());
		return items;
	}

	@Override
	public List<Item> getItems(String username) {
		String SQL = "SELECT * FROM ITEM WHERE (SELECT userID FROM USER WHERE username = ?)";
		List<Item> items = jdbcTemplateObject.query(SQL, new Object[] { username }, new ItemMapper());
		return items;
	}

	@Override
	public List<Item> getItems(String username, int year, int month, int day) {
		String SQL = "SELECT * FROM ITEM WHERE username = ? AND year = ? AND month = ? AND day = ?";
		List<Item> items = jdbcTemplateObject.query(SQL, new Object[] { username, year, month, day }, new ItemMapper());
		return items;
	}

	@Override
	public List<Item> getItems(User user, DateTime date) {
		return getItems(user.getName(), date.getYear(), date.getMonthOfYear(), date.getDayOfMonth());
	}

}
