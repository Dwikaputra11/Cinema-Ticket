package com.spring.binar.challenge_5.service;

import com.spring.binar.challenge_5.models.Schedule;
import com.spring.binar.challenge_5.repos.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    private ScheduleRepository repository;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Schedule> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Schedule findById(int id) {
        var data = repository.findById(id);

        if(data.isEmpty()) throw new RuntimeException("No Schedule found");

        return data.get();
    }

    @Override
    public Schedule save(Schedule schedule) {
        System.out.println(schedule.toString());
        if(schedule.getPrice() <= 0 || schedule.getDueDate() == null
                || schedule.getStudio() == null || schedule.getFilm() == null)
            throw new RuntimeException("Data not valid");

        return repository.save(schedule);
    }

    @Override
    public Schedule update(Schedule updatedSchedule) {
        var result = repository.findById(updatedSchedule.getScheduleId());

        if(result.isEmpty()) throw new RuntimeException("No Schedule found");
        
        var schedule = result.get();
        schedule.setFilm(updatedSchedule.getFilm());
        schedule.setPrice(updatedSchedule.getPrice());
        schedule.setDueDate(updatedSchedule.getDueDate());
        schedule.setStudio(updatedSchedule.getStudio());



        return repository.save(schedule);
    }

    @Override
    public void delete(int id) {
        var result = repository.findById(id);

        if(result.isEmpty()) throw new RuntimeException("No Schedule found");

        repository.delete(result.get());
    }
}
