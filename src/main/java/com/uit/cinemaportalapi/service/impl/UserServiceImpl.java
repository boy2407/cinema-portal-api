package com.uit.cinemaportalapi.service.impl;


import com.uit.cinemaportalapi.entity.User;
import com.uit.cinemaportalapi.payload.ResponseObject;
import com.uit.cinemaportalapi.payload.dto.EmailRequestDTO;
import com.uit.cinemaportalapi.payload.dto.EmailVerificationDTO;
import com.uit.cinemaportalapi.payload.dto.UserDTO;
import com.uit.cinemaportalapi.repository.UserRepository;
import com.uit.cinemaportalapi.service.JwtService;
import com.uit.cinemaportalapi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("${app.email-verification.from}")
    private String emailFrom;

    @Value("${app.email-verification.mock-send}")
    private boolean mockSend;

    @Value("${app.email-verification.expire-minutes}")
    private int verificationExpireMinutes;

    @Value("${app.email-verification.verify-url}")
    private String verificationUrl;

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
        newUser.setEnabled(false);
        newUser.setEmail(dto.getEmail());
        newUser.setCreateDate(new Date());
        userRepository.save(newUser);

        String verificationToken = jwtService.generateEmailVerificationToken(newUser);
        boolean sent = sendVerificationEmail(newUser.getEmail(), verificationToken);
        if (!sent) {
            return new ResponseObject("fail", "Cannot send verification email");
        }

        String verificationLink = buildVerificationLink(verificationToken);
        if (mockSend) {
            return new ResponseObject("success", "Register success. Verification link (dev): " + verificationLink);
        }
        return new ResponseObject("success", "Register success. Please check your email to verify account.");
    }

    @Override
    public ResponseObject Login(UserDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername());
        if (user == null) {
            return new ResponseObject("fail", "can't found user :" + dto.getUsername());
        }

        if (!Boolean.TRUE.equals(user.getEnabled())) {
            return new ResponseObject("fail", "Email is not verified");
        }

        if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            String token = jwtService.generateToken(user);
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("type", "Bearer");
            responseData.put("token", token);
            responseData.put("user", user);
            return new ResponseObject("success", responseData);
        }
        return new ResponseObject(" fail", null);
    }

    @Override
    public ResponseObject verifyEmail(EmailVerificationDTO dto) {
        String email = jwtService.validateAndExtractEmailFromVerificationToken(dto.getToken());
        if (email == null) {
            return new ResponseObject("fail", "Verification token is invalid or expired");
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            return new ResponseObject("fail", "Email not found");
        }

        if (Boolean.TRUE.equals(user.getEnabled())) {
            return new ResponseObject("success", "Email already verified");
        }

        user.setEnabled(true);
        userRepository.save(user);

        return new ResponseObject("success", "Email verified successfully");
    }

    @Override
    public ResponseObject resendVerificationCode(EmailRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail());
        if (user == null) {
            return new ResponseObject("fail", "Email not found");
        }

        if (Boolean.TRUE.equals(user.getEnabled())) {
            return new ResponseObject("fail", "Email is already verified");
        }

        String verificationToken = jwtService.generateEmailVerificationToken(user);
        boolean sent = sendVerificationEmail(user.getEmail(), verificationToken);
        if (!sent) {
            return new ResponseObject("fail", "Cannot send verification email");
        }

        String verificationLink = buildVerificationLink(verificationToken);
        if (mockSend) {
            return new ResponseObject("success", "Resent verification link (dev): " + verificationLink);
        }
        return new ResponseObject("success", "Verification email has been resent");
    }

    private boolean sendVerificationEmail(String toEmail, String verificationToken) {
        String verifyLink = buildVerificationLink(verificationToken);

        if (mockSend || mailSender == null) {
            LOGGER.info("Email verification link for {} is {}", toEmail, verifyLink);
            return true;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(toEmail);
            message.setSubject("Cinema Portal - Verify Your Email");
            message.setText("Click the link below to verify your email:\n" + verifyLink + "\n\nThis link will expire in " + verificationExpireMinutes + " minutes.");
            mailSender.send(message);
            return true;
        } catch (Exception ex) {
            LOGGER.error("Unable to send verification email to {}", toEmail, ex);
            return false;
        }
    }

    private String buildVerificationLink(String token) {
        return verificationUrl + token;
    }



}
