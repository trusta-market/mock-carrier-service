package com.trustamarket.mockcarrierservice.carrier.application.service;

import com.trustamarket.mockcarrierservice.carrier.application.port.in.HandleDeliveryRequestedUseCase;
import com.trustamarket.mockcarrierservice.carrier.application.port.out.CarrierEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarrierService implements HandleDeliveryRequestedUseCase {

    private final CarrierEventPublisher carrierEventPublisher;
    private final ConcurrentLinkedQueue<UUID> pendingDeliveries = new ConcurrentLinkedQueue<>();

    @Override
    public void handle(UUID deliveryId) {
        pendingDeliveries.add(deliveryId);
        log.info("배송 요청 접수 → 대기열 추가: deliveryId={}, 대기 건수={}", deliveryId, pendingDeliveries.size());
    }

    @Scheduled(fixedDelay = 30_000)
    public void processPendingDeliveries() {
        log.info("[배치] 스케줄러 실행 — 대기 건수: {}", pendingDeliveries.size());

        if (pendingDeliveries.isEmpty()) {
            log.info("[배치] 처리할 배송 없음, 스킵");
            return;
        }

        List<UUID> batch = new ArrayList<>();
        UUID deliveryId;
        while ((deliveryId = pendingDeliveries.poll()) != null) {
            batch.add(deliveryId);
        }

        log.info("[배치] 처리 시작: {}건 — {}", batch.size(), batch);
        for (UUID id : batch) {
            try {
                carrierEventPublisher.publishCarrierCompleted(id);
                log.info("[배치] carrier.completed 발행 완료: deliveryId={}", id);
            } catch (Exception e) {
                log.error("[배치] 발행 실패, 대기열 재등록: deliveryId={}", id, e);
                pendingDeliveries.add(id);
            }
        }
        log.info("[배치] 처리 완료: {}건", batch.size());
    }
}
