package com.studyretro.controllers;

import com.studyretro.dto.LoginDto;
import com.studyretro.dto.OtpVerificationDto;
import com.studyretro.entity.Users;
import com.studyretro.service.EmailService;
import com.studyretro.service.OtpServiceImpl;
import com.studyretro.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OtpServiceImpl otpVerificationService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Users user) {
        Users registeredUser = userService.registerUser(user);

        // Send OTP email
        String subject = "Email Verification OTP";
        String text = "";
        CompletableFuture<Void> emailFuture = emailService.sendEmail(user.getEmail(), subject, text);

        return ResponseEntity.ok().body("User Registered. Please check your email for the OTP.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto) {
        String result = userService.login(loginDto);
        if ("Not Authenticated".equals(result)) {
            return ResponseEntity.status(401).body("Authentication Failed");
        } else if ("User Not Verified".equals(result)) {
            return ResponseEntity.status(403).body("User Not Verified");
        }

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpVerificationDto otpVerificationDto) {
        boolean isVerified = otpVerificationService.validateOTP(otpVerificationDto.getEmail(), otpVerificationDto.getOtp());
        if (isVerified) {
            return ResponseEntity.ok().body("OTP Verified. User is now verified.");
        } else {
            return ResponseEntity.status(400).body("Invalid OTP");
        }
    }

    @PostMapping("/deleteAllUsers")
    public String delete() {
        userService.delete();
        return "Deleted";
    }
    @GetMapping("/findAllUsers")
    public List<?> getAll(Users users){
        return userService.getUsers(users);
    }
}
