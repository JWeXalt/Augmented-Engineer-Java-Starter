package com.belair.buvette.domain.order;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PlaceOrderUseCaseTest {

    @Test
    void shouldCreatePendingOrderAndReturnOrderIdForAvailableItem() {
        PlaceOrderUseCase useCase = new PlaceOrderUseCase();

        PlaceOrderCommand command = new PlaceOrderCommand(
            "festivalier-123",
            "Mojito",
            1,
            true
        );

        OrderResult result = useCase.handle(command);

        assertNotNull(result.orderId());
        assertEquals(OrderStatus.EN_ATTENTE, result.status());
    }
}
