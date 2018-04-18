package de.web.ngthi.item;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

public class ItemMapper implements RowMapper<Item> {

	@Override
	public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
		DateTime date = new DateTime(rs.getInt("year"), rs.getInt("month"), rs.getInt("day"), 0, 0);

		Item i = new Item();
		i.setUserID(rs.getInt("userID"));
		i.setDate(date);
		i.setName(rs.getString("itemname"));
		i.setPrice(rs.getDouble("price"));
		i.setLocation(rs.getString("location"));
		i.setPosition(rs.getInt("position"));
		
		return i;
	}
}
