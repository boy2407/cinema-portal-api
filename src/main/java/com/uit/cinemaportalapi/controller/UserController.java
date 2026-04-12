package com.uit.cinemaportalapi.controller;


import com.uit.cinemaportalapi.entity.User;
import com.uit.cinemaportalapi.payload.ResponseObject;
import com.uit.cinemaportalapi.payload.dto.UserDTO;
import com.uit.cinemaportalapi.service.UserService;
import com.uit.cinemaportalapi.service.impl.UserServiceImpl;
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
}
