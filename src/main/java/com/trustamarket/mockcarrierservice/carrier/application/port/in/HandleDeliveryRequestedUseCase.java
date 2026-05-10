package com.trustamarket.mockcarrierservice.carrier.application.port.in;

import java.util.UUID;

public interface HandleDeliveryRequestedUseCase {

    void handle(UUID deliveryId);
}
