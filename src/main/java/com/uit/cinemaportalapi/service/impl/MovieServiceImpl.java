package com.uit.cinemaportalapi.service.impl;


import com.uit.cinemaportalapi.entity.Movie;
import com.uit.cinemaportalapi.exception.BadRequestException;
import com.uit.cinemaportalapi.repository.MovieRepository;
import com.uit.cinemaportalapi.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Override
    public List<Movie> getMovieIsShowing() {
        try {
            return movieRepository.findAllByEnableIsTrueOrderByReleaseDate();
        } catch (Exception e) {
            throw new BadRequestException("Can not find Movie: " + e.getMessage());
        }
    }

    @Override
    public List<Movie> findAllMovieComingSon() {
        try {
            LocalDate currentDate = LocalDate.now();
            return movieRepository.findAllByReleaseDate(currentDate);
        } catch (Exception e) {
            throw new BadRequestException("Can not find Movie: " + e.getMessage());
        }
    }

    @Override
    public Movie findMovieByID(Long id) {
        try {
            Optional<Movie> movie =  movieRepository.findById(id);
            if (movie.isPresent()) {
                return movie.get();
            }
        } catch (Exception e) {
            throw new BadRequestException("Can not find Movie by id: " + e.getMessage());
        }
        return null;
    }

}
