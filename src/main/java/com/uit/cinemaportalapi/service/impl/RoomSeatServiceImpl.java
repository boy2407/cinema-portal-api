package com.uit.cinemaportalapi.service.impl;

import com.uit.cinemaportalapi.entity.RoomSeat;
import com.uit.cinemaportalapi.entity.Screen;
import com.uit.cinemaportalapi.entity.SeatType;
import com.uit.cinemaportalapi.exception.BadRequestException;
import com.uit.cinemaportalapi.payload.CreateRoomSeatRequest;
import com.uit.cinemaportalapi.payload.CreateSeatTypeRequest;
import com.uit.cinemaportalapi.repository.RoomSeatRepository;
import com.uit.cinemaportalapi.repository.SeatTypeRepository;
import com.uit.cinemaportalapi.service.RoomSeatService;
import com.uit.cinemaportalapi.service.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomSeatServiceImpl implements RoomSeatService {

    @Autowired
    private SeatTypeRepository seatTypeRepository;

    @Autowired
    private RoomSeatRepository roomSeatRepository;

    @Autowired
    private ScreenService screenService;

    @Override
    public SeatType createSeatType(CreateSeatTypeRequest request) {
        try {
            if (request.getName() == null || request.getName().trim().isEmpty()) {
                throw new BadRequestException("Seat type name is required");
            }
            if (request.getSurcharge() == null) {
                throw new BadRequestException("Seat type surcharge is required");
            }
            if (seatTypeRepository.findByNameIgnoreCase(request.getName()).isPresent()) {
                throw new BadRequestException("Seat type already exists: " + request.getName());
            }

            SeatType seatType = new SeatType();
            seatType.setName(request.getName().trim().toUpperCase());
            seatType.setSurcharge(request.getSurcharge());
            return seatTypeRepository.save(seatType);
        } catch (Exception e) {
            throw new BadRequestException("Can not create SeatType: " + e.getMessage());
        }
    }

    @Override
    public List<SeatType> getSeatTypes() {
        try {
            return seatTypeRepository.findAll();
        } catch (Exception e) {
            throw new BadRequestException("Can not find SeatType: " + e.getMessage());
        }
    }

    @Override
    public List<RoomSeat> getRoomSeats() {
        try {
            return roomSeatRepository.findAll();
        } catch (Exception e) {
            throw new BadRequestException("Can not find all RoomSeat: " + e.getMessage());
        }
    }

    @Override
    public RoomSeat getRoomSeatByID(Long id) {
        try {
            return roomSeatRepository.findById(id)
                    .orElseThrow(() -> new BadRequestException("RoomSeat not found with id: " + id));
        } catch (Exception e) {
            throw new BadRequestException("Can not find RoomSeat by id: " + e.getMessage());
        }
    }

    @Override
    public RoomSeat createRoomSeat(CreateRoomSeatRequest request) {
        try {
            Screen screen = validateAndGetScreen(request);
            SeatType seatType = validateAndGetSeatType(request);
            String seatRow = normalizeSeatRow(request.getSeatRow());
            Integer seatNumber = validateSeatNumber(request.getSeatNumber());

            if (roomSeatRepository.existsByScreenAndSeatRowAndSeatNumber(screen, seatRow, seatNumber)) {
                throw new BadRequestException("Seat already exists for this screen: " + seatRow + seatNumber);
            }

            RoomSeat roomSeat = new RoomSeat();
            roomSeat.setScreen(screen);
            roomSeat.setSeatRow(seatRow);
            roomSeat.setSeatNumber(seatNumber);
            roomSeat.setType(seatType);
            return roomSeatRepository.save(roomSeat);
        } catch (Exception e) {
            throw new BadRequestException("Can not create RoomSeat: " + e.getMessage());
        }
    }

    @Override
    public RoomSeat updateRoomSeat(Long id, CreateRoomSeatRequest request) {
        try {
            RoomSeat existingRoomSeat = getRoomSeatByID(id);

            Screen screen = request.getScreenID() != null
                    ? screenService.findScreenByID(request.getScreenID())
                    : existingRoomSeat.getScreen();
            SeatType seatType = request.getSeatTypeID() != null
                    ? seatTypeRepository.findById(request.getSeatTypeID())
                    .orElseThrow(() -> new BadRequestException("SeatType not found with id: " + request.getSeatTypeID()))
                    : existingRoomSeat.getType();

            String seatRow = request.getSeatRow() != null
                    ? normalizeSeatRow(request.getSeatRow())
                    : existingRoomSeat.getSeatRow();
            Integer seatNumber = request.getSeatNumber() != null
                    ? validateSeatNumber(request.getSeatNumber())
                    : existingRoomSeat.getSeatNumber();

            if (roomSeatRepository.existsByScreenAndSeatRowAndSeatNumberAndIdNot(screen, seatRow, seatNumber, id)) {
                throw new BadRequestException("Seat already exists for this screen: " + seatRow + seatNumber);
            }

            existingRoomSeat.setScreen(screen);
            existingRoomSeat.setType(seatType);
            existingRoomSeat.setSeatRow(seatRow);
            existingRoomSeat.setSeatNumber(seatNumber);
            return roomSeatRepository.save(existingRoomSeat);
        } catch (Exception e) {
            throw new BadRequestException("Can not update RoomSeat: " + e.getMessage());
        }
    }

    @Override
    public void deleteRoomSeat(Long id) {
        try {
            RoomSeat existingRoomSeat = getRoomSeatByID(id);
            roomSeatRepository.delete(existingRoomSeat);
        } catch (Exception e) {
            throw new BadRequestException("Can not delete RoomSeat: " + e.getMessage());
        }
    }

    @Override
    public List<RoomSeat> getRoomSeatsByScreen(Long screenID) {
        try {
            screenService.findScreenByID(screenID);
            return roomSeatRepository.findAllByScreenId(screenID);
        } catch (Exception e) {
            throw new BadRequestException("Can not find RoomSeat by screen: " + e.getMessage());
        }
    }

    private Screen validateAndGetScreen(CreateRoomSeatRequest request) {
        if (request.getScreenID() == null) {
            throw new BadRequestException("screenID is required");
        }
        return screenService.findScreenByID(request.getScreenID());
    }

    private SeatType validateAndGetSeatType(CreateRoomSeatRequest request) {
        if (request.getSeatTypeID() == null) {
            throw new BadRequestException("seatTypeID is required");
        }
        return seatTypeRepository.findById(request.getSeatTypeID())
                .orElseThrow(() -> new BadRequestException("SeatType not found with id: " + request.getSeatTypeID()));
    }

    private String normalizeSeatRow(String seatRow) {
        if (seatRow == null || seatRow.trim().isEmpty()) {
            throw new BadRequestException("seatRow is required");
        }
        return seatRow.trim().toUpperCase();
    }

    private Integer validateSeatNumber(Integer seatNumber) {
        if (seatNumber == null || seatNumber <= 0) {
            throw new BadRequestException("seatNumber must be greater than 0");
        }
        return seatNumber;
    }
}

