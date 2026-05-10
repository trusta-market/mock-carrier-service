package com.trustamarket.mockcarrierservice.carrier.adapter.out.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trustamarket.mockcarrierservice.carrier.application.port.out.CarrierEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaCarrierEventPublisher implements CarrierEventPublisher {

    private static final String CARRIER_COMPLETED_TOPIC = "carrier.completed";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void publishCarrierCompleted(UUID deliveryId) {
        try {
            String payload = objectMapper.writeValueAsString(new CarrierCompletedKafkaEvent(deliveryId));
            kafkaTemplate.send(CARRIER_COMPLETED_TOPIC, deliveryId.toString(), payload);
            log.info("carrier.completed 발행: deliveryId={}", deliveryId);
        } catch (JsonProcessingException e) {
            log.error("carrier.completed 직렬화 실패: deliveryId={}", deliveryId, e);
            throw new RuntimeException(e);
        }
    }
}
