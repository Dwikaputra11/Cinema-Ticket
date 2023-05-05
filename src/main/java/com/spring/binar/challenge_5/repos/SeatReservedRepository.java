package com.spring.binar.challenge_5.repos;

import com.spring.binar.challenge_5.models.SeatReserved;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatReservedRepository extends JpaRepository<SeatReserved, Integer> {

//    @Query("SELECT s.seat_id, s.row, s.number FROM Seat s LEFT JOIN SeatReserved sv ON s.seat_id = sv.seat_id where sv.paymentId = ?1")
//    List<Seat> findSeatsByPaymentId(Integer paymentId);

//    @Query("select s.seat_id, s.number, s.row, s.studio_id from Seat s" +
//            "where s.seat_id NOT IN (SELECT seat_id from SeatReserved where schedule_id = ?1) and s.studio_id = ?2")
//    List<Seat> findByScheduleAndStudioId(Integer scheduleId, Integer studioId);

}
