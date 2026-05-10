package com.trustamarket.mockcarrierservice.carrier.adapter.in.messaging;

import java.util.UUID;

record CarrierDeliveryRequestedEvent(
        UUID deliveryId,
        String deliveryType,
        UUID productId,
        UUID senderId,
        UUID receiverId
) {
}
