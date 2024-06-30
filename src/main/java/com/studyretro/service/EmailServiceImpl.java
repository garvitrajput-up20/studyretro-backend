package com.studyretro.service;

import com.studyretro.entity.OtpInfo;
import com.studyretro.repository.OtpInfoRepository;
import com.studyretro.utils.OtpUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Autowired
    private final JavaMailSender mailSender;
    @Autowired
    private OtpInfoRepository otpInfoRepository;


    @Async
    @Override
    public CompletableFuture<Void> sendEmail(String to, String subject, String text) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        try {
            String otp = OtpUtil.generateOtp();
            text = "Your OTP to verify the email is: " + otp;

            OtpInfo otpInfo = new OtpInfo();
            otpInfo.setEmail(to);
            otpInfo.setOtp(otp);
            otpInfo.setGeneratedAt(LocalDateTime.now());
            otpInfoRepository.save(otpInfo);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(message);

            log.info("Sent email to {}", to);
            future.complete(null);

        } catch (MessagingException | MailException e) {
            log.error("Failed to send email to {}", to, e);
            future.completeExceptionally(e);
        }

        return future;
    }
}
