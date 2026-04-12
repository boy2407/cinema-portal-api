package com.uit.cinemaportalapi.service;

import com.uit.cinemaportalapi.entity.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> getMovieIsShowing();
    List<Movie> findAllMovieComingSon();

    Movie findMovieByID(Long id);

}
