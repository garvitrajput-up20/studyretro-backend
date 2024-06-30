package com.studyretro.service;

import java.util.concurrent.CompletableFuture;

public interface OtpService {

    String generateOTP(String accountNumber);

    public CompletableFuture<Boolean> sendOTPByEmail(String email, String name, String otp) ;
    public boolean validateOTP(String email, String otp);
}