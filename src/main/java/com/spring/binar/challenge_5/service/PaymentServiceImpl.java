package com.spring.binar.challenge_5.service;

import com.spring.binar.challenge_5.models.Payment;
import com.spring.binar.challenge_5.repos.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{

    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Page<Payment> findAll(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }

    @Override
    public Payment findById(int id) {
        var data = paymentRepository.findById(id);

        if(data.isEmpty()) throw new RuntimeException("No Payment found");
        return data.get();
    }

    @Override
    public Payment save(Payment payment) {
        System.out.println(payment.toString());
        if(payment.getAmount() <= 0 || payment.getStaff() == null || payment.getSchedule() == null || payment.getPaymentDate() == null)
            throw new RuntimeException("Invalid Payment");

        return paymentRepository.save(payment);
    }

    @Override
    public Payment update(Payment updatedPayment) {
        var result = paymentRepository.findById(updatedPayment.getPaymentId());

        if(result.isEmpty()) throw new RuntimeException("No Schedule found");

        var schedule = result.get();
        schedule.setSchedule(updatedPayment.getSchedule());
        schedule.setCostumer(updatedPayment.getCostumer());
        schedule.setStaff(updatedPayment.getStaff());
        schedule.setAmount(updatedPayment.getAmount());
        schedule.setPaymentDate(updatedPayment.getPaymentDate());

        return paymentRepository.save(schedule);
    }

    @Override
    public void delete(int id) {
        var result = paymentRepository.findById(id);
        if(result.isEmpty()) throw new RuntimeException("No Payment found");

        paymentRepository.delete(result.get());
    }
}
