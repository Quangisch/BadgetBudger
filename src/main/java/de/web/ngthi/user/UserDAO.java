package de.web.ngthi.user;

import java.util.Collection;
import java.util.Optional;

import javax.sql.DataSource;

public interface UserDAO {

	public void setDataSource(DataSource ds);
	
	public User save(User user);
	public User delete(int id);

	public Optional<User> findById(int id);
	public Optional<User> findByName(String userName);
	public Collection<User> findAll();
	
	public User update(int userID, User userUpdated);
	
}
