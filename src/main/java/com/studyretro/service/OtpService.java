package com.studyretro.service;

import java.util.concurrent.CompletableFuture;

public interface OtpService {

    public boolean validateOTP(String email, String otp);
}