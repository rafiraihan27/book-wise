package com.tubesbookwise.repository;

import com.tubesbookwise.exception.ApiException;
import com.tubesbookwise.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));  // Password is stored as hashed, so no need to encode again
            user.setEmail(rs.getString("email"));
            user.setRole(User.Role.valueOf(rs.getString("role"))); // Convert String to Role enum
            return user;
        }
    }

    @Override
    public User authenticate(String username, String rawPassword) {
        return null;
    }

    @Override
    public int save(User user) {
        String sql = "INSERT INTO users (name, username, password, email, role) VALUES (?, ?, ?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, user.getName(), user.getUsername(), user.getPassword(), user.getEmail(), user.getRole().toString());
        } catch (Exception e) {
            throw new ApiException("Failed to save user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
        } catch (Exception e) {
            throw new ApiException("Failed to fetch user with ID " + id + ": " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        try {
            return jdbcTemplate.query(sql, new UserRowMapper());
        } catch (Exception e) {
            throw new ApiException("Failed to fetch all users: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public int update(User user) {
        String sql = "UPDATE users SET name = ?, username = ?, password = ?, email = ?, role = ? WHERE id = ?";
        try {
            int rows = jdbcTemplate.update(sql, user.getName(), user.getUsername(), user.getPassword(), user.getEmail(), user.getRole().toString(), user.getId());
            if (rows == 0) {
                throw new ApiException("No user found to update with ID " + user.getId(), HttpStatus.NOT_FOUND);
            }
            return rows;
        } catch (Exception e) {
            throw new ApiException("Failed to update user with ID " + user.getId() + ": " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public int deleteById(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try {
            int rows = jdbcTemplate.update(sql, id);
            if (rows == 0) {
                throw new ApiException("No user found to delete with ID " + id, HttpStatus.NOT_FOUND);
            }
            return rows;
        } catch (Exception e) {
            throw new ApiException("Failed to delete user with ID " + id + ": " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), username);
        } catch (Exception e) {
            throw new ApiException("Failed to fetch user with username '" + username + "': " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
