package com.spring.binar.challenge_5.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.io.Serializable;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "seat",schema = "public")
public class Seat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // use identity when data type in db is serial
    @Column(name = "seat_id", nullable = false, unique = true)
    private int seatId;

    @Column(name = "row")
    private char row;

    @Column(name = "number")
    private byte number;

    @OneToOne(targetEntity = Studio.class,cascade = CascadeType.MERGE)
    @JoinColumn(name = "studio_id", referencedColumnName = "studio_id", nullable = false)
    private Studio studio;
}
