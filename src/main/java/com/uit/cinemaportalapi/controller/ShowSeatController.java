package com.uit.cinemaportalapi.controller;

import com.uit.cinemaportalapi.entity.RoomSeat;
import com.uit.cinemaportalapi.entity.SeatType;
import com.uit.cinemaportalapi.entity.ShowSeat;
import com.uit.cinemaportalapi.payload.CreateRoomSeatRequest;
import com.uit.cinemaportalapi.payload.CreateSeatTypeRequest;
import com.uit.cinemaportalapi.payload.BookingSeatsRequest;
import com.uit.cinemaportalapi.service.RoomSeatService;
import com.uit.cinemaportalapi.service.ShowSeatService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"/show-seats", "/seat"})
public class ShowSeatController {

    @Autowired
    private ShowSeatService showSeatService;

    @Autowired
    private RoomSeatService roomSeatService;

    @GetMapping
    ResponseEntity<List<ShowSeat>> getShowSeats() {
        return ResponseEntity.ok(showSeatService.getShowSeats());
    }

    @GetMapping("/detail/{showSeatID}")
    ResponseEntity<ShowSeat> getShowSeatByID(@PathVariable(name = "showSeatID") Long showSeatID) {
        return ResponseEntity.ok(showSeatService.getShowSeatByID(showSeatID));
    }

    @GetMapping("/{showtimeID}")
    ResponseEntity<List<ShowSeat>> getSeatByShowTime(@PathVariable(name = "showtimeID") Long showtimeID){
        return ResponseEntity.ok(showSeatService.getSeatByShowTime(showtimeID));
    }

    @PostMapping("/types")
    ResponseEntity<SeatType> createSeatType(@RequestBody CreateSeatTypeRequest request) {
        return ResponseEntity.ok(roomSeatService.createSeatType(request));
    }

    @GetMapping("/types")
    ResponseEntity<List<SeatType>> getSeatTypes() {
        return ResponseEntity.ok(roomSeatService.getSeatTypes());
    }

    @PostMapping("/room-seats")
    ResponseEntity<RoomSeat> createRoomSeat(@RequestBody CreateRoomSeatRequest request) {
        return ResponseEntity.ok(roomSeatService.createRoomSeat(request));
    }

    @GetMapping("/room-seats")
    ResponseEntity<List<RoomSeat>> getRoomSeats() {
        return ResponseEntity.ok(roomSeatService.getRoomSeats());
    }

    @GetMapping("/room-seats/{id}")
    ResponseEntity<RoomSeat> getRoomSeatByID(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(roomSeatService.getRoomSeatByID(id));
    }

    @PutMapping("/room-seats/{id}")
    ResponseEntity<RoomSeat> updateRoomSeat(@PathVariable(name = "id") Long id, @RequestBody CreateRoomSeatRequest request) {
        return ResponseEntity.ok(roomSeatService.updateRoomSeat(id, request));
    }

    @DeleteMapping("/room-seats/{id}")
    ResponseEntity<String> deleteRoomSeat(@PathVariable(name = "id") Long id) {
        roomSeatService.deleteRoomSeat(id);
        return ResponseEntity.ok("Deleted room seat with id: " + id);
    }

    @GetMapping("/room-seats/screen/{screenID}")
    ResponseEntity<List<RoomSeat>> getRoomSeatsByScreen(@PathVariable(name = "screenID") Long screenID) {
        return ResponseEntity.ok(roomSeatService.getRoomSeatsByScreen(screenID));
    }

    @PatchMapping("/booking")
    ResponseEntity<List<ShowSeat>> bookingSeats(@RequestBody BookingSeatsRequest request){
        return ResponseEntity.ok(showSeatService.bookingSeats(request));
    }

    @DeleteMapping("/{showSeatID}")
    ResponseEntity<String> deleteShowSeat(@PathVariable(name = "showSeatID") Long showSeatID) {
        showSeatService.deleteShowSeat(showSeatID);
        return ResponseEntity.ok("Deleted show seat with id: " + showSeatID);
    }
}