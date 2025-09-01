package com.example.Restaurant_Ordering_System.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User customer;

    private int partySize;
    private LocalDateTime reservationTime;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status = ReservationStatus.PENDING;

    private String notes;

    public void setCustomer(User user) {
    }

    public void setPartySize(int partySize) {
    }

    public void setReservationTime(LocalDateTime when) {
    }

    public void setNotes(String notes) {
    }

    public void setStatus(ReservationStatus status) {
    }

    // getters/setters ...
}
