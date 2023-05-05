package com.spring.binar.challenge_5.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "seat_reserved", schema = "public")
@Entity
@Builder
public class SeatReserved implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = Seat.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name = "seat_id", referencedColumnName = "seat_id")
    private List<Seat> seat;

    @JoinColumn(name = "payment_id",referencedColumnName = "payment_id",nullable = false)
    @OneToOne(targetEntity = Payment.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Payment payment;

    @JoinColumn(name = "schedule_id",referencedColumnName = "schedule_id",nullable = false)
    @OneToOne(targetEntity = Schedule.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Schedule schedule;

}
