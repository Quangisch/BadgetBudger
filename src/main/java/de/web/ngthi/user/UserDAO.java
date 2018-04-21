package de.web.ngthi.user;

import java.util.Collection;
import java.util.Optional;

import javax.sql.DataSource;

public interface UserDAO {

	public void setDataSource(DataSource ds);
	
	public Optional<User> save(User user);
	public Optional<User> delete(int id);

	public Optional<User> findById(int id);
	public Optional<User> findByName(String userName);
	public Collection<User> findAll();
	
	public Optional<User> update(int userID, User userUpdated);
	
}
