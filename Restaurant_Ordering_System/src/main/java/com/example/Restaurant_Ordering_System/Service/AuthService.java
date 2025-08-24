package com.example.Restaurant_Ordering_System.Service;

import com.example.Restaurant_Ordering_System.Entity.User;
import com.example.Restaurant_Ordering_System.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    // Handle user login
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new RuntimeException("Invalid credentials");
//        }
//
//        return "Login successful for user: " + user.getName();
//    }

    // Handle user registration
    public String register(User user){
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new RuntimeException("Email already in use");
            }

            // Encrypt password before saving
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userRepository.save(user);
//
//        return "User registered successfully: " + user.getName();
        }
}
