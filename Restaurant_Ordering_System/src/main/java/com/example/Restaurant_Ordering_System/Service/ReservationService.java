package com.example.Restaurant_Ordering_System.Service;

import com.example.Restaurant_Ordering_System.Entity.*;
import com.example.Restaurant_Ordering_System.Repositories.ReservationRepository;
import com.example.Restaurant_Ordering_System.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepo;
    private final UserRepository userRepo;

    public ReservationService(ReservationRepository reservationRepo, UserRepository userRepo) {
        this.reservationRepo = reservationRepo;
        this.userRepo = userRepo;
    }

    public Reservation create(Long customerId, int partySize, LocalDateTime when, String notes){
        User user = userRepo.findById(customerId).orElseThrow();
        Reservation r = new Reservation();
        r.setCustomer(user);
        r.setPartySize(partySize);
        r.setReservationTime(when);
        r.setNotes(notes);
        return reservationRepo.save(r);
    }

    public List<Reservation> myReservations(Long customerId){
        User user = userRepo.findById(customerId).orElseThrow();
        return reservationRepo.findByCustomer(user);
    }

    public List<Reservation> all(){
        return reservationRepo.findAll();
    }

    public Reservation updateStatus(Long id, ReservationStatus status){
        Reservation r = reservationRepo.findById(id).orElseThrow();
        r.setStatus(status);
        return reservationRepo.save(r);
    }
}
