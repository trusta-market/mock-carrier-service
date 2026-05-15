package com.trustamarket.mockcarrierservice.carrier.application.port.out;

import java.util.UUID;

public interface CarrierEventPublisher {
    void publishCarrierCompleted(UUID deliveryId);
}
