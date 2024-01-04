package com.example.db_projects.service;

import com.example.db_projects.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User registerUser(User newUser) {
        String sql = "INSERT INTO users (username, password, email, phone_number, company_name) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, newUser.getUsername(), newUser.getPassword(), newUser.getEmail(), newUser.getPhoneNumber(), newUser.getCompanyName());
        return newUser;
    }

    public User loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{username, password}, new UserRowMapper());
        if (!users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }
}


