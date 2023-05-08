package com.spring.binar.challenge_5.service.implementation;

import com.spring.binar.challenge_5.dto.PaymentRequestDTO;
import com.spring.binar.challenge_5.dto.PaymentResponseDTO;
import com.spring.binar.challenge_5.models.Invoice;
import com.spring.binar.challenge_5.models.Payment;
import com.spring.binar.challenge_5.models.Seat;
import com.spring.binar.challenge_5.repos.*;
import com.spring.binar.challenge_5.repos.CostumerRepository;
import com.spring.binar.challenge_5.repos.PaymentRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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
        this.staffRepository = staffRepository;
        this.seatReservedRepository = seatReservedRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public Page<Payment> findAll(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }

    @Override
    public List<PaymentResponseDTO> findAll() {
        var payments = paymentRepository.findAll();

        if(payments.isEmpty()) return new ArrayList<>();

        var responses = payments.stream().map(payment -> {
            var seatsReserved = seatReservedRepository.findSeatsByPaymentPaymentId(payment.getPaymentId());
            System.out.println("seat reserved: " + seatsReserved);
            var seats = seatsReserved.stream().map(it -> seatRepository.findById(it.getSeat().getSeatId()).orElseThrow()).toList();
            System.out.println("seats " + seats);
            return payment.toPaymentResponseDTO(seats);
        }).toList();

        System.out.println("Response: " + responses);

        return responses;
    }

    @Override
    public PaymentResponseDTO findById(int id) {
        var payment = paymentRepository.findById(id).orElseThrow();

        var seatReserved = seatReservedRepository.findSeatsByPaymentPaymentId(payment.getPaymentId());
        var seats = seatReserved.stream().map(it -> seatRepository.findById(it.getSeat().getSeatId()).orElseThrow()).toList();

        return payment.toPaymentResponseDTO(seats);
    }

    @Override
    public JasperPrint exportReport(int id) throws JRException, FileNotFoundException {
        String file = ResourceUtils.getFile("classpath:challenge5payment.jrxml").getAbsolutePath();
        JasperReport jasperReport = JasperCompileManager.compileReport(file);
        List<Invoice> dataList = new ArrayList<>();
        Payment payment = paymentRepository.findById(id).orElseThrow();
        Invoice invoice = payment.toInvoice();
        dataList.add(invoice);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(dataList);
        Map<String, Object> parameters = new HashMap();
        return JasperFillManager.fillReport(jasperReport, parameters, beanCollectionDataSource);
    }

    @Override
    public PaymentResponseDTO save(PaymentRequestDTO request) {
        System.out.println(request.toString());
        if(request.getAmount() <= 0 || request.getStaffId() <= 0
                || request.getScheduleId() <= 0 || request.getSeatIds().isEmpty())
            throw new RuntimeException("Invalid Payment");

        // check seats if exist
        List<Seat> seats = seatRepository.findAllById(request.getSeatIds());
        System.out.println("seats : " + seats);
        if(seats.isEmpty()) throw new RuntimeException("Seat not Available");

        var schedule = scheduleRepository.findById(request.getScheduleId());
        var staff = staffRepository.findById(request.getStaffId());
        var costumer = costumerRepository.findById(request.getCostumerId());

        var isEmpty = staff.isEmpty() || schedule.isEmpty() || costumer.isEmpty();
        if(isEmpty) throw new RuntimeException("Data is empty");

        var payment = new Payment();

        payment.setStaff(staff.get());
        payment.setSchedule(schedule.get());
        payment.setCostumer(costumer.get());
        payment.setPaymentDate(request.getPaymentDate());
        payment.setAmount(request.getAmount());

        payment = paymentRepository.save(payment);

        var seatReserved = payment.toSeatReserved(seats);

        seatReserved.forEach(it -> System.out.println(it.toString()));

        // save each seat to db
        seatReserved.forEach(seatReservedRepository::save);


        return payment.toPaymentResponseDTO(seats);
    }

    @Override
    public PaymentResponseDTO update(PaymentRequestDTO updatedPayment) {
        var payment = paymentRepository.findById(updatedPayment.getPaymentId()).orElseThrow();

        List<Seat> seats = seatRepository.findAllById(updatedPayment.getSeatIds());
        System.out.println("seats : " + seats);
        if(seats.isEmpty()) throw new RuntimeException("Seat not Available");

        var schedule = scheduleRepository.findById(updatedPayment.getScheduleId());
        var staff = staffRepository.findById(updatedPayment.getStaffId());
        var costumer = costumerRepository.findById(updatedPayment.getCostumerId());

        if(schedule.isEmpty() || staff.isEmpty() || costumer.isEmpty()) throw new RuntimeException("No Item found");

        payment.setSchedule(schedule.get());
        payment.setStaff(staff.get());
        payment.setCostumer(costumer.get());
        payment.setPaymentDate(updatedPayment.getPaymentDate());
        payment.setAmount(updatedPayment.getAmount());

        payment = paymentRepository.save(payment);

        seatReservedRepository.removeAllByPaymentPaymentId(payment.getPaymentId());
        var seatReserved = payment.toSeatReserved(seats);
        seatReservedRepository.saveAll(seatReserved);


        return payment.toPaymentResponseDTO(seats);
    }

    @Override
    public void delete(int id) {
        var result = paymentRepository.findById(id);
        if(result.isEmpty()) throw new RuntimeException("No Payment found");

        paymentRepository.delete(result.get());
    }
}
