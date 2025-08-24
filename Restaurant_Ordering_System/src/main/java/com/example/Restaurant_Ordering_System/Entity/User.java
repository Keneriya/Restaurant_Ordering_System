package com.example.Restaurant_Ordering_System.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false) private String name;
    @Column(nullable=false, unique=true) private String email;
    @Column(nullable=false) private String password;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private Role role;
    private Instant createdAt = Instant.now();
}
