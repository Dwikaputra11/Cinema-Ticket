package com.spring.binar.challenge_5.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Setter @Getter
@NoArgsConstructor
@Table(name = "schedule", schema = "public")
@Entity
@AllArgsConstructor
@ToString
public class Schedule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id", nullable = false, unique = true)
    private int scheduleId;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "price")
    private int price;

    @OneToOne(targetEntity = Studio.class,cascade = CascadeType.MERGE)
    @JoinColumn(name = "studio_id_fk", referencedColumnName = "studio_id", nullable = false)
    private Studio studio;

    @OneToOne(targetEntity = Film.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "film_id_fk", referencedColumnName = "film_id", nullable = false)
    private Film film;
}
