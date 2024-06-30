package com.studyretro.service;

import com.studyretro.repository.OtpInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpCleanupService {

    private final OtpInfoRepository otpInfoRepository;

    @Scheduled(fixedRate = 60000) // Run every 1 minute (60000 milliseconds)
    @Transactional
    public void deleteExpiredOTPs() {
        LocalDateTime now = LocalDateTime.now();
        int deletedCount = otpInfoRepository.deleteByGeneratedAtBefore(now.minusMinutes(OtpServiceImpl.OTP_EXPIRY_MINUTES));
        log.info("Deleted {} expired OTPs", deletedCount);
    }
}