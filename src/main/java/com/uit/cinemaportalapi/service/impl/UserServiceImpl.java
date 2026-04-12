package com.uit.cinemaportalapi.service.impl;


import com.uit.cinemaportalapi.entity.User;
import com.uit.cinemaportalapi.payload.ResponseObject;
import com.uit.cinemaportalapi.payload.dto.UserDTO;
import com.uit.cinemaportalapi.repository.UserRepository;
import com.uit.cinemaportalapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public ResponseObject registerUser(UserDTO dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            return new ResponseObject("fail", "Username already exists");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            return new ResponseObject("fail", "Email already exists");
        }

        User newUser = new User();
        newUser.setUsername(dto.getUsername());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        System.out.println("Password: " + passwordEncoder.encode(dto.getPassword()));
        newUser.setEnabled(true);
        newUser.setEmail(dto.getEmail());
        newUser.setCreateDate(new Date());
        userRepository.save(newUser);
        return new ResponseObject("success", "Register success");
    }

    @Override
    public ResponseObject Login(UserDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername());
        if (user == null) {
            return new ResponseObject("fail", "can't found user :" + dto.getUsername());
        }

        if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return new ResponseObject("success", user);
        }
        return new ResponseObject(" fail", null);
    }


}
