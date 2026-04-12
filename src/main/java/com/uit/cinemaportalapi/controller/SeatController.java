package com.uit.cinemaportalapi.controller;

import com.uit.cinemaportalapi.entity.Seat;
import com.uit.cinemaportalapi.payload.BookingSeatsRequest;
import com.uit.cinemaportalapi.payload.dto.SeatDTO;
import com.uit.cinemaportalapi.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/seat")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping("/{showtimeID}")
    ResponseEntity<List<Seat>> getSeatByShowTime(@PathVariable(name = "showtimeID") String showtimeID){
        return ResponseEntity.ok(seatService.getSeatByShowTime(Long.valueOf(showtimeID)));
    }

    @PatchMapping("/booking")
    ResponseEntity<List<Seat>> bookingSeats(@RequestBody BookingSeatsRequest request){
        return ResponseEntity.ok(seatService.bookingSeats(request));
    }
}