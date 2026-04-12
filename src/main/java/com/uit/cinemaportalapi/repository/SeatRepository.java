package com.uit.cinemaportalapi.repository;

import com.uit.cinemaportalapi.entity.Seat;
import com.uit.cinemaportalapi.payload.dto.SeatDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Long > {
    List<Seat> findAllByShowtimeId(Long showtimeID);
}