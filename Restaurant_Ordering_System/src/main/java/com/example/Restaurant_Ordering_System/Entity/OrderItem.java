package com.example.Restaurant_Ordering_System.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(optional=false) @JoinColumn(name="order_id")
    private Order order;


    @ManyToOne(optional=false)
    private MenuItem menuItem;


    @Column(nullable=false) private int quantity;
    @Column(nullable=false, precision=10, scale=2) private BigDecimal unitPrice;
    @Column(nullable=false, precision=10, scale=2) private BigDecimal subtotal;
}
