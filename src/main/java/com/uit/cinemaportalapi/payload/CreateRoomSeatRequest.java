package com.uit.cinemaportalapi.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRoomSeatRequest {
    private Long screenID;
    private String seatRow;
    private Integer seatNumber;
    private Long seatTypeID;
}

