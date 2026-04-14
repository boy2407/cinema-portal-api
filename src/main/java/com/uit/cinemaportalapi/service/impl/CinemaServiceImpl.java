package com.uit.cinemaportalapi.service.impl;


import com.uit.cinemaportalapi.entity.Cinema;
import com.uit.cinemaportalapi.exception.BadRequestException;
import com.uit.cinemaportalapi.repository.CinemaRepository;
import com.uit.cinemaportalapi.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            return cinemaRepository.findById(id)
                    .orElseThrow(() -> new BadRequestException("Cinema not found with id: " + id));
        } catch (Exception e) {
            throw new BadRequestException("Can not find Cinema: " + e.getMessage());
        }
    }

    @Override
    public Cinema createCinema(Cinema cinema) {
        try {
            cinema.setId(null);
            return cinemaRepository.save(cinema);
        } catch (Exception e) {
            throw new BadRequestException("Can not create Cinema: " + e.getMessage());
        }
    }

    @Override
    public Cinema updateCinema(Long id, Cinema cinema) {
        try {
            Cinema existingCinema = getCinemaByID(id);
            existingCinema.setCode(cinema.getCode());
            existingCinema.setName(cinema.getName());
            existingCinema.setLocation(cinema.getLocation());
            return cinemaRepository.save(existingCinema);
        } catch (Exception e) {
            throw new BadRequestException("Can not update Cinema: " + e.getMessage());
        }
    }

    @Override
    public void deleteCinema(Long id) {
        try {
            Cinema existingCinema = getCinemaByID(id);
            cinemaRepository.delete(existingCinema);
        } catch (Exception e) {
            throw new BadRequestException("Can not delete Cinema: " + e.getMessage());
        }
    }
}
