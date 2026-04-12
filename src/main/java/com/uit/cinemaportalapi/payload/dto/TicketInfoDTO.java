package com.uit.cinemaportalapi.payload.dto;


import lombok.*;

import java.math.BigDecimal;
import java.util.Date;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketInfoDTO {
    private String qrCode;
    private String movieTitle;
    private Date showDate;
    private Date showTime;
    private String cinemaName;
    private String bookingNumber;
    private String posterURL;
    private String seatNumbers;
    private String screenName;
    private BigDecimal subtotal;
    private String rating;
}
