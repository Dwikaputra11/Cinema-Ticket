package com.spring.binar.challenge_5.controller;

import com.spring.binar.challenge_5.models.Schedule;
import com.spring.binar.challenge_5.service.ScheduleService;
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
public class ScheduleController {

    private final ScheduleService scheduleService;
    private static final String SUCCESS_RETRIEVE_MSG = "Successfully retrieved data!";
    private static final String SUCCESS_EDIT_MSG = "Successfully edit data!";

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/schedule")
    public ResponseEntity<Object> findAll(
            @RequestParam(defaultValue ="0") int page,
            @RequestParam(defaultValue ="10") int size
    ){
        Page<Schedule> schedules;
        Pageable pageable = PageRequest.of(page, size);
        schedules = scheduleService.findAll(pageable);

        return ResponseHandler.generatePagingResponse(SUCCESS_RETRIEVE_MSG, HttpStatus.OK,schedules);
    }

    @GetMapping("/schedule/{id}")
    public ResponseEntity<Object> findAll(@PathVariable("id") int id){

        var data = scheduleService.findById(id);

        return ResponseHandler.generateResponse(SUCCESS_RETRIEVE_MSG, HttpStatus.OK,data);
    }

    @PostMapping("/schedule")
    public ResponseEntity<Object> save(@RequestBody Schedule schedule){
        var data = scheduleService.save(schedule);
        return ResponseHandler.generateResponse(SUCCESS_EDIT_MSG, HttpStatus.OK, data);
    }

    @PutMapping("/schedule")
    public ResponseEntity<Object> update(@RequestBody Schedule schedule){
        var data = scheduleService.update(schedule);
        return ResponseHandler.generateResponse(SUCCESS_EDIT_MSG, HttpStatus.OK, data);
    }

    @DeleteMapping("/schedule/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") int id){
        scheduleService.delete(id);
        return ResponseHandler.generateResponse(SUCCESS_EDIT_MSG, HttpStatus.OK, id);
    }

}
