package com.spring.binar.challenge_5.repos;

import com.spring.binar.challenge_5.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
}
