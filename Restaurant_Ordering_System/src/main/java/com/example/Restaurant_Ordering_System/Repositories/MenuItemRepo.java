package com.example.Restaurant_Ordering_System.Repositories;

import com.example.Restaurant_Ordering_System.Entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepo extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByActiveTrue();
}
