package com.uit.cinemaportalapi.service.impl;

import com.uit.cinemaportalapi.entity.*;
import com.uit.cinemaportalapi.exception.BadRequestException;
import com.uit.cinemaportalapi.payload.CreateShowTimeRequest;
import com.uit.cinemaportalapi.repository.ShowTimeRepository;
import com.uit.cinemaportalapi.service.*;
import jakarta.transaction.RollbackException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class ShowTimeServiceImpl implements ShowTimeService {

    @Autowired
    ShowTimeRepository showtimeRepository;
    // showTime - > movieService

    @Autowired
    MovieService movieService;

    @Autowired
    ShowSeatService showSeatService;

    @Autowired
    CinemaService cinemaService;

    @Autowired
    ScreenService screenService;

    @Override
    public List<ShowTime> getShowTimes() {
        try {
            return showtimeRepository.findAll();
        } catch (Exception e) {
            throw new BadRequestException("Can not find showtimes: " + e.getMessage());
        }
    }

    @Override
    public ShowTime getShowTimeByID(Long id) {
        try {
            return showtimeRepository.findById(id)
                    .orElseThrow(() -> new BadRequestException("ShowTime not found with id: " + id));
        } catch (Exception e) {
            throw new BadRequestException("Can not find showtime by id: " + e.getMessage());
        }
    }

    @Override
    public List<ShowTime> getShowTimeByMovie(Long movieID) {
        try {
            Movie movie = movieService.findMovieByID(movieID);
            return showtimeRepository.findAllByMovie_IdAndStartTimeGreaterThanEqualOrderByStartTimeAsc(movie.getId(), new Date());
        } catch (Exception e) {
            throw new BadRequestException("Can not find showtimes: " + e.getMessage());
        }
    }


    @Transactional (rollbackOn = {Exception.class, RollbackException.class})
    @Override
    public ShowTime createShowTimeByMovie(CreateShowTimeRequest request) {
        return createShowTimeInternal(request);
    }

    @Transactional (rollbackOn = {Exception.class, RollbackException.class})
    @Override
    public List<ShowTime> createShowTimeByMovies(List<CreateShowTimeRequest> request) {
        List<ShowTime> response = new ArrayList<>();
        for (CreateShowTimeRequest createShowTimeRequest : request) {
            ShowTime showtime = createShowTimeInternal(createShowTimeRequest);
            response.add(showtime);
        }

        return response;
    }

    @Transactional (rollbackOn = {Exception.class, RollbackException.class})
    @Override
    public ShowTime updateShowTime(Long showtimeID, CreateShowTimeRequest request) {
        try {
            validateCreateShowTimeRequest(request);

            ShowTime existingShowTime = getShowTimeByID(showtimeID);
            Screen screen = screenService.findScreenByID(request.getScreenID());
            Cinema cinema = cinemaService.getCinemaByID(request.getCinemaID());
            if (screen.getCinema() == null || !screen.getCinema().getId().equals(cinema.getId())) {
                throw new BadRequestException("Screen does not belong to cinema. screenID=" + request.getScreenID() + ", cinemaID=" + request.getCinemaID());
            }

            boolean hasOverlap = showtimeRepository.existsOverlappingShowTimeExceptId(
                    screen.getId(), showtimeID, request.getStartTime(), request.getEndTime());
            if (hasOverlap) {
                throw new BadRequestException("ShowTime is overlapping on this screen");
            }

            Movie movie = movieService.findMovieByID(request.getMovieID());

            existingShowTime.setStartTime(request.getStartTime());
            existingShowTime.setEndTime(request.getEndTime());
            existingShowTime.setScreen(screen);
            existingShowTime.setCinema(cinema);
            existingShowTime.setMovie(movie);
            existingShowTime.setPrice(request.getPrice());

            ShowTime savedShowTime = showtimeRepository.save(existingShowTime);
            List<ShowSeat> seats = showSeatService.createSeatsForShowTime(savedShowTime);
            savedShowTime.setShowSeats(seats);
            return savedShowTime;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Can not update showtime: " + e.getMessage());
        }
    }

    @Transactional (rollbackOn = {Exception.class, RollbackException.class})
    @Override
    public void deleteShowTime(Long showtimeID) {
        try {
            ShowTime showTime = getShowTimeByID(showtimeID);
            showtimeRepository.delete(showTime);
        } catch (Exception e) {
            throw new BadRequestException("Can not delete showtime: " + e.getMessage());
        }
    }

    private ShowTime createShowTimeInternal(CreateShowTimeRequest request) {
        try {
            validateCreateShowTimeRequest(request);

            ShowTime showTime = new ShowTime();
            showTime.setStartTime(request.getStartTime());
            showTime.setEndTime(request.getEndTime());

            Screen screen = screenService.findScreenByID(request.getScreenID());
            Cinema cinema = cinemaService.getCinemaByID(request.getCinemaID());
            if (screen.getCinema() == null || !screen.getCinema().getId().equals(cinema.getId())) {
                throw new BadRequestException("Screen does not belong to cinema. screenID=" + request.getScreenID() + ", cinemaID=" + request.getCinemaID());
            }

            boolean hasOverlap = showtimeRepository.existsOverlappingShowTime(screen.getId(), request.getStartTime(), request.getEndTime());
            if (hasOverlap) {
                throw new BadRequestException("ShowTime is overlapping on this screen");
            }

            Movie movie = movieService.findMovieByID(request.getMovieID());

            showTime.setScreen(screen);
            showTime.setCinema(cinema);
            showTime.setMovie(movie);
            showTime.setPrice(request.getPrice());

            ShowTime showtimeSaved = showtimeRepository.save(showTime);

            List<ShowSeat> seats = showSeatService.createSeatsForShowTime(showtimeSaved);
            showtimeSaved.setShowSeats(seats);
            return showtimeSaved;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Can not create showtime: " + e.getMessage());
        }
    }

    private void validateCreateShowTimeRequest(CreateShowTimeRequest request) {
        if (request.getCinemaID() == null || request.getMovieID() == null || request.getScreenID() == null) {
            throw new BadRequestException("cinemaID, movieID and screenID are required");
        }
        if (request.getPrice() == null) {
            throw new BadRequestException("price is required");
        }
        if (request.getStartTime() == null || request.getEndTime() == null) {
            throw new BadRequestException("startTime and endTime are required");
        }
        if (!request.getStartTime().before(request.getEndTime())) {
            throw new BadRequestException("startTime must be before endTime");
        }
    }
}
