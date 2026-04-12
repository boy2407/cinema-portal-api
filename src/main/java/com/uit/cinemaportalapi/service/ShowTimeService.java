package com.uit.cinemaportalapi.service;

import com.uit.cinemaportalapi.entity.Seat;
import com.uit.cinemaportalapi.entity.ShowTime;
import com.uit.cinemaportalapi.payload.CreateShowTimeRequest;

import java.util.List;

public interface ShowTimeService {
    List<ShowTime> getShowTimeByID(Long showtimeID);

    List<ShowTime> getShowTimeByMovie(Long movieID);

    ShowTime createShowTimeByMovie(CreateShowTimeRequest request);
    List<ShowTime> createShowTimeByMovies(List<CreateShowTimeRequest> request);

}
