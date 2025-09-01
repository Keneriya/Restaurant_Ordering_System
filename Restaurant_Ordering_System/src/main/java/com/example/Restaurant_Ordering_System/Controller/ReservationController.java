package com.example.Restaurant_Ordering_System.Controller;

import com.example.Restaurant_Ordering_System.Entity.Reservation;
import com.example.Restaurant_Ordering_System.Entity.ReservationStatus;
import com.example.Restaurant_Ordering_System.Service.ReservationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    public ReservationController(ReservationService reservationService) { this.reservationService = reservationService; }

    // CUSTOMER: create
    @PostMapping
    public Reservation create(
            @RequestParam Long customerId,
            @RequestParam int partySize,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime when,
            @RequestParam(required = false) String notes
    ){
        return reservationService.create(customerId, partySize, when, notes);
    }

    // CUSTOMER: mine
    @GetMapping("/mine")
    public List<Reservation> mine(@RequestParam Long customerId){
        return reservationService.myReservations(customerId);
    }

    // STAFF/ADMIN: all
    @GetMapping
    public List<Reservation> all(){
        return reservationService.all();
    }

    // STAFF/ADMIN: status change
    @PatchMapping("/{id}/status")
    public Reservation updateStatus(@PathVariable Long id, @RequestParam String status){
        return reservationService.updateStatus(id, ReservationStatus.valueOf(status.toUpperCase()));
    }
}
