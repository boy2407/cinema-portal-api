package com.uit.cinemaportalapi.controller;


import com.uit.cinemaportalapi.entity.User;
import com.uit.cinemaportalapi.payload.ResponseObject;
import com.uit.cinemaportalapi.payload.dto.EmailRequestDTO;
import com.uit.cinemaportalapi.payload.dto.EmailVerificationDTO;
import com.uit.cinemaportalapi.payload.dto.UserDTO;
import com.uit.cinemaportalapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAllUser")
    ResponseEntity<ResponseObject> getAllUser(){
          List<User> users = userService.getAllUser();
        return users.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).body( new ResponseObject("404", null )):
                ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("200",users));

    }
    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody UserDTO userDTO){
        ResponseObject data  = userService.registerUser(userDTO);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/login")
    ResponseEntity<ResponseObject> login(@RequestBody UserDTO userDTO){
        ResponseObject data  = userService.Login(userDTO);
        return new ResponseEntity<>(data,HttpStatus.OK);
    }


    @GetMapping("/verify-email")
    ResponseEntity<ResponseObject> verifyEmailByToken(@RequestParam("token") String token) {
        ResponseObject data = userService.verifyEmail(new EmailVerificationDTO(token));
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/resend-verification")
    ResponseEntity<ResponseObject> resendVerification(@RequestBody EmailRequestDTO dto) {
        ResponseObject data = userService.resendVerificationCode(dto);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
