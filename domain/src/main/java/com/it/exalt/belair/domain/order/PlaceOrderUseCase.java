package com.it.exalt.belair.domain.order;

/**
 * Primary port (use case) for placing a food order.
 * <p>
 * Implementations must validate the festival goer's identity, verify stock availability,
 * check that the festivalier has sufficient tokens, then persist the order.
 * </p>
 */
public interface PlaceOrderUseCase {

    /**
     * Executes the place-order use case.
     *
     * @param command the validated command carrying the festival goer ID and the list of articles
     * @return the result containing the new order ID and its initial status
     * @throws IllegalArgumentException if the festivalier or any article is not found
     * @throws com.it.exalt.belair.domain.order.OutOfStockException if any requested article has insufficient stock
     * @throws com.it.exalt.belair.domain.order.InsufficientTokensException if the festival goer lacks the required tokens
     */
    PlaceOrderResult execute(PlaceOrderCommand command);
}
