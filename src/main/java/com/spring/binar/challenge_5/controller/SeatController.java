package com.spring.binar.challenge_5.controller;

import com.spring.binar.challenge_5.models.Seat;
import com.spring.binar.challenge_5.service.SeatService;
import com.spring.binar.challenge_5.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SeatController {
    private final SeatService seatService;
    private static final String SUCCESS_RETRIEVE_MSG = "Successfully retrieved data!";
    private static final String SUCCESS_EDIT_MSG = "Successfully edit data!";

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/seat")
    public ResponseEntity<Object> findAll(
            @RequestParam(defaultValue ="0") int page,
            @RequestParam(defaultValue ="10") int size
    ){
        Page<Seat> seatList;
        Pageable pageable = PageRequest.of(page, size);
        seatList = seatService.findAll(pageable);

        return ResponseHandler.generatePagingResponse(SUCCESS_RETRIEVE_MSG, HttpStatus.OK,seatList);
    }
    @GetMapping("/seat/{id}")
    public ResponseEntity<Object> findAll(@PathVariable("id") int id){

        var seats = seatService.findById(id);

        return ResponseHandler.generateResponse(SUCCESS_RETRIEVE_MSG, HttpStatus.OK,seats);
    }
    @PostMapping("/seat")
    public ResponseEntity<Object> save(@RequestBody Seat seat){
        var seats = seatService.save(seat);
        return ResponseHandler.generateResponse(SUCCESS_EDIT_MSG, HttpStatus.OK, seats);
    }
    @PutMapping("/seat")
    public ResponseEntity<Object> update(@RequestBody Seat seat ) {
        seatService.update(seat);
        return ResponseHandler.generateResponse(SUCCESS_EDIT_MSG, HttpStatus.OK, seat);
    }
    @DeleteMapping("/seat/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id ) {
        seatService.delete(id);
        return ResponseHandler.generateResponse(SUCCESS_EDIT_MSG, HttpStatus.OK, id);
    }
}
