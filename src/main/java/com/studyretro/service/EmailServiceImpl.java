package com.studyretro.service;

import com.studyretro.entity.OtpInfo;
import com.studyretro.entity.Users;
import com.studyretro.repository.OtpInfoRepository;
import com.studyretro.repository.UserRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Autowired
    private final JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OtpInfoRepository otpInfoRepository;

    @Async
    @Override
    public CompletableFuture<Void> sendEmail(String to, String subject, String text) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        try {
            String otp = OtpUtil.generateOtp();

            // HTML template with the OTP inserted
            String htmlTemplate = "<!DOCTYPE html>" +
                    "<html lang=\"en\">" +
                    "<head>" +
                    "    <meta charset=\"UTF-8\">" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                    "    <title>OTP Verification</title>" +
                    "    <style>" +
                    "        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; border: 1px solid}" +
                    "        .container { width: 100%; padding: 20px; background-color: #ffffff; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); margin: 20px auto; max-width: 600px; border-radius: 8px; }" +
                    "        .header { text-align: center; padding: 10px 0; border-bottom: 1px solid #dddddd; }" +
                    "        .header h1 { margin: 0; color: #333333; }" +
                    "        .content { padding: 20px; text-align: center; }" +
                    "        .content p { font-size: 16px; color: #666666; }" +
                    "        .otp { font-size: 24px; font-weight: bold; color: #333333; margin: 20px 0; }" +
                    "        .footer { text-align: center; padding: 10px 0; border-top: 1px solid #dddddd; margin-top: 20px; }" +
                    "        .footer p { font-size: 12px; color: #999999; }" +
                    "    </style>" +
                    "</head>" +
                    "<body>" +
                    "    <div class=\"container\">" +
                    "        <div class=\"header\">" +
                    "            <h1>Email Verification</h1>" +
                    "        </div>" +
                    "        <div class=\"content\">" +
                    "            <p>Dear User,</p>" +
                    "            <p>Thank you for registering with us. To complete your registration, please use the following One-Time Password (OTP) to verify your email address:</p>" +
                    "            <div class=\"otp\">" + otp + "</div>" +
                    "            <p>This OTP is valid for 5 minutes. If you did not request this verification, please ignore this email.</p>" +
                    "            <p>Best regards,<br>StudyRetro</p>" +
                    "        </div>" +
                    "        <div class=\"footer\">" +
                    "            <p>&copy; 2024 StudyRetro | All rights reserved.</p>" +
                    "        </div>" +
                    "    </div>" +
                    "</body>" +
                    "</html>";

            OtpInfo otpInfo = new OtpInfo();
            otpInfo.setEmail(to);
            otpInfo.setOtp(otp);
            otpInfo.setGeneratedAt(LocalDateTime.now());
            otpInfoRepository.save(otpInfo);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlTemplate, true); // true indicates the content is HTML
            mailSender.send(message);

            log.info("Sent email to {}", to);
            future.complete(null);

        } catch (MessagingException | MailException e) {
            log.error("Failed to send email to {}", to, e);
            future.completeExceptionally(e);
        }

        return future;
    }

    @Override
    @Transactional
    public boolean resendOtp(String email) {
        Optional<Users> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            // User exists, delete any existing OTP and resend
            Optional<OtpInfo> existingOtp = Optional.ofNullable(otpInfoRepository.findByEmail(email));
            existingOtp.ifPresent(otpInfo -> otpInfoRepository.delete(otpInfo));

            try {
                sendEmail(email, "Email Verification OTP", "").get();
                return true;
            } catch (Exception e) {
                log.error("Failed to resend OTP to {}", email, e);
                return false;
            }
        } else {
            // User does not exist
            log.warn("User with email {} not found. Cannot resend OTP.", email);
            return false;
        }
    }

}