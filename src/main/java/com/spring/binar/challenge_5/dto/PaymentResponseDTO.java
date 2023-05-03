package com.spring.binar.challenge_5.dto;

import com.spring.binar.challenge_5.models.Costumer;
import com.spring.binar.challenge_5.models.Schedule;
import com.spring.binar.challenge_5.models.Staff;
import lombok.Data;

import java.util.Date;
@Data
public class PaymentResponseDTO {
    private int paymentId;
    private Date paymentDate;
    private int amount;
    private Schedule schedule;
    private Costumer costumer;
    private Staff staff;
}


