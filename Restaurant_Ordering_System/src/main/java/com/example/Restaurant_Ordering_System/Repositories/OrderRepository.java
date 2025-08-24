package com.example.Restaurant_Ordering_System.Repositories;

import com.example.Restaurant_Ordering_System.Entity.Order;
import com.example.Restaurant_Ordering_System.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer(User customer);
}
