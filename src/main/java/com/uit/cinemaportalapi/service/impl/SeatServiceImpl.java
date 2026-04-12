package com.uit.cinemaportalapi.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.uit.cinemaportalapi.entity.Seat;
import com.uit.cinemaportalapi.entity.ShowTime;
import com.uit.cinemaportalapi.entity.Ticket;
import com.uit.cinemaportalapi.exception.BadRequestException;
import com.uit.cinemaportalapi.payload.BookingSeatsRequest;
import com.uit.cinemaportalapi.repository.SeatRepository;
import com.uit.cinemaportalapi.service.SeatService;
import com.uit.cinemaportalapi.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    TicketService ticketService;

    @Override
    public List<Seat> getSeatByShowTime(Long showtimeID) {
        try {
            return seatRepository.findAllByShowtimeId(showtimeID);
        } catch (Exception e) {
            throw new BadRequestException("Can not find seats for show time: " + e.getMessage());
        }
    }

    @Override
    public List<Seat> createSeatsForShowTime(ShowTime showtimeID) {
        try {
            String[] myIntArray = {"NONE", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};

            // 108 + 6 = 114
            int numOfSeats = 114;
            int numOfRow = 9;
            int numOfColumn = 12;
            List<Seat> seats = new ArrayList<>();

            for (int r = 1; r <= numOfRow; r++) {
                for (int n = 1; n <= numOfColumn; n++) {
                    Seat seat = new Seat();
                    seat.setRow(myIntArray[r]);
                    seat.setNumber(n);
                    if (r <= 8 && r >= 5 && n <= 9 && n >= 4) {
                        seat.setType("VIP");
                    } else {
                        seat.setType("NORMAL");
                    }
                    seat.setIsBooked(false);
                    seat.setShowtime(showtimeID);
                    seats.add(seat);
                }
            }

            for (int n = 1; n <= 6; n++) {
                Seat seat = new Seat();
                seat.setRow("K");
                seat.setNumber(n);
                seat.setType("COUPLE");
                seat.setIsBooked(false);
                seat.setShowtime(showtimeID);
                seats.add(seat);
            }
            seatRepository.saveAll(seats);
            return seats;
        } catch (Exception e) {
            throw new BadRequestException("Can not create seats for show time: " + e.getMessage());
        }
    }

    @Override
    public List<Seat> bookingSeats(BookingSeatsRequest request) {
        try {
            List<Seat> seats = seatRepository.findAllById(request.getSeatIDs());

            Ticket ticket = ticketService.createTicket(seats, request.getUserID(), request.getSubtotal());

            for (Seat seat : seats) {
                seat.setTicket(ticket);
                seat.setIsBooked(true);
            }

            String content = ticket.toString();
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 350, 350);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
            byte[] pngData = Base64.getEncoder().encode(pngOutputStream.toByteArray());
            ticket.setQrCode(new String(pngData));

            return seatRepository.saveAll(seats);
        } catch (Exception e) {
            throw new BadRequestException("Can not booking seats: " + e.getMessage());
        }
    }
}
