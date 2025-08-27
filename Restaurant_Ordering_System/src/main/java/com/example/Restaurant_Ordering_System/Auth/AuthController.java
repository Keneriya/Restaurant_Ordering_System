package com.example.Restaurant_Ordering_System.Auth;

import com.example.Restaurant_Ordering_System.DTO.AuthDtos;
import com.example.Restaurant_Ordering_System.Entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Register new user (public)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthDtos.RegisterRequest request) {
        try {
            User created = authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed");
        }
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDtos.LoginRequest request) {
        try {
            AuthDtos.LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (org.springframework.security.core.AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed");
        }
    }
}
