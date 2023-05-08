package com.spring.binar.challenge_5.service.implementation;

import com.spring.binar.challenge_5.dto.ScheduleRequestDTO;
import com.spring.binar.challenge_5.dto.ScheduleResponseDTO;
import com.spring.binar.challenge_5.models.Schedule;
import com.spring.binar.challenge_5.repos.*;
import com.spring.binar.challenge_5.service.ScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    ModelMapper modelMapper;
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
    public List<ScheduleResponseDTO> findAll() {
        var schedules = scheduleRepository.findAll();
        if(schedules.isEmpty()) return new ArrayList<>();

        return schedules.stream().map(schedule -> {
            var availableSeats = seatRepository.findAvailableSeats(schedule.getScheduleId(), schedule.getStudio().getStudioId());
            System.out.println("Avalable seats" + availableSeats);
            return schedule.convertToResponse(availableSeats);
        }).toList();
    }

    @Override
    public ScheduleResponseDTO findById(int id) {
        var schedule = scheduleRepository.findById(id);

        if(schedule.isEmpty()) throw new RuntimeException("No Schedule found");

        var availableSeats = seatRepository.findAvailableSeats(schedule.get().getScheduleId(), schedule.get().getStudio().getStudioId());

        return schedule.get().convertToResponse(availableSeats);
    }

    @Override
    public ScheduleResponseDTO save(ScheduleRequestDTO request) {

        if(request.getScheduleId() > 0 && (scheduleRepository.findById(request.getScheduleId()).isPresent()))
            throw new RuntimeException("Schedule already exists");

        request.setScheduleId(0);

        if(request.getFromDate() > request.getToDate())
            throw new RuntimeException("Date range not valid");

        var studio = studioRepository.findById(request.getStudioId());
        var film = filmRepository.findById(request.getFilmId());

        if(studio.isEmpty() || film.isEmpty())
            throw new RuntimeException("No studio or film found");

        var schedule = modelMapper.map(request, Schedule.class);
        schedule.setFilm(film.get());
        schedule.setStudio(studio.get());

        var result = scheduleRepository.save(schedule);

        var seats = seatRepository.findByStudioStudioId(result.getStudio().getStudioId());

        return result.convertToResponse(seats);
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

    /*
    * permasalahan ketika schedule di update studio maka payment yang sudah terjadi perlu overwrite id seat yang ada agar sesuai
    * id seat yang sesuai pada studio.
    * Jadi update hanya dibatasi pada perubahan film, price, dan tanggal
    * */
    @Override
    public ScheduleResponseDTO update(ScheduleRequestDTO updatedSchedule) {
        var request = scheduleRepository.findById(updatedSchedule.getScheduleId());

        if(request.isEmpty())
            throw new RuntimeException("No Schedule found");
        
        var schedule = request.get();

        var film = filmRepository.findById(updatedSchedule.getFilmId());
        if(film.isEmpty())
            throw new RuntimeException("No film found");

        if(updatedSchedule.getFromDate() > updatedSchedule.getToDate())
            throw new RuntimeException("Date range not valid");

        schedule.setPrice(updatedSchedule.getPrice());
        schedule.setFromDate(updatedSchedule.getFromDate());
        schedule.setToDate(updatedSchedule.getToDate());
        schedule.setFilm(film.get());

        var result = scheduleRepository.save(schedule);
        var seats = seatRepository.findByStudioStudioId(result.getStudio().getStudioId());

        return result.convertToResponse(seats);
    }

    @Override
    public void delete(int id) {
        var result = scheduleRepository.findById(id);

        if(result.isEmpty()) throw new RuntimeException("No Schedule found");

        scheduleRepository.delete(result.get());
    }
}
