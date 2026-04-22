package com.it.exalt.belair.domain.order;

public class OrderAlreadyAcknowledgedException extends RuntimeException {

    public OrderAlreadyAcknowledgedException(String orderId) {
        super("Order already acknowledged and cannot be cancelled: " + orderId);
    }
}
