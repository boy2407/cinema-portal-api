package com.uit.cinemaportalapi.repository;

import com.uit.cinemaportalapi.entity.RoomSeat;
import com.uit.cinemaportalapi.entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomSeatRepository extends JpaRepository<RoomSeat, Long> {
    List<RoomSeat> findAllByScreen(Screen screen);
    List<RoomSeat> findAllByScreenId(Long screenId);
    boolean existsByScreenAndSeatRowAndSeatNumber(Screen screen, String seatRow, Integer seatNumber);
    boolean existsByScreenAndSeatRowAndSeatNumberAndIdNot(Screen screen, String seatRow, Integer seatNumber, Long id);
}

