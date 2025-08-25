package com.example.Restaurant_Ordering_System.Auth;

public class AuthDtos {
    public record LoginRequest(String email, String password) {}
    public record LoginResponse(String token) {}

    public record RegisterRequest(String name, String email, String password) {}

}
