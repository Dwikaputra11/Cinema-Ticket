package com.spring.binar.challenge_5.service;

import com.spring.binar.challenge_5.models.Payment;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {

    Page<Payment> findAll(Pageable pageable);

    Payment findById(int id);

    JasperPrint exportReport(int id) throws JRException, IOException;

    Payment save(Payment payment);

    Payment update(Payment updatedPayment);

    void delete(int id);
}
