package com.spring.binar.challenge_5.dto;

import lombok.Data;

import java.util.Date;
@Data
public class PaymentDTO {
    int paymentId;
    Date paymentDate;
    int amount;
    int scheduleId;
    int costumerId;
    int staffId;
}
