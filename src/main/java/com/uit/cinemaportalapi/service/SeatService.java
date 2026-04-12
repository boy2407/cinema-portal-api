package com.uit.cinemaportalapi.service;

import com.uit.cinemaportalapi.entity.Seat;
import com.uit.cinemaportalapi.entity.ShowTime;
import com.uit.cinemaportalapi.payload.BookingSeatsRequest;
import com.uit.cinemaportalapi.payload.dto.SeatDTO;

import java.util.List;

public interface SeatService {
    List<Seat> getSeatByShowTime(Long showtimeID);

    List<Seat> createSeatsForShowTime(ShowTime showtimeID);

    List<Seat> bookingSeats(BookingSeatsRequest request);
}
