package com.spring.binar.challenge_5.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Setter @Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name="costumer", schema = "public")
public class Costumer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "costumer_id", nullable = false, unique = true)
    private int costumerId;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", shape = JsonFormat.Shape.STRING)
    @Column(name = "last_update")
    private Date lastUpdate;
}
