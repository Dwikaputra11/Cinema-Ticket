package com.spring.binar.challenge_5.service;

import com.spring.binar.challenge_5.dto.PaymentDTO;
import com.spring.binar.challenge_5.models.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {

    Page<Payment> findAll(Pageable pageable);

    Payment findById(int id);

    Payment save(Payment payment);

    Payment update(Payment updatedPayment);

    void delete(int id);
}
