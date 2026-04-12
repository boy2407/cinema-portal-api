package com.uit.cinemaportalapi.service.impl;


import com.uit.cinemaportalapi.entity.Cinema;
import com.uit.cinemaportalapi.exception.BadRequestException;
import com.uit.cinemaportalapi.repository.CinemaRepository;
import com.uit.cinemaportalapi.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;
    @Override
    public List<Cinema> getCinemas() {
        try {
            return cinemaRepository.findAll();
        } catch (Exception e) {
            throw new BadRequestException("Can not find all Cinema: " + e.getMessage());
        }
    }

    @Override
    public Cinema getCinemaByID(Long id) {
        try {
            Optional<Cinema> cinemaOptional = cinemaRepository.findById(id);
            return cinemaOptional.orElse(null);
        } catch (Exception e) {
            throw new BadRequestException("Can not find Movie: " + e.getMessage());
        }
    }
}
