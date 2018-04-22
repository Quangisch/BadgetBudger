package de.web.ngthi.user;

import java.util.List;

import javax.sql.DataSource;

public interface IUserDAO {

	public void setDataSource(DataSource ds);
	
	public User save(User user);
	public User delete(int id);

	public User findById(int id);
	public User findByName(String userName);
	public List<User> findAll();
	
	public User update(int userID, User userUpdated);
	
	public boolean exists(int id);
	
}
