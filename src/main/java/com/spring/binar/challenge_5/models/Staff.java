package com.spring.binar.challenge_5.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="staff", schema = "public")
public class Staff implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id", nullable = false, unique = true)
    private int staffId;

    @Column(name = "name")
    private String name;

    @Column(name = "contact")
    private String contact;

//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", shape = JsonFormat.Shape.STRING)
//    @Column(name = "last_update")
//    private Date lastUpdate;
}
