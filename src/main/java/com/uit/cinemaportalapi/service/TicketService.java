package com.uit.cinemaportalapi.service;

import com.uit.cinemaportalapi.entity.Seat;
import com.uit.cinemaportalapi.entity.Ticket;
import com.uit.cinemaportalapi.payload.dto.PaymentHistoryDTO;
import com.uit.cinemaportalapi.payload.dto.TicketInfoDTO;

import java.math.BigDecimal;
import java.util.List;

public interface TicketService {

    TicketInfoDTO getTicketInfo(Long ticketId);
    List<PaymentHistoryDTO> getTicketsByUserId(Long userID);

    Ticket createTicket(List<Seat> seats, Long userID, BigDecimal subtotal);

}
