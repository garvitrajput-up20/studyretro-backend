package com.studyretro.service;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public interface EmailService {

    @Async
    public CompletableFuture<Void> sendEmail(String to, String subject, String text);
    public boolean resendOtp(String email);

}
