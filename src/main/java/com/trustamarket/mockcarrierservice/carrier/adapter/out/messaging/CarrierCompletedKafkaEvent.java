package com.trustamarket.mockcarrierservice.carrier.adapter.out.messaging;

import java.util.UUID;

record CarrierCompletedKafkaEvent(
        UUID deliveryId
) {
}
