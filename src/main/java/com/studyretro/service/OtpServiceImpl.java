package com.studyretro.service;

import com.studyretro.entity.OtpInfo;
import com.studyretro.entity.Users;
import com.studyretro.repository.OtpInfoRepository;
import com.studyretro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OtpServiceImpl implements OtpService {


    public static final int OTP_EXPIRY_MINUTES = 5;
    public static final int OTP_RESET_WAITING_TIME_MINUTES = 10;
    public static final int OTP_RETRY_LIMIT_WINDOW_MINUTES = 15;

    @Autowired
    private OtpInfoRepository otpInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean validateOTP(String email, String otp) {
        // Fetch OTP info from repository
        OtpInfo otpInfo = otpInfoRepository.findByEmailAndOtp(email, otp);

        if (otpInfo == null) {
            return false;
        }

        // Check if the OTP is expired
        LocalDateTime now = LocalDateTime.now();
        if (otpInfo.getGeneratedAt().plusMinutes(OTP_EXPIRY_MINUTES).isBefore(now)) {
            return false;
        }

        Optional<Users> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            user.setVerified(true);
            userRepository.save(user);
            otpInfoRepository.delete(otpInfo);
            return true;
        }

        return false;
    }
}
