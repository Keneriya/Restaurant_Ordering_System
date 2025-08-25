package com.example.Restaurant_Ordering_System.Controller;

import com.example.Restaurant_Ordering_System.DTO.AuthDtos;
import com.example.Restaurant_Ordering_System.Entity.User;
import com.example.Restaurant_Ordering_System.Service.AuthService;
import com.example.Restaurant_Ordering_System.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public AuthController(UserService userService, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.userService = userService;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public String register(@RequestBody AuthDtos.RegisterRequest req) {
        User user = userService.createCustomer(req.name(), req.email(), req.password());
        return "Customer registered: " + user.getEmail();
    }

    @PostMapping("/login")
    public AuthDtos.LoginResponse login(@RequestBody AuthDtos.LoginRequest req) {
        User user = userService.userRepository.findByEmail(req.email())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!encoder.matches(req.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtils.generateToken(user);
        return new AuthDtos.LoginResponse(token);
    }
}