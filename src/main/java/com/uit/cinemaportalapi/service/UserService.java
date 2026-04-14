package com.uit.cinemaportalapi.service;

import com.uit.cinemaportalapi.entity.User;
import com.uit.cinemaportalapi.payload.ResponseObject;
import com.uit.cinemaportalapi.payload.dto.EmailRequestDTO;
import com.uit.cinemaportalapi.payload.dto.EmailVerificationDTO;
import com.uit.cinemaportalapi.payload.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<User> getAllUser();
    ResponseObject registerUser(UserDTO dto);
    ResponseObject Login(UserDTO dto);
    ResponseObject verifyEmail(EmailVerificationDTO dto);
    ResponseObject resendVerificationCode(EmailRequestDTO dto);
}
