package com.example.Restaurant_Ordering_System.Repositories;

import com.example.Restaurant_Ordering_System.Entity.Reservation;
import com.example.Restaurant_Ordering_System.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByCustomer(User user);
}
