package my.app.service;

import my.app.model.User;
import my.app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl  implements AdminService{
    private final UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
