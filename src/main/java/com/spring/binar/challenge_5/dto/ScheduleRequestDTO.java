package com.spring.binar.challenge_5.dto;

import com.spring.binar.challenge_5.models.Seat;
import lombok.Data;

import java.util.List;

@Data
public class ScheduleRequestDTO {
    private int scheduleId;
    private long fromDate;
    private long toDate;
    private int price;
    private int studioId;
    private int filmId;
    private List<Seat> seats;
}
