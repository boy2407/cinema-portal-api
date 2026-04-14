package com.uit.cinemaportalapi.service;

import com.uit.cinemaportalapi.entity.*;
import com.uit.cinemaportalapi.payload.BookingSeatsRequest;

import java.util.List;

public interface ShowSeatService {
    List<ShowSeat> getShowSeats();
    ShowSeat getShowSeatByID(Long showSeatID);
    List<ShowSeat> getSeatByShowTime(Long showtimeID);
    List<ShowSeat> createSeatsForShowTime(ShowTime showtimeID);
    List<ShowSeat> bookingSeats(BookingSeatsRequest request);
    void deleteShowSeat(Long showSeatID);
}
