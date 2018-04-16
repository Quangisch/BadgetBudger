package de.web.ngthi;

import java.util.List;

import javax.sql.DataSource;

public interface UserDAO {

	public void setDataSource(DataSource ds);
	
	public void create(String name);
	public void delete(String name);

	public User getUser(String name);
	public List<User> getUserList();
	
	public void update(String oldName, String newName);
	
}
