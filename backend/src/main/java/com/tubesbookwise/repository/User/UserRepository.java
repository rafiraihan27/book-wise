package com.tubesbookwise.repository.User;

import com.tubesbookwise.model.User;
import java.util.List;

public interface UserRepository {
    User authenticate(String username, String rawPassword);
    User save(User user);
    User findById(String id);
    List<User> findAll(String role);
    User update(User user);
    int deleteById(String id);
}
