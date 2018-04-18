package de.web.ngthi.user;

import java.util.List;

import javax.sql.DataSource;

public interface UserDAO {

	public void setDataSource(DataSource ds);
	
	public User create(String name);
	public User delete(int id);

	public User getUser(int id);
	public List<User> getUserList();
	
	public User update(int userID, String newName);
	
}
