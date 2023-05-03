package com.spring.binar.challenge_5.repos;

import com.spring.binar.challenge_5.models.Seat;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface SeatRepository extends JpaRepository <Seat, Integer>{
}
