package com.studyretro.service;

import com.studyretro.entity.OtpInfo;
import com.studyretro.entity.Users;
import com.studyretro.exceptions.InvalidOtpException;
import com.studyretro.repository.OtpInfoRepository;
import com.studyretro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OtpServiceImpl implements OtpService {

    public static final int OTP_EXPIRY_MINUTES = 5;

    @Autowired
    private OtpInfoRepository otpInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean validateOTP(String email, String otp) {
        // Fetch OTP info from repository
        OtpInfo otpInfo = otpInfoRepository.findByEmailAndOtp(email, otp);

        if (otpInfo == null) {
            throw new InvalidOtpException("Invalid OTP. Please enter a valid OTP.");
        }

        // Check if the OTP is expired
        LocalDateTime now = LocalDateTime.now();
        if (otpInfo.getGeneratedAt().plusMinutes(OTP_EXPIRY_MINUTES).isBefore(now)) {
            throw new InvalidOtpException("OTP has expired. Please request a new OTP.");
        }

        // Fetch user by email
        Optional<Users> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            user.setVerified(true);
            userRepository.save(user);
            otpInfoRepository.delete(otpInfo);
            return true;
        } else {
            throw new InvalidOtpException("User not found for the provided email.");
        }
    }
}
