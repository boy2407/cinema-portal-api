package com.uit.cinemaportalapi.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.uit.cinemaportalapi.entity.RoomSeat;
import com.uit.cinemaportalapi.entity.ShowSeat;
import com.uit.cinemaportalapi.entity.ShowTime;
import com.uit.cinemaportalapi.entity.Ticket;
import com.uit.cinemaportalapi.exception.BadRequestException;
import com.uit.cinemaportalapi.payload.BookingSeatsRequest;
import com.uit.cinemaportalapi.repository.RoomSeatRepository;
import com.uit.cinemaportalapi.repository.ShowSeatRepository;
import com.uit.cinemaportalapi.repository.ShowTimeRepository;
import com.uit.cinemaportalapi.service.ShowSeatService;
import com.uit.cinemaportalapi.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ShowSeatServiceImpl implements ShowSeatService {

    @Autowired
    ShowSeatRepository showSeatRepository;

    @Autowired
    ShowTimeRepository showTimeRepository;

    @Autowired
    TicketService ticketService;

    @Autowired
    RoomSeatRepository roomSeatRepository;

    @Override
    public List<ShowSeat> getShowSeats() {
        try {
            return showSeatRepository.findAll();
        } catch (Exception e) {
            throw new BadRequestException("Can not find all ShowSeat: " + e.getMessage());
        }
    }

    @Override
    public ShowSeat getShowSeatByID(Long showSeatID) {
        try {
            return showSeatRepository.findById(showSeatID)
                    .orElseThrow(() -> new BadRequestException("ShowSeat not found with id: " + showSeatID));
        } catch (Exception e) {
            throw new BadRequestException("Can not find ShowSeat by id: " + e.getMessage());
        }
    }

    @Override
    public List<ShowSeat> getSeatByShowTime(Long showtimeID) {
        try {
            Optional<ShowTime> showTime = showTimeRepository.findById(showtimeID);
            if (showTime.isEmpty()) {
                throw new BadRequestException("ShowTime not found with id: " + showtimeID);
            }
            return showSeatRepository.findAllByShowTime(showTime.get());
        } catch (Exception e) {
            throw new BadRequestException("Can not find seats for show time: " + e.getMessage());
        }
    }

    @Override
    public List<ShowSeat> createSeatsForShowTime(ShowTime showTime) {
        try {
            // Check if seats already exist for this showtime
            List<ShowSeat> existingSeats = showSeatRepository.findAllByShowTime(showTime);
            if (!existingSeats.isEmpty()) {
                return existingSeats;
            }

            List<RoomSeat> roomSeats = roomSeatRepository.findAllByScreen(showTime.getScreen());
            if (roomSeats.isEmpty()) {
                throw new BadRequestException("Room seat layout is empty for screen id: " + showTime.getScreen().getId() + ". Create SeatType and RoomSeat first.");
            }

            List<ShowSeat> showSeats = new ArrayList<>();
            for (RoomSeat roomSeat : roomSeats) {
                ShowSeat showSeat = new ShowSeat();
                showSeat.setShowTime(showTime);
                showSeat.setRoomSeat(roomSeat);
                showSeat.setIsReserved(false);
                double surcharge = roomSeat.getType() != null && roomSeat.getType().getSurcharge() != null
                        ? roomSeat.getType().getSurcharge()
                        : 0D;
                double basePrice = showTime.getPrice() != null ? showTime.getPrice().doubleValue() : 0D;
                showSeat.setPrice(basePrice + surcharge);

                showSeats.add(showSeat);
            }

            showSeatRepository.saveAll(showSeats);
            return showSeats;
        } catch (Exception e) {
            throw new BadRequestException("Can not create seats for show time: " + e.getMessage());
        }
    }

    @Override
    public List<ShowSeat> bookingSeats(BookingSeatsRequest request) {
        try {
            List<ShowSeat> seats = showSeatRepository.findAllById(request.getSeatIDs());

            Ticket ticket = ticketService.createTicket(seats, request.getUserID(), request.getSubtotal());

            for (ShowSeat seat : seats) {
                seat.setTicket(ticket);
                seat.setIsReserved(true);
            }

            String content = ticket.toString();
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 350, 350);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = Base64.getEncoder().encode(pngOutputStream.toByteArray());
            ticket.setQrCode(new String(pngData));

            return showSeatRepository.saveAll(seats);
        } catch (Exception e) {
            throw new BadRequestException("Can not booking seats: " + e.getMessage());
        }
    }

    @Override
    public void deleteShowSeat(Long showSeatID) {
        try {
            ShowSeat showSeat = getShowSeatByID(showSeatID);
            showSeatRepository.delete(showSeat);
        } catch (Exception e) {
            throw new BadRequestException("Can not delete ShowSeat: " + e.getMessage());
        }
    }

}
