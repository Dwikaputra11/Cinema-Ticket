package com.spring.binar.challenge_5.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "payment", schema = "public")
@Entity
@AllArgsConstructor
@ToString
@Builder
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false, unique = true)
    private int paymentId;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "amount")
    private int amount;

    @OneToOne(targetEntity = Schedule.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", referencedColumnName = "schedule_id")
    @ToString.Exclude
    private Schedule schedule;

    @OneToOne(targetEntity = Costumer.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "costumer_id", referencedColumnName = "costumer_id")
    @ToString.Exclude
    private Costumer costumer;

    @OneToOne(targetEntity = Staff.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", referencedColumnName = "staff_id")
    @ToString.Exclude
    private Staff staff;

    public Invoice toInvoice(){
        return Invoice.builder()
        .costumerId(this.costumer.getCostumerId())
        .paymentId(this.paymentId)
        .paymentDate(this.paymentDate)
        .amount(amount)
        .fromDate(this.schedule.getFromDate())
        .toDate(this.schedule.getToDate())
        .title(this.schedule.getFilm().getTitle())
        .studioName(this.schedule.getStudio().getName())
        .username(this.costumer.getUsername())
        .build();
    }
}
