package com.uit.cinemaportalapi.controller;

import com.uit.cinemaportalapi.entity.ShowTime;
import com.uit.cinemaportalapi.payload.CreateShowTimeRequest;
import com.uit.cinemaportalapi.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"/showtimes", "/showtime"})
public class ShowTimeController {

    @Autowired
    private ShowTimeService showtimeService;

    @GetMapping
    ResponseEntity<List<ShowTime>> getShowTimes(){
        return ResponseEntity.ok(showtimeService.getShowTimes());
    }

    @GetMapping("/{showtimeID}")
    ResponseEntity<ShowTime> getShowTime(@PathVariable(name = "showtimeID") Long showtimeID){
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

    @PutMapping("/{showtimeID}")
    ResponseEntity<ShowTime> updateShowTime(@PathVariable(name = "showtimeID") Long showtimeID,
                                            @RequestBody CreateShowTimeRequest request){
        return ResponseEntity.ok(showtimeService.updateShowTime(showtimeID, request));
    }

    @DeleteMapping("/{showtimeID}")
    ResponseEntity<String> deleteShowTime(@PathVariable(name = "showtimeID") Long showtimeID){
        showtimeService.deleteShowTime(showtimeID);
        return ResponseEntity.ok("Deleted showtime with id: " + showtimeID);
    }
}