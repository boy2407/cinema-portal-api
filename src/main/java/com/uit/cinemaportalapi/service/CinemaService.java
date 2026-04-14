package com.uit.cinemaportalapi.service;

import com.uit.cinemaportalapi.entity.Cinema;

import java.util.List;

public interface CinemaService {
    List<Cinema> getCinemas();
    Cinema getCinemaByID(Long id);
    Cinema createCinema(Cinema cinema);
    Cinema updateCinema(Long id, Cinema cinema);
    void deleteCinema(Long id);

}
