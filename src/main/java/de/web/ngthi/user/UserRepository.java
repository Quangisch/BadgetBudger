package de.web.ngthi.user;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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
	public Optional<User> save(User user) {
		String name = user.getUsername();
		if(findByName(name).isPresent())
			throw new DuplicateUserException(name);
		String SQL = String.format("insert into %s (%s, %s) values (DEFAULT, ?)", TABLE, USERID, USERNAME);
		jdbcTemplateObject.update(SQL, name);
		return Optional.of(findByName(name).get());
		
	}

	@Override
	public Optional<User> delete(int userID) {
		Optional<User> u = findById(userID);
		if(!u.isPresent())
			throw new UserNotFoundException(userID);
		
		String SQL = String.format("delete from %s where %s = ?", TABLE, USERID);
		jdbcTemplateObject.update(SQL, userID);
		return u;
	}

	@Override
	public Optional<User> findById(int userID) {
		User user = null;
		try {
			String SQL = String.format("select * from %s where %s = ?", TABLE, USERID);
			user = jdbcTemplateObject.queryForObject(SQL, new Object[] { userID }, new UserMapper());
		} catch(EmptyResultDataAccessException e) {
			return Optional.empty();
		}
		
		return Optional.of(user);
	}
	
	@Override
	public List<User> findAll() {
		String SQL = String.format( "select * from User", TABLE);
		List<User> users = jdbcTemplateObject.query(SQL, new UserMapper());
		return users;
	}

	@Override
	public Optional<User> update(int userID, User userUpdated) {
		if(!findById(userID).isPresent())
			throw new UserNotFoundException(userID);
		if(findByName(userUpdated.getUsername()).isPresent())
			throw new DuplicateUserException(userUpdated.getUsername());
		
		String SQL = String.format("update %s set %s = ? where %s = ?", TABLE, USERNAME, USERID);
		jdbcTemplateObject.update(SQL, userUpdated.getUsername(), userID);
		return findById(userID);
	}
	
	@Override
	public Optional<User> findByName(String name) {
		try {
			String SQL = String.format("select * from %s where %s = ?", TABLE, USERNAME);
			return Optional.of(jdbcTemplateObject.queryForObject(SQL, new Object[] { name }, new UserMapper()));
		} catch(Exception e) {
			return Optional.empty();
		}
	}



}
