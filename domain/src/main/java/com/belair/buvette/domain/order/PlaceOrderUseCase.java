package com.belair.buvette.domain.order;

import java.util.UUID;

/**
 * Use case for placing a food order.
 * Handles the creation of new orders and returns the order result with generated order ID.
 * The order is initially created in EN_ATTENTE (pending) status.
 */
public class PlaceOrderUseCase {

    /**
     * Handles the place order command and creates a new order.
     *
     * @param command the place order command containing order details
     * @return the result containing the generated order ID and status
     */
    public OrderResult handle(PlaceOrderCommand command) {
        String orderId = UUID.randomUUID().toString();
        return new OrderResult(orderId, OrderStatus.EN_ATTENTE);
    }
}
