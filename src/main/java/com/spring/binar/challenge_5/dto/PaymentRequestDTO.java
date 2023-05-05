package com.spring.binar.challenge_5.dto;

import com.spring.binar.challenge_5.models.Seat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PaymentRequestDTO {
    int paymentId;
    Date paymentDate;
    int amount;
    int scheduleId;
    int costumerId;
    int staffId;
//    List<Seat> seats;
}
