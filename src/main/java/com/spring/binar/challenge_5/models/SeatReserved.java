package com.spring.binar.challenge_5.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "seat_reserved", schema = "public")
public class SeatReserved {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = Seat.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "seat_id", referencedColumnName = "seat_id")
    private int seatId;

    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id", nullable = false)
    @OneToOne(targetEntity = Payment.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Payment payment;

    @JoinColumn(name = "schedule_id", referencedColumnName = "schedule_id", nullable = false)
    @OneToOne(targetEntity = Schedule.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Schedule schedule;

}
