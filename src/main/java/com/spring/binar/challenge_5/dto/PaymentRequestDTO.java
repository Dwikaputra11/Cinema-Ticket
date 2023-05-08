package com.spring.binar.challenge_5.dto;

import com.spring.binar.challenge_5.models.Payment;
import com.spring.binar.challenge_5.models.Seat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class PaymentRequestDTO {
    int paymentId;
    Date paymentDate;
    int amount;
    int scheduleId;
    int costumerId;
    int staffId;
    List<Integer> seatIds;

//    public Payment toPayment(Payment payment){
//        return payment.builder()
//                .
//    }
}
