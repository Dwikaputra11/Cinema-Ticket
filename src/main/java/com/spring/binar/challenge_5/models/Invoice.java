package com.spring.binar.challenge_5.models;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Invoice {
    private int costumerId;
    private int paymentId;
    private Date paymentDate;
    private int amount;
    private 
    
}
