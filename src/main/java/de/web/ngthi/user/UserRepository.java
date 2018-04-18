package de.web.ngthi.user;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Repository
public class UserRepository implements UserDAO {

	private JdbcTemplate jdbcTemplateObject;
	private final String TABLE = "User";
	private final String USERNAME = "username";
	private final String USERID = "userID";
	
	@Autowired
	@Override
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public User create(String name) throws DuplicateKeyException {
		String SQL = String.format("insert into %s (%s, %s) values (DEFAULT, ?)", TABLE, USERID, USERNAME);
		jdbcTemplateObject.update(SQL, name);
		return getUserByName(name);
		
	}

	@Override
	public User delete(int userID) throws UserNotFoundException {
		User u = getUser(userID);
		String SQL = String.format("delete from %s where %s = ?", TABLE, USERID);
		jdbcTemplateObject.update(SQL, userID);
		return u;
	}

	@Override
	public User getUser(int userID) throws UserNotFoundException {
		User user = null;
		try {
			String SQL = String.format("select * from %s where %s = ?", TABLE, USERID);
			user = jdbcTemplateObject.queryForObject(SQL, new Object[] { userID }, new UserMapper());
		} catch(EmptyResultDataAccessException e) {
			throw new UserNotFoundException(userID);
		}
		
		return user;
	}

	@Override
	public List<User> getUserList() {
		String SQL = String.format( "select * from User", TABLE);
		List<User> users = jdbcTemplateObject.query(SQL, new UserMapper());
		return users;
	}

	@Override
	public User update(int userID, String newName) throws UserNotFoundException, DuplicateKeyException {
		String SQL = String.format("update %s set %s = ? where %s = ?", TABLE, USERNAME, USERID);
		jdbcTemplateObject.update(SQL, newName, userID);
		return getUser(userID);
	}
	
	private User getUserByName(String name) {
		String SQL = String.format("select * from %s where %s = ?", TABLE, USERNAME);
		return jdbcTemplateObject.queryForObject(SQL, new Object[] { name }, new UserMapper());
	}


}
