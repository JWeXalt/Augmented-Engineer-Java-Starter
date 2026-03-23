package com.belair.buvette.domain.order;

/**
 * Represents the result of placing an order.
 * Contains the generated order ID and the initial order status.
 *
 * @param orderId the unique identifier for the placed order
 * @param status the current status of the order
 */
public record OrderResult(String orderId, OrderStatus status) {
}
