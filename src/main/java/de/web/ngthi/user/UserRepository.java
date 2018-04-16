package de.web.ngthi.user;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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
	public void create(String name) throws org.springframework.dao.DuplicateKeyException {
		String SQL = String.format("insert into %s (%s, %s) values (DEFAULT, ?)", TABLE, USERID, USERNAME);
		jdbcTemplateObject.update(SQL, name);
	}

	@Override
	public void delete(String name) {
		String SQL = String.format("delete from %s where %s = ?", TABLE, USERNAME);
		jdbcTemplateObject.update(SQL, name);
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
		String SQL = String.format("update %s set %s = ? where %s = ?", TABLE, USERNAME, USERNAME);
		jdbcTemplateObject.update(SQL, newName, oldName);
	}
	


}
