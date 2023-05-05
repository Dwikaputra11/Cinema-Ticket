package com.spring.binar.challenge_5.service.implementation;

import com.spring.binar.challenge_5.dto.PaymentRequestDTO;
import com.spring.binar.challenge_5.dto.PaymentResponseDTO;
import com.spring.binar.challenge_5.models.Invoice;
import com.spring.binar.challenge_5.models.Payment;
import com.spring.binar.challenge_5.models.SeatReserved;
import com.spring.binar.challenge_5.repos.*;
import com.spring.binar.challenge_5.repos.CostumerRepository;
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

import com.spring.binar.challenge_5.repos.ScheduleRepository;
import com.spring.binar.challenge_5.repos.StaffRepository;
import com.spring.binar.challenge_5.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private ModelMapper modelMapper;
    private final PaymentRepository paymentRepository;
    private final ScheduleRepository scheduleRepository;
    private final CostumerRepository costumerRepository;
    private final StaffRepository staffRepository;
    private final SeatReservedRepository seatReservedRepository;
    private final SeatRepository seatRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, ScheduleRepository scheduleRepository, CostumerRepository costumerRepository, StaffRepository staffRepository, SeatReservedRepository seatReservedRepository, SeatRepository seatRepository) {
        this.paymentRepository = paymentRepository;
        this.scheduleRepository = scheduleRepository;
        this.costumerRepository = costumerRepository;
        this.staffRepository =staffRepository;
        this.seatReservedRepository = seatReservedRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public Page<Payment> findAll(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }

    @Override
    public List<Payment> findAll() {
        var payments = paymentRepository.findAll();
        var responses = new ArrayList<PaymentResponseDTO>();


//        for (Payment payment : payments) {
//            var seats = seatReservedRepository.findSeatsByPaymentId(payment.getPaymentId());
//            responses.add(payment.toPaymentResponseDTO(seats));
//        }

        return paymentRepository.findAll();
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
        List<Invoice> dataList = new ArrayList<>();
        Payment payment = findById(id);
        Invoice invoice = payment.toInvoice();
        dataList.add(invoice);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(dataList);
        Map<String, Object> parameters = new HashMap();
        return JasperFillManager.fillReport(jasperReport, parameters, beanCollectionDataSource);
    }

    @Override
    public PaymentResponseDTO save(PaymentRequestDTO request) {
        System.out.println(request.toString());
        if(request.getAmount() <= 0 || request.getStaffId() > 0
                || request.getScheduleId() > 0 || request.getPaymentDate() == null
                || request.getSeatIds().isEmpty())
            throw new RuntimeException("Invalid Payment");

        // check seats if exist
        var seats = seatRepository.findAllById(request.getSeatIds());
        if(seats.isEmpty()) throw new RuntimeException("Seat not Available");

        var payment = modelMapper.map(request, Payment.class);

        var schedule = scheduleRepository.findById(request.getScheduleId());
        var staff = staffRepository.findById(request.getStaffId());
        var costumer = costumerRepository.findById(request.getCostumerId());

        var isEmpty = staff.isEmpty() || schedule.isEmpty() || costumer.isEmpty();
        if(isEmpty) throw new RuntimeException("Data is empty");

        payment.setStaff(staff.get());
        payment.setSchedule(schedule.get());
        payment.setCostumer(costumer.get());
        var seatReserved = payment.toSeatReserved(seats);

        seatReservedRepository.save(seatReserved);

        payment = paymentRepository.save(payment);

        return payment.toPaymentResponseDTO(seats);
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
