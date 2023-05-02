package com.spring.binar.challenge_5.service;

import com.spring.binar.challenge_5.models.Costumer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CostumerService {

    Page<Costumer> findAll(Pageable pageable);

    Costumer findById(int id);

    Costumer save(Costumer costumer);

    Costumer update(Costumer updatedCostumer);

    void delete(int id);
}
