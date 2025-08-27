package com.example.Restaurant_Ordering_System.Auth;

import com.example.Restaurant_Ordering_System.Entity.User;
import com.example.Restaurant_Ordering_System.Repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                   .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // convert Role enum to granted authorities
        String role = user.getRole() != null ? user.getRole().name() : "CUSTOMER";

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + role))
        );
    }
}
