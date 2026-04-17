package com.belair.buvette.domain.order;

public class OrderResult {
    private final String orderId;
    private final OrderStatus status;

    public OrderResult(String orderId, OrderStatus status) {
        this.orderId = orderId;
        this.status = status;
    }

    public String orderId() { return orderId; }
    public OrderStatus status() { return status; }
}
