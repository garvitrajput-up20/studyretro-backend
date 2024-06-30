package com.studyretro.repository;

import com.studyretro.entity.OtpInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpInfoRepository extends JpaRepository<OtpInfo, Long> {

    OtpInfo findByEmailAndOtp(String email, String otp);
    OtpInfo deleteByEmail(String email);

}