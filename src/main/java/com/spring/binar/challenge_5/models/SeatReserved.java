package com.spring.binar.challenge_5.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "seat_reserved", schema = "public")
public class SeatReserved {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = Seat.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "seat_id", referencedColumnName = "seat_id")
    private int seatId;

    @Column(name = "payment_id", nullable = false)
    @OneToOne(targetEntity = Payment.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private int paymentId;

    @Column(name = "studio_id", nullable = false)
    @OneToOne(targetEntity = Studio.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private int studioId;

}
