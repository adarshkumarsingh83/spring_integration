
package com.adarsh.spring.jdbc.mapper;

import com.adarsh.spring.jdbc.entities.User;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getString("username"), rs.getString("password"), rs.getString("email"));
    }
}
