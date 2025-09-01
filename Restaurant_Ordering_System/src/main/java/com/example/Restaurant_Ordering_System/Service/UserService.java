package com.example.Restaurant_Ordering_System.Service;

import com.example.Restaurant_Ordering_System.DTO.AuthDtos;
import com.example.Restaurant_Ordering_System.Entity.Role;
import com.example.Restaurant_Ordering_System.Entity.User;
import com.example.Restaurant_Ordering_System.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public final UserRepository userRepository;
    private User request;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User registerUser(AuthDtos.RegisterRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(encoder.encode(request.password()));

        // Set role based on request
        if ("ADMIN".equalsIgnoreCase(request.role())) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.CUSTOMER);
        }

        return userRepository.save(user);
    }

    public User createAdmin(String name, String email, String password) {
        User user = new User();
        user.setUsername(name);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));
        user.setRole(String.valueOf(Role.ADMIN));
        return userRepository.save(user);
    }


}