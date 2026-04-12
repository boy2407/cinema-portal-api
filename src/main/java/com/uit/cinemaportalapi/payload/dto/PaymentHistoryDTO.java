package com.uit.cinemaportalapi.payload.dto;

import com.uit.cinemaportalapi.entity.Movie;
import com.uit.cinemaportalapi.entity.ShowTime;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentHistoryDTO {

    private Long ticketID;
    private String movieTitle;
    private String cinemaName;
    private Date showDate;
    private Date showTime;
    private Date endTime;
    private String posterURL;
    private String bookingNumber;
    private String Screen;
    private BigDecimal subtotal;
    private String seats;
    private String qrCode;
}
