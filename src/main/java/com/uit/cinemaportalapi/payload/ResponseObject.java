package com.uit.cinemaportalapi.payload;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject {
    private String status;
    private  Object data ;
}
