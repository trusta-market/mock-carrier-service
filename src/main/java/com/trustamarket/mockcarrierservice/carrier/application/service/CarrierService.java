package com.trustamarket.mockcarrierservice.carrier.application.service;

import com.trustamarket.mockcarrierservice.carrier.application.port.in.HandleDeliveryRequestedUseCase;
import com.trustamarket.mockcarrierservice.carrier.application.port.out.CarrierEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarrierService implements HandleDeliveryRequestedUseCase {

    private final CarrierEventPublisher carrierEventPublisher;

    @Override
    public void handle(UUID deliveryId) {
        log.info("배송 요청 수신 → 즉시 완료 처리: deliveryId={}", deliveryId);
        carrierEventPublisher.publishCarrierCompleted(deliveryId);
    }
}
