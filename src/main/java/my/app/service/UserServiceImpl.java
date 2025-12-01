package my.app.service;

import my.app.model.User;
import my.app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
       return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);

    }
}
