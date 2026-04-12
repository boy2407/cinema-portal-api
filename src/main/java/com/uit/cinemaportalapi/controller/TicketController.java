package com.uit.cinemaportalapi.controller;


import com.uit.cinemaportalapi.entity.Movie;
import com.uit.cinemaportalapi.payload.dto.PaymentHistoryDTO;
import com.uit.cinemaportalapi.payload.dto.TicketInfoDTO;
import com.uit.cinemaportalapi.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @GetMapping("/ticket-info/{ticketID}")
     ResponseEntity<TicketInfoDTO>getTicketInfo(@PathVariable(name = "ticketID") Long ticketID){
        return ResponseEntity.ok(ticketService.getTicketInfo(ticketID));

    }
    @GetMapping("/history-payment/{userID}")
    ResponseEntity<List<PaymentHistoryDTO>>getTicketsByUserId(@PathVariable(name = "userID") Long userID){
        return ResponseEntity.ok(ticketService.getTicketsByUserId(userID));

    }
}
