package com.example.Restaurant_Ordering_System.Service;

import com.example.Restaurant_Ordering_System.Entity.Role;
import com.example.Restaurant_Ordering_System.Entity.User;
import com.example.Restaurant_Ordering_System.Repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    // private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createCustomer(String name, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        // user.setPassword(encoder.encode(password));
        user.setRole(Role.CUSTOMER);
        return userRepository.save(user);
    }

    public User createAdmin(String name, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        // user.setPassword(encoder.encode(password));
        user.setRole(Role.ADMIN);
        return userRepository.save(user);
    }
}
