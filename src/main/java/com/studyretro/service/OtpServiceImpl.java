package com.studyretro.service;

import java.util.concurrent.CompletableFuture;

public class OtpServiceImpl implements OtpService{

    public static final int OTP_ATTEMPTS_LIMIT = 3;
    public static final int OTP_EXPIRY_MINUTES = 5;
    public static final int OTP_RESET_WAITING_TIME_MINUTES = 10;
    public static final int OTP_RETRY_LIMIT_WINDOW_MINUTES = 15;

    @Override
    public String generateOTP(String email) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> sendOTPByEmail(String email, String name, String otp) {
        return null;
    }

    @Override
    public boolean validateOTP(String accountNumber, String otp) {
        return false;
    }
}
