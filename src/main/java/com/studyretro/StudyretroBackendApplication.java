package com.studyretro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class StudyretroBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyretroBackendApplication.class, args);
    }

}
