package com.spring.binar.challenge_5.service.implementation;

import com.spring.binar.challenge_5.dto.ScheduleRequestDTO;
import com.spring.binar.challenge_5.models.Schedule;
import com.spring.binar.challenge_5.repos.*;
import com.spring.binar.challenge_5.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final StudioRepository studioRepository;
    private final FilmRepository filmRepository;
    private final SeatRepository seatRepository;
    private final SeatReservedRepository seatReservedRepository;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, StudioRepository studioRepository, FilmRepository filmRepository, SeatRepository seatRepository, SeatReservedRepository seatReservedRepository) {
        this.scheduleRepository = scheduleRepository;
        this.studioRepository = studioRepository;
        this.filmRepository = filmRepository;
        this.seatRepository = seatRepository;
        this.seatReservedRepository = seatReservedRepository;
    }

    @Override
    public Page<Schedule> findAll(Pageable pageable) {
        return scheduleRepository.findAll(pageable);
    }

    @Override
    public Schedule findById(int id) {
        var data = scheduleRepository.findById(id);

        if(data.isEmpty()) throw new RuntimeException("No Schedule found");

        return data.get();
    }

    @Override
    public ScheduleRequestDTO save(Schedule schedule) {
        return null;
    }

//    @Override
//    public ScheduleRequestDTO save(Schedule schedule) {
//        System.out.println(schedule.toString());
//        if(schedule.getPrice() <= 0 || schedule.getFromDate() > 0 || schedule.getToDate() > 0
//                || schedule.getStudio() == null || schedule.getFilm() == null)
//            throw new RuntimeException("Data not valid");
//
////        var film = filmRepository.findById(schedule.getFilm().getFilmId());
////        var studio = studioRepository.findById(schedule.getStudio().getStudioId());
//        var seats = seatRepository.findAllByStudioStudioId(schedule.getStudioId());
//        var seatAvailable = seatRepository.findSeatAvailable(schedule.getScheduleId());
//
////        var isEmpty = film.isEmpty() || studio.isEmpty();
////        if(isEmpty) throw new RuntimeException("Data not valid");
//
////        schedule.setStudio(studio.get());
////        schedule.setFilm(film.get());
//
//        return scheduleRepository.save(schedule);
//    }

//    @Override
//    public ScheduleRequestDTO save(ScheduleRequestDTO schedule) {
//        System.out.println(schedule.toString());
//        if(schedule.getPrice() <= 0 || schedule.getFromDate() > 0 || schedule.getToDate() > 0
//                || schedule.getStudioId() > 0 || schedule.getFilmId() > 0)
//            throw new RuntimeException("Data not valid");
//
//        var film = filmRepository.findById(schedule.getFilmId());
//        var studio = studioRepository.findById(schedule.getStudioId());
//        var seats = seatRepository.findAllByStudioStudioId(schedule.getStudioId());
////        var seatAvailable = seatRepository.findSeatAvailable(schedule.getScheduleId());
//
//        var sceduled = scheduleRepository.save(schedule);
//
//
////        var isEmpty = film.isEmpty() || studio.isEmpty();
////        if(isEmpty) throw new RuntimeException("Data not valid");
//
////        schedule.setStudio(studio.get());
////        schedule.setFilm(film.get());
//
//        return
//    }

    @Override
    public Schedule update(Schedule updatedSchedule) {
        var result = scheduleRepository.findById(updatedSchedule.getScheduleId());

        if(result.isEmpty()) throw new RuntimeException("No Schedule found");
        
        var schedule = result.get();
        schedule.setFilm(updatedSchedule.getFilm());
        schedule.setPrice(updatedSchedule.getPrice());
        schedule.setFromDate(updatedSchedule.getFromDate());
        schedule.setStudio(updatedSchedule.getStudio());



        return scheduleRepository.save(schedule);
    }

    @Override
    public void delete(int id) {
        var result = scheduleRepository.findById(id);

        if(result.isEmpty()) throw new RuntimeException("No Schedule found");

        scheduleRepository.delete(result.get());
    }
}
