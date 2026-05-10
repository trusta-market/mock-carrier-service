package com.trustamarket.mockcarrierservice.carrier.adapter.in.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trustamarket.mockcarrierservice.carrier.application.port.in.HandleDeliveryRequestedUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CarrierDeliveryRequestedConsumer {

    private final HandleDeliveryRequestedUseCase handleDeliveryRequestedUseCase;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "carrier.delivery_requested", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String payload) {
        try {
            CarrierDeliveryRequestedEvent event = objectMapper.readValue(payload, CarrierDeliveryRequestedEvent.class);
            log.info("carrier.delivery_requested 수신: deliveryId={}, type={}", event.deliveryId(), event.deliveryType());
            handleDeliveryRequestedUseCase.handle(event.deliveryId());
        } catch (JsonProcessingException e) {
            log.error("carrier.delivery_requested 파싱 실패: payload={}", payload, e);
            throw new RuntimeException(e);
        }
    }
}
