package de.web.ngthi.user;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Repository
public class UserRepository implements IUserDAO {

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
	public User save(User user) {
		String name = user.getUsername();
		if(findByName(name) != null)
			throw new DuplicateUserException(name);
		
		String SQL = String.format("insert into %s (%s, %s) values (DEFAULT, ?)", TABLE, USERID, USERNAME);
		jdbcTemplateObject.update(SQL, name);
		return findByName(name);
	}

	@Override
	public User delete(int userID) {
		User u = findById(userID);
		String SQL = String.format("delete from %s where %s = ?", TABLE, USERID);
		jdbcTemplateObject.update(SQL, userID);
		return u;
	}

	@Override
	public User findById(int userID) {
		if(exists(userID)) {
			String SQL = String.format("select * from %s where %s = ?", TABLE, USERID);
			return jdbcTemplateObject.queryForObject(SQL, new Object[] { userID }, new UserMapper());
		} else {
			throw new UserNotFoundException(userID);
		}
	}
	
	@Override
	public User findByName(String name) {
		try {
			String SQL = String.format("select * from %s where %s = ?", TABLE, USERNAME);
			return jdbcTemplateObject.queryForObject(SQL, new Object[] { name }, new UserMapper());
		} catch(Exception e) {
			return null;
		}
		
	}
	
	@Override
	public List<User> findAll() {
		String SQL = String.format( "select * from User", TABLE);
		List<User> users = jdbcTemplateObject.query(SQL, new UserMapper());
		return users;
	}

	@Override
	public User update(int userID, User userUpdated) {
		if(!exists(userID))
			throw new UserNotFoundException(userID);
		
		String SQL = String.format("update %s set %s = ? where %s = ?", TABLE, USERNAME, USERID);
		jdbcTemplateObject.update(SQL, userUpdated.getUsername(), userID);
		return findById(userID);
	}

	@Override
	public boolean exists(int id) {
		User user = null;
		try {
			String SQL = String.format("select * from %s where %s = ?", TABLE, USERID);
			user = jdbcTemplateObject.queryForObject(SQL, new Object[] { id }, new UserMapper());
			
			return user != null;
		} catch(Exception e) {
			return false;
		}
	}

}
