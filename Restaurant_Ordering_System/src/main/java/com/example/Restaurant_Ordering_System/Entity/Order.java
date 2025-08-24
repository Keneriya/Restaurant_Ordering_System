package com.example.Restaurant_Ordering_System.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(optional=false)
    private User customer;


    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private OrderStatus status = OrderStatus.PENDING;


    @Column(nullable=false, precision=10, scale=2)
    private BigDecimal total;


    private Instant createdAt = Instant.now();


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();
}
