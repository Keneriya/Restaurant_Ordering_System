package com.example.Restaurant_Ordering_System.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false) private String name;
    @Column(length=1000) private String description;
    @Column(nullable=false, precision=10, scale=2) private BigDecimal price;
    private String imageUrl;
    @Column(nullable=false) private boolean active = true;
}
