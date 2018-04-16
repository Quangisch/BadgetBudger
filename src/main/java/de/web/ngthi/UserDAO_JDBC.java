package de.web.ngthi;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Repository
public class UserDAO_JDBC implements UserDAO {

	private JdbcTemplate jdbcTemplateObject;
	private final String TABLE = "User";
	private final String USERNAME = "username";
	
	@Autowired
	@Override
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void create(String name) throws org.springframework.dao.DuplicateKeyException {
		if(hasUser(name))
			throw new DuplicateUserException(name);
		
		String SQL = String.format("insert into %s (%s) values (?)", TABLE, USERNAME);
		jdbcTemplateObject.update(SQL, name);
	}

	@Override
	public void delete(String name) {
		if(hasUser(name)) {
			String SQL = String.format("delete from %s where %s = ?", TABLE, USERNAME);
			jdbcTemplateObject.update(SQL, name);
		} else {
			throw new UserNotFoundException(name);
		}
		
	}

	@Override
	public User getUser(String name) {
		String SQL = String.format("select * from %s where %s = ?", TABLE, USERNAME);
		User user = jdbcTemplateObject.queryForObject(SQL, new Object[] { name }, new UserMapper());
		
		if(user == null)
			throw new UserNotFoundException(name);
		
		return user;
	}

	@Override
	public List<User> getUserList() {
		String SQL = String.format( "select * from User", TABLE);
		List<User> users = jdbcTemplateObject.query(SQL, new UserMapper());
		return users;
	}

	@Override
	public void update(String oldName, String newName) {
		if(!hasUser(oldName))
			throw new UserNotFoundException(oldName);
		if(hasUser(newName))
			throw new DuplicateUserException(newName);
		
		String SQL = String.format("update %s set %s = ? where userID = ?", TABLE, USERNAME);
		jdbcTemplateObject.update(SQL, newName, oldName);
	}
	
	private boolean hasUser(String username) {
		return getUser(username) != null;
	}
	

}
