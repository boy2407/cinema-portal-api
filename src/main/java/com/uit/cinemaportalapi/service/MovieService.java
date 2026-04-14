package com.uit.cinemaportalapi.service;

import com.uit.cinemaportalapi.entity.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> getMovies();
    List<Movie> getMovieIsShowing();
    List<Movie> findAllMovieComingSon();
    Movie findMovieByID(Long id);
    Movie createMovie(Movie movie);
    Movie updateMovie(Long id, Movie movie);
    void deleteMovie(Long id);

}




