package com.uit.cinemaportalapi.service.impl;

import com.uit.cinemaportalapi.entity.*;
import com.uit.cinemaportalapi.exception.BadRequestException;
import com.uit.cinemaportalapi.payload.dto.PaymentHistoryDTO;
import com.uit.cinemaportalapi.payload.dto.TicketInfoDTO;
import com.uit.cinemaportalapi.repository.ShowSeatRepository;
import com.uit.cinemaportalapi.repository.ShowTimeRepository;
import com.uit.cinemaportalapi.repository.TicketRepository;
import com.uit.cinemaportalapi.repository.UserRepository;
import com.uit.cinemaportalapi.service.TicketService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    // string stringbuilder


    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public TicketInfoDTO getTicketInfo(Long ticketId) {

        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);

        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            ShowSeat showSeat = ticket.getShowSeats().get(0);
            ShowTime showTime = showSeat.getShowTime();
            Movie movie = showTime.getMovie();
            Cinema cinema = showTime.getCinema();
            TicketInfoDTO ticketInfoDTO = new TicketInfoDTO();
            ticketInfoDTO.setQrCode(ticket.getQrCode());
            ticketInfoDTO.setMovieTitle(movie.getTitle());
            ticketInfoDTO.setShowDate(showTime.getStartTime());
            ticketInfoDTO.setShowTime(showTime.getStartTime());
            ticketInfoDTO.setCinemaName(cinema.getName());
            ticketInfoDTO.setBookingNumber(ticket.getBookingNumber());
            ticketInfoDTO.setPosterURL(movie.getPosterURL());

            ticketInfoDTO.setScreenName(showTime.getCinema().getName());

            ticketInfoDTO.setSubtotal(ticket.getSubtotal());
            ticketInfoDTO.setRating(movie.getRating());

            return ticketInfoDTO;
        }
       return  null;
    }

    @Transactional
    @Override
    public List<PaymentHistoryDTO> getTicketsByUserId(Long userId) {
        List<Ticket> tickets = ticketRepository.findAllByUserId(userId);

        return tickets.stream()
                .map(this::mapToTicketInfoDTO)
                .collect(Collectors.toList());
    }

    private PaymentHistoryDTO mapToTicketInfoDTO(Ticket ticket) {
        ShowSeat showSeat = ticket.getShowSeats().get(0);
        ShowTime showTime = showSeat.getShowTime();
        Movie movie = showTime.getMovie();
        Cinema cinema = showTime.getCinema();
        PaymentHistoryDTO paymentHistoryDTO = new PaymentHistoryDTO();
        paymentHistoryDTO.setTicketID(ticket.getId());
        paymentHistoryDTO.setMovieTitle(movie.getTitle());
        paymentHistoryDTO.setCinemaName(cinema.getName());
        paymentHistoryDTO.setShowDate(showTime.getStartTime());
        paymentHistoryDTO.setShowTime(showTime.getStartTime());
        paymentHistoryDTO.setPosterURL(movie.getPosterURL());
        paymentHistoryDTO.setEndTime(showTime.getEndTime());
        paymentHistoryDTO.setBookingNumber(ticket.getBookingNumber());
        List<String> seatList = new ArrayList<>();
        for (ShowSeat seat : ticket.getShowSeats()) {
            seatList.add(seat.getRoomSeat().getSeatRow() + seat.getRoomSeat().getSeatNumber());
        }
        String seats = String.join(", ", seatList);
        paymentHistoryDTO.setSeats(seats);
        paymentHistoryDTO.setScreen(showTime.getScreen().getScreenCode());
        paymentHistoryDTO.setSubtotal(ticket.getSubtotal());
        paymentHistoryDTO.setQrCode(ticket.getQrCode());
        return paymentHistoryDTO;
    }

    @Override
    public Ticket createTicket(List<ShowSeat> seats, Long userId, BigDecimal subtotal) {
        Ticket ticket = new Ticket();
        Optional<User> user= userRepository.findById(userId);

        if (user.isPresent()) {
            ticket.setShowSeats(seats);
            ticket.setUser(user.get());
            ticket.setSubtotal(subtotal);
            LocalDateTime now = LocalDateTime.now();

            String bookingNumber = Integer.toString(now.getYear()) + Integer.toString(now.getMonthValue())  + Integer.toString(now.getDayOfMonth());

            for (ShowSeat seat : seats) {
                bookingNumber = bookingNumber + seat.getRoomSeat().getSeatRow() + Integer.toString(seat.getRoomSeat().getSeatNumber());
            }
            ticket.setBookingNumber(bookingNumber);
        }

        return ticketRepository.save(ticket);
    }
}
