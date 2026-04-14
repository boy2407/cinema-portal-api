package com.uit.cinemaportalapi.service;

import com.uit.cinemaportalapi.entity.RoomSeat;
import com.uit.cinemaportalapi.entity.SeatType;
import com.uit.cinemaportalapi.payload.CreateRoomSeatRequest;
import com.uit.cinemaportalapi.payload.CreateSeatTypeRequest;

import java.util.List;

public interface RoomSeatService {
    SeatType createSeatType(CreateSeatTypeRequest request);
    List<SeatType> getSeatTypes();
    List<RoomSeat> getRoomSeats();
    RoomSeat getRoomSeatByID(Long id);
    RoomSeat createRoomSeat(CreateRoomSeatRequest request);
    RoomSeat updateRoomSeat(Long id, CreateRoomSeatRequest request);
    void deleteRoomSeat(Long id);
    List<RoomSeat> getRoomSeatsByScreen(Long screenID);
}

