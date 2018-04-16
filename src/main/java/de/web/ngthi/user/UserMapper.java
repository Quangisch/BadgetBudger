package de.web.ngthi.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User u = new User(rs.getInt("userID"), rs.getString("username"));
		return u;
	}

	
	
}
