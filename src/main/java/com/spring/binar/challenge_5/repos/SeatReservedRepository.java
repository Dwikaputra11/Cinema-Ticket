package com.spring.binar.challenge_5.repos;

import com.spring.binar.challenge_5.models.SeatReserved;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatReservedRepository extends JpaRepository<SeatReserved, Integer> {
}
