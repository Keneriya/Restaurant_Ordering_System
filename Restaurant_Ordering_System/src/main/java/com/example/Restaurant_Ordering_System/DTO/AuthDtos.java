package com.example.Restaurant_Ordering_System.DTO;

public class AuthDtos {

    public record RegisterRequest(String username, String email, String password, String role) {
    }

    public record LoginRequest(String username, String password) {}

    public record LoginResponse(String token, String role) {}
}
