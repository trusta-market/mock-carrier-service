package com.trustamarket.mockcarrierservice;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class MockCarrierServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockCarrierServiceApplication.class, args);
    }

    @PostConstruct
    public void init() {
        log.info("[MockCarrier] 서비스 기동 완료 — 30초 배치 스케줄러 활성");
    }
}
