package com.uit.cinemaportalapi.repository;

import com.uit.cinemaportalapi.entity.ShowSeat;
import com.uit.cinemaportalapi.entity.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long > {
    List<ShowSeat> findAllByShowTime(ShowTime showTime);
    List<ShowSeat> findAllByShowTimeId(Long showTimeId);
}