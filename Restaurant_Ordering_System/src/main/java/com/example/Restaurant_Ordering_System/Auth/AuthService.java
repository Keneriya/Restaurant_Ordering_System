package com.example.Restaurant_Ordering_System.Auth;

import com.example.Restaurant_Ordering_System.Config.JwtUtils;
import com.example.Restaurant_Ordering_System.DTO.AuthDtos;
import com.example.Restaurant_Ordering_System.Entity.Role;
import com.example.Restaurant_Ordering_System.Entity.User;
import com.example.Restaurant_Ordering_System.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public AuthService(AuthenticationManager authenticationManager, JwtUtils jwtUtils,
                       UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register public user — default to CUSTOMER
    public User register(AuthDtos.RegisterRequest request) {
        if (request.username() == null || request.password() == null || request.email() == null) {
            throw new IllegalArgumentException("username, email and password are required");
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username already taken");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        // Map role from request JSON
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole(Role.valueOf(String.valueOf(Role.CUSTOMER))); // default role
        }


        return userRepository.save(user);
    }

    // Login -> authenticate + generate token
    public AuthDtos.LoginResponse login(AuthDtos.LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        try {
            String token = jwtUtils.generateToken(authentication);
            User user = userRepository.findByUsername(request.username())
                    .orElseThrow(() -> new RuntimeException("User not found after authentication"));
            log.info("Login successful for user {}", request.username());
            return new AuthDtos.LoginResponse(token, user.getRole().name());
        } catch (Exception ex) {
            log.error("Failed to generate token / complete login for {}: {}", request.username(), ex.getMessage(), ex);
            throw ex; // let controller handle response (or wrap in custom exception)
        }     }
}
