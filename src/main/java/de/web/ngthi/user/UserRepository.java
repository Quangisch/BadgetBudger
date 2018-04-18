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
		return getUser(name);
		
	}

	@Override
	public User delete(String name) throws UserNotFoundException {
		User u = getUser(name);
		String SQL = String.format("delete from %s where %s = ?", TABLE, USERNAME);
		jdbcTemplateObject.update(SQL, name);
		return u;
	}

	@Override
	public User getUser(String name) throws UserNotFoundException {
		User user = null;
		try {
			String SQL = String.format("select * from %s where %s = ?", TABLE, USERNAME);
			user = jdbcTemplateObject.queryForObject(SQL, new Object[] { name }, new UserMapper());
		} catch(EmptyResultDataAccessException e) {
			throw new UserNotFoundException(name);
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
	public User update(String oldName, String newName) throws UserNotFoundException, DuplicateKeyException {
		String SQL = String.format("update %s set %s = ? where %s = ?", TABLE, USERNAME, USERNAME);
		jdbcTemplateObject.update(SQL, newName, oldName);
		return getUser(newName);
	}


}
