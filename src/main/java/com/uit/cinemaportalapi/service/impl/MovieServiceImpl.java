package com.uit.cinemaportalapi.service.impl;


import com.uit.cinemaportalapi.entity.Movie;
import com.uit.cinemaportalapi.exception.BadRequestException;
import com.uit.cinemaportalapi.repository.MovieRepository;
import com.uit.cinemaportalapi.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Movie> getMovies() {
        try {
            return movieRepository.findAll();
        } catch (Exception e) {
            throw new BadRequestException("Can not find all Movie: " + e.getMessage());
        }
    }


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
            return movieRepository.findById(id)
                    .orElseThrow(() -> new BadRequestException("Movie not found with id: " + id));
        } catch (Exception e) {
            throw new BadRequestException("Can not find Movie by id: " + e.getMessage());
        }
    }

    @Override
    public Movie createMovie(Movie movie) {
        try {
            movie.setId(null);
            return movieRepository.save(movie);
        } catch (Exception e) {
            throw new BadRequestException("Can not create Movie: " + e.getMessage());
        }
    }

    @Override
    public Movie updateMovie(Long id, Movie movie) {
        try {
            Movie existingMovie = findMovieByID(id);
            existingMovie.setMovieCode(movie.getMovieCode());
            existingMovie.setTitle(movie.getTitle());
            existingMovie.setCategory(movie.getCategory());
            existingMovie.setTrailerURL(movie.getTrailerURL());
            existingMovie.setPosterURL(movie.getPosterURL());
            existingMovie.setPosterHorizontalURL(movie.getPosterHorizontalURL());
            existingMovie.setTime(movie.getTime());
            existingMovie.setDescription(movie.getDescription());
            existingMovie.setRating(movie.getRating());
            existingMovie.setLanguages(movie.getLanguages());
            existingMovie.setDirector(movie.getDirector());
            existingMovie.setCast(movie.getCast());
            existingMovie.setEnable(movie.getEnable());
            existingMovie.setReleaseDate(movie.getReleaseDate());
            return movieRepository.save(existingMovie);
        } catch (Exception e) {
            throw new BadRequestException("Can not update Movie: " + e.getMessage());
        }
    }

    @Override
    public void deleteMovie(Long id) {
        try {
            Movie existingMovie = findMovieByID(id);
            movieRepository.delete(existingMovie);
        } catch (Exception e) {
            throw new BadRequestException("Can not delete Movie: " + e.getMessage());
        }
    }

}
