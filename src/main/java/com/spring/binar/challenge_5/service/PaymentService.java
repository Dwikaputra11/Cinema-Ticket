package com.spring.binar.challenge_5.service;

import com.spring.binar.challenge_5.dto.PaymentRequestDTO;
import com.spring.binar.challenge_5.dto.PaymentResponseDTO;
import com.spring.binar.challenge_5.models.Payment;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentService {

    Page<Payment> findAll(Pageable pageable);

    List<Payment> findAll();

    Payment findById(int id);

    JasperPrint exportReport(int id) throws JRException, IOException;

    PaymentResponseDTO save(PaymentRequestDTO payment);

    Payment update(Payment updatedPayment);

    void delete(int id);
}
