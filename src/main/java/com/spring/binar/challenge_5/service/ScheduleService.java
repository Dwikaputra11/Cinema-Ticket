package com.spring.binar.challenge_5.service;

import com.spring.binar.challenge_5.dto.ScheduleRequestDTO;
import com.spring.binar.challenge_5.models.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScheduleService {
    Page<Schedule> findAll(Pageable pageable);

    Schedule findById(int id);

    ScheduleRequestDTO save(Schedule schedule);

    Schedule update(Schedule updatedSchedule);

    void delete(int id);
}
