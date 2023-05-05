package com.spring.binar.challenge_5.dto;

import com.spring.binar.challenge_5.models.Film;
import com.spring.binar.challenge_5.models.Studio;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScheduleResponseDTO {
    private int scheduleId;
    private long fromDate;
    private long toDate;
    private int price;
    private StudioSeatDTO studio;
    private Film film;
}
