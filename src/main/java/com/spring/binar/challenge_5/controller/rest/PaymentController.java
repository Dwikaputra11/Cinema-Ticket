package com.spring.binar.challenge_5.controller.rest;

import com.spring.binar.challenge_5.dto.PaymentRequestDTO;
import com.spring.binar.challenge_5.dto.PaymentResponseDTO;
import com.spring.binar.challenge_5.models.Payment;
import com.spring.binar.challenge_5.service.PaymentService;
import com.spring.binar.challenge_5.utils.ResponseHandler;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    ModelMapper modelMapper;
    private PaymentService paymentService;
    private static final String SUCCESS_RETRIEVE_MSG = "Successfully retrieved data!";
    private static final String SUCCESS_EDIT_MSG = "Successfully edit data!";

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/payment")
    public ResponseEntity<Object> findAll(
//            @RequestParam(defaultValue ="0") int page,
//            @RequestParam(defaultValue ="10") int size
    ){
        List<PaymentResponseDTO> payments;
//        Pageable pageable = PageRequest.of(page, size);
        payments = paymentService.findAll();

        return ResponseHandler.generateResponse(SUCCESS_RETRIEVE_MSG, HttpStatus.OK,payments);
    }

    @GetMapping("/payment/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") int id){

        var data = paymentService.findById(id);

        return ResponseHandler.generateResponse(SUCCESS_RETRIEVE_MSG, HttpStatus.OK,data);
    }

    @GetMapping("/payment/document/{id}")
    public ResponseEntity<Object> getDocument(HttpServletResponse response, @PathVariable("id") int id) throws IOException, JRException {
        
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=payment.pdf");
        JasperExportManager.exportReportToPdfStream(paymentService.exportReport(id), response.getOutputStream());
        return ResponseEntity.ok().body("ok");
    }   

    @PostMapping("/payment")
    public ResponseEntity<Object> save(@RequestBody PaymentRequestDTO paymentRequestDTO){
        var data = paymentService.save(paymentRequestDTO);
        return ResponseHandler.generateResponse(SUCCESS_EDIT_MSG, HttpStatus.CREATED, data);
    }

    @PutMapping("/payment")
    public ResponseEntity<Object> update(@RequestBody PaymentRequestDTO payment){
        var data = paymentService.update(payment);

        return ResponseHandler.generateResponse(SUCCESS_EDIT_MSG, HttpStatus.OK, data);
    }

    @DeleteMapping("/payment/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id){
        paymentService.delete(id);
        return ResponseHandler.generateResponse(SUCCESS_EDIT_MSG, HttpStatus.OK, id);
    }

    private PaymentResponseDTO convertToDto(Payment payment) {
        PaymentResponseDTO postDto = modelMapper.map(payment, PaymentResponseDTO.class);
        return postDto;
    }

}
