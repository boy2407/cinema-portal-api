package com.uit.cinemaportalapi.service.impl;

import com.uit.cinemaportalapi.entity.Cinema;
import com.uit.cinemaportalapi.entity.Movie;
import com.uit.cinemaportalapi.entity.Screen;
import com.uit.cinemaportalapi.entity.ShowSeat;
import com.uit.cinemaportalapi.entity.ShowTime;
import com.uit.cinemaportalapi.exception.BadRequestException;
import com.uit.cinemaportalapi.payload.CreateShowTimeRequest;
import com.uit.cinemaportalapi.repository.ShowTimeRepository;
import com.uit.cinemaportalapi.service.CinemaService;
import com.uit.cinemaportalapi.service.MovieService;
import com.uit.cinemaportalapi.service.ScreenService;
import com.uit.cinemaportalapi.service.ShowSeatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShowTimeServiceImplTest {

    @Mock
    private ShowTimeRepository showtimeRepository;

    @Mock
    private MovieService movieService;

    @Mock
    private ShowSeatService showSeatService;

    @Mock
    private CinemaService cinemaService;

    @Mock
    private ScreenService screenService;

    @InjectMocks
    private ShowTimeServiceImpl showTimeService;

    @Test
    void createShowTimeByMovie_rejectWhenOverlapOnSameScreen() {
        CreateShowTimeRequest request = buildRequest();

        Cinema cinema = new Cinema();
        cinema.setId(1L);
        Screen screen = new Screen();
        screen.setId(2L);
        screen.setCinema(cinema);

        when(screenService.findScreenByID(2L)).thenReturn(screen);
        when(cinemaService.getCinemaByID(1L)).thenReturn(cinema);
        when(showtimeRepository.existsOverlappingShowTime(2L, request.getStartTime(), request.getEndTime())).thenReturn(true);

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> showTimeService.createShowTimeByMovie(request));

        assertNotNull(ex.getMessage());
        verify(showtimeRepository).existsOverlappingShowTime(2L, request.getStartTime(), request.getEndTime());
    }

    @Test
    void createShowTimeByMovie_generateShowSeatsFromRoomSeatBlueprint() {
        CreateShowTimeRequest request = buildRequest();

        Cinema cinema = new Cinema();
        cinema.setId(1L);

        Screen screen = new Screen();
        screen.setId(2L);
        screen.setCinema(cinema);

        Movie movie = new Movie();
        movie.setId(3L);

        when(screenService.findScreenByID(2L)).thenReturn(screen);
        when(cinemaService.getCinemaByID(1L)).thenReturn(cinema);
        when(showtimeRepository.existsOverlappingShowTime(2L, request.getStartTime(), request.getEndTime())).thenReturn(false);
        when(movieService.findMovieByID(3L)).thenReturn(movie);
        when(showtimeRepository.save(ArgumentMatchers.any(ShowTime.class))).thenAnswer(invocation -> {
            ShowTime saved = invocation.getArgument(0);
            saved.setId(99L);
            return saved;
        });
        when(showSeatService.createSeatsForShowTime(ArgumentMatchers.any(ShowTime.class)))
                .thenReturn(Collections.singletonList(new ShowSeat()));

        ShowTime saved = showTimeService.createShowTimeByMovie(request);

        assertNotNull(saved);
        assertEquals(99L, saved.getId());
        assertNotNull(saved.getShowSeats());
        assertEquals(1, saved.getShowSeats().size());
        verify(showSeatService).createSeatsForShowTime(ArgumentMatchers.any(ShowTime.class));
    }

    private CreateShowTimeRequest buildRequest() {
        CreateShowTimeRequest request = new CreateShowTimeRequest();
        request.setCinemaID(1L);
        request.setScreenID(2L);
        request.setMovieID(3L);
        request.setPrice(BigDecimal.valueOf(120000));

        Date start = new Date(System.currentTimeMillis() + 3600_000);
        Date end = new Date(start.getTime() + 7200_000);
        request.setStartTime(start);
        request.setEndTime(end);
        return request;
    }
}

