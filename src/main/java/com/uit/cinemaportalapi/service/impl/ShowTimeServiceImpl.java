package com.uit.cinemaportalapi.service.impl;

import com.uit.cinemaportalapi.entity.Cinema;
import com.uit.cinemaportalapi.entity.Movie;
import com.uit.cinemaportalapi.entity.Seat;
import com.uit.cinemaportalapi.entity.ShowTime;
import com.uit.cinemaportalapi.exception.BadRequestException;
import com.uit.cinemaportalapi.payload.CreateShowTimeRequest;
import com.uit.cinemaportalapi.repository.ShowTimeRepository;
import com.uit.cinemaportalapi.service.CinemaService;
import com.uit.cinemaportalapi.service.MovieService;
import com.uit.cinemaportalapi.service.SeatService;
import com.uit.cinemaportalapi.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class ShowTimeServiceImpl implements ShowTimeService {

    @Autowired
    ShowTimeRepository showtimeRepository;

    @Autowired
    MovieService movieService;

    @Autowired
    SeatService seatService;

    @Autowired
    CinemaService cinemaService;

    @Override
    public List<ShowTime> getShowTimeByID(Long id) {
        try {
            return showtimeRepository.findAllById(Collections.singleton(id));
        } catch (Exception e) {
            throw new BadRequestException("Can not find seats for show time: " + e.getMessage());
        }
    }

    @Override
    public List<ShowTime> getShowTimeByMovie(Long movieID) {
        try {
            Movie movie = movieService.findMovieByID(movieID);
            LocalDateTime currentTime = LocalDateTime.now();
            System.out.println(currentTime + "Time");

            return showtimeRepository.findAllByMovieAndStartTime(movie.getId(), currentTime);
        } catch (Exception e) {
            throw new BadRequestException("Can not find showtimes: " + e.getMessage());
        }
    }

    @Override
    public ShowTime createShowTimeByMovie(CreateShowTimeRequest request) {
        try {
            ShowTime showTime = new ShowTime();

            showTime.setStartTime(request.getStartTime());
            showTime.setEndTime(request.getEndTime());
            showTime.setScreen(request.getScreen());
            showTime.setPrice(request.getPrice());

            Movie movie = movieService.findMovieByID(request.getMovieID());
            showTime.setMovie(movie);

            Cinema cinema = cinemaService.getCinemaByID(request.getCinemaID());

            showTime.setCinemaName(cinema.getName());
            showTime.setCinema(cinema);

            ShowTime showtimeSaved = showtimeRepository.save(showTime);

            List<Seat> seats = seatService.createSeatsForShowTime(showtimeSaved);
            showtimeSaved.setSeats(seats);

            showtimeRepository.save(showtimeSaved);

            return showTime;
        } catch (Exception e) {
            throw new BadRequestException("Can not find showtimes: " + e.getMessage());
        }
    }

    @Override
    public List<ShowTime> createShowTimeByMovies(List<CreateShowTimeRequest> request) {
        List<ShowTime> response = new ArrayList<>();
        for (CreateShowTimeRequest createShowTimeRequest : request) {
            ShowTime showtime = createShowTimeByMovie(createShowTimeRequest);
            response.add(showtime);
        }

        return response;
    }
}
