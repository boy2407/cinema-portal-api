package com.uit.cinemaportalapi.payload.dto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String  username;
    private String  password;
    private String  email;
}
