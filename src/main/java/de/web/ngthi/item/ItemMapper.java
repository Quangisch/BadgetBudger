package de.web.ngthi.item;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ItemMapper implements RowMapper<Item> {

	@Override
	public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
		Item i = new Item();
		i.setUserID(rs.getInt("userID"));
		i.setUserID(rs.getInt("year"));
		i.setUserID(rs.getInt("month"));
		i.setUserID(rs.getInt("day"));
		i.setName(rs.getString("itemname"));
		i.setPrice(rs.getDouble("price"));
		i.setLocation(rs.getString("location"));
		i.setPosition(rs.getInt("position"));
		
		return i;
	}
}
