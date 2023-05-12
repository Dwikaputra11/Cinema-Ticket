package com.spring.binar.challenge_5.controller.rest;

import com.spring.binar.challenge_5.models.Costumer;
import com.spring.binar.challenge_5.service.CostumerService;
import com.spring.binar.challenge_5.utils.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.spring.binar.challenge_5.utils.Constants.SUCCESS_EDIT_MSG;
import static com.spring.binar.challenge_5.utils.Constants.SUCCESS_RETRIEVE_MSG;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CostumerController {

    private final CostumerService costumerService;

    @GetMapping("/costumer")
    public ResponseEntity<Object> findAll(
            @RequestParam(defaultValue ="0") int page,
            @RequestParam(defaultValue ="10") int size
    ){
        Page<Costumer> filmList;
        Pageable pageable = PageRequest.of(page, size);
        filmList = costumerService.findAll(pageable);

        return ResponseHandler.generatePagingResponse(SUCCESS_RETRIEVE_MSG, HttpStatus.OK,filmList);
    }

    @GetMapping("/costumer/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") int id){
        var costumer = costumerService.findById(id);
        return ResponseHandler.generateResponse(SUCCESS_RETRIEVE_MSG, HttpStatus.OK,costumer);
    }

    @PostMapping("/costumer")
    public ResponseEntity<Object> save(@RequestBody Costumer film){
        costumerService.save(film);
        return ResponseHandler.generateResponse(SUCCESS_EDIT_MSG, HttpStatus.OK,film);
    }

    @PutMapping("/costumer")
    public ResponseEntity<Object> update(@RequestBody Costumer film ) {
        costumerService.update(film);
        return ResponseHandler.generateResponse(SUCCESS_EDIT_MSG, HttpStatus.OK, film);
    }
    @DeleteMapping("/costumer/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id ) {
        costumerService.delete(id);
        return ResponseHandler.generateResponse(SUCCESS_EDIT_MSG, HttpStatus.OK, id);
    }
}
