package com.tubesbookwise.repository;

import com.tubesbookwise.model.User;
import java.util.List;

public interface UserRepository {
    User authenticate(String username, String rawPassword);

    int save(User user);
    User findById(int id);
    List<User> findAll();
    int update(User user);
    int deleteById(int id);
    User findByUsername(String username);
}
