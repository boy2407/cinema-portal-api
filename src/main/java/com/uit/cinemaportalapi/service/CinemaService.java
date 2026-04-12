package com.uit.cinemaportalapi.service;

import com.uit.cinemaportalapi.entity.Cinema;
import com.uit.cinemaportalapi.entity.ShowTime;
import com.uit.cinemaportalapi.payload.CreateShowTimeRequest;

import java.util.List;

public interface CinemaService {
    List<Cinema> getCinemas();

    Cinema getCinemaByID(Long id);

}
