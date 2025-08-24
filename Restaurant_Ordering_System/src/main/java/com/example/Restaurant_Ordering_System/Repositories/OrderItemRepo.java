package com.example.Restaurant_Ordering_System.Repositories;

import com.example.Restaurant_Ordering_System.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
}
