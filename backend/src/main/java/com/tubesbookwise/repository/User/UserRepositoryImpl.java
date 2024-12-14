package com.tubesbookwise.repository.User;

import com.tubesbookwise.exception.ApiException;
import com.tubesbookwise.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

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
            user.setId(rs.getString("id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            user.setRole(User.Role.valueOf(rs.getString("role").toLowerCase())); // Match enum values
            user.setPhone(rs.getString("phone"));
            user.setNim(rs.getString("nim"));
            user.setNip(rs.getString("nip"));
            user.setYear(rs.getString("year"));
            return user;
        }
    }

    @Override
    public User authenticate(String username, String rawPassword) {
        return null;
    }

    @Override
    public User save(User user) {
        String generatedId = UUID.randomUUID().toString(); // Generate UUID in Java
        String sql = "INSERT INTO users (id, name, email, password, role, phone, nim, nip, year) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, generatedId); // Use the generated UUID
                ps.setString(2, user.getName());
                ps.setString(3, user.getEmail());
                ps.setString(4, user.getPassword());
                ps.setString(5, user.getRole().toString());
                ps.setString(6, user.getPhone());
                ps.setString(7, user.getNim());
                ps.setString(8, user.getNip());
                ps.setString(9, user.getYear());
                return ps;
            });

            return findById(generatedId); // Fetch by generated UUID
        } catch (Exception e) {
            throw new ApiException("Failed to save user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    public User findById(String id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
        } catch (Exception e) {
            throw new ApiException("Failed to fetch user with ID " + id + ": " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<User> findAll(String role) {
        String sql;
        Object[] params;

        if (role != null && !role.isEmpty()) {
            sql = "SELECT * FROM users WHERE role = ?";
            params = new Object[]{role};
        } else {
            sql = "SELECT * FROM users";
            params = new Object[]{};
        }

        try {
            return jdbcTemplate.query(sql, new UserRowMapper(), params);
        } catch (Exception e) {
            throw new ApiException("Failed to fetch users: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, password = ?, name = ?, role = ?, phone = ?, nim = ?, nip = ?, year = ? WHERE id = ?";
        try {
            int rows = jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword(),  user.getName(), user.getRole().toString(),
                    user.getPhone(), user.getNim(), user.getNip(), user.getYear(), user.getId());
            if (rows == 0) {
                throw new ApiException("No user found to update with ID " + user.getId(), HttpStatus.NOT_FOUND);
            }
            return findById(user.getId());
        } catch (Exception e) {
            throw new ApiException("Failed to update user with ID " + user.getId() + ": " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public int deleteById(String id) {
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
}
