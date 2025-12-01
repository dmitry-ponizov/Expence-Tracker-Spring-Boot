package my.app.service;

import my.app.model.User;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    User findByUsername(String username);
    Optional<User> findUserById(Long id);
}
