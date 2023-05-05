package com.spring.binar.challenge_5.repos;

import com.spring.binar.challenge_5.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface SeatRepository extends JpaRepository <Seat, Integer>{
    @Query(value = "SELECT s.seat_id, s.row, s.number FROM Seat s LEFT JOIN SeatReserved sv ON s.seat_id = sv.seat_id where sv.schedule_id = ?1", nativeQuery = true)
    List<Seat> findSeatAvailable( Integer scheduleId);

//    @Query("SELECT * FROM Seat WHERE studioId = ?1")
    List<Seat> findAllByStudioStudioId(Integer studioId);
}
