package com.spring.binar.challenge_5.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Setter @Getter
@NoArgsConstructor
@ToString
@Table(name = "studio", schema = "public")
public class Studio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studio_id", nullable = false, unique = true)
    private int studioId;

    @Column(name = "name")
    private String name;

//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", shape = JsonFormat.Shape.STRING)
//    @Column(name = "last_update")
//    private Date lastUpdate;

//    @OneToMany(mappedBy = "studio")
//    private List<Schedule> schedules;
}
