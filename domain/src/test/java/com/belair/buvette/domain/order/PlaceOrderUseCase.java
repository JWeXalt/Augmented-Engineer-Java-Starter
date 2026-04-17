package com.belair.buvette.domain.order;

import java.util.UUID;

public class PlaceOrderUseCase {

    public OrderResult handle(PlaceOrderCommand command) {
        String id = UUID.randomUUID().toString();
        return new OrderResult(id, OrderStatus.EN_ATTENTE);
    }
}
