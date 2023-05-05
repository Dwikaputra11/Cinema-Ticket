package com.spring.binar.challenge_5.service;

import com.spring.binar.challenge_5.models.Invoice;
import com.spring.binar.challenge_5.models.Payment;
import com.spring.binar.challenge_5.repos.PaymentRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

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
    public JasperPrint exportReport(int id) throws JRException, FileNotFoundException {
        String file = ResourceUtils.getFile("classpath:challenge5payment.jrxml").getAbsolutePath();
        JasperReport jasperReport = JasperCompileManager.compileReport(file);
        List<Payment> dataList = new ArrayList<>();
        Payment payment = findById(id);
        Invoice invoice = payment.toInvoice();
        dataList.add(payment);  
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(dataList);
        Map<String, Object> parameters = new HashMap();
        return JasperFillManager.fillReport(jasperReport, parameters, beanCollectionDataSource);
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
