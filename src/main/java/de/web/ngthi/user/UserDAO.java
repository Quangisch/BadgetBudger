package de.web.ngthi.user;

import java.util.List;

import javax.sql.DataSource;

public interface UserDAO {

	public void setDataSource(DataSource ds);
	
	public User create(String name);
	public User delete(String name);

	public User getUser(String name);
	public List<User> getUserList();
	
	public User update(String oldName, String newName);
	
}
