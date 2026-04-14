package com.uit.cinemaportalapi.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSeatTypeRequest {
    private String name;
    private Double surcharge;
}

