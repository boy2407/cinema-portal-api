package com.uit.cinemaportalapi.controller;

import com.uit.cinemaportalapi.entity.Seat;
import com.uit.cinemaportalapi.entity.ShowTime;
import com.uit.cinemaportalapi.payload.CreateShowTimeRequest;
import com.uit.cinemaportalapi.service.SeatService;
import com.uit.cinemaportalapi.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/showtime")
public class ShowTimeController {

    @Autowired
    private ShowTimeService showtimeService;

    @GetMapping("/{showtimeID}")
    ResponseEntity<List<ShowTime>> getShowTime(@PathVariable(name = "showtimeID") Long showtimeID){
        return ResponseEntity.ok(showtimeService.getShowTimeByID(showtimeID));
    }

    @GetMapping("/movie/{movieID}")
    ResponseEntity<List<ShowTime>> getShowTimeByMovie(@PathVariable(name = "movieID") Long movieID){
        return ResponseEntity.ok(showtimeService.getShowTimeByMovie(movieID));
    }

    @PostMapping("/movie")
    ResponseEntity<ShowTime> createShowTimeByMovie(@RequestBody CreateShowTimeRequest request){
        return ResponseEntity.ok(showtimeService.createShowTimeByMovie(request));
    }

    @PostMapping("/movies")
    ResponseEntity<List<ShowTime>> createShowTimeByMovies(@RequestBody List<CreateShowTimeRequest> request){
        return ResponseEntity.ok(showtimeService.createShowTimeByMovies(request));
    }
}