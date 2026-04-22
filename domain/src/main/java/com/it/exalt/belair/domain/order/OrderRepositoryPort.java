package com.it.exalt.belair.domain.order;

import java.util.List;
import java.util.Optional;

/**
 * Driven port defining the persistence contract for {@link Order} aggregate roots.
 * <p>
 * Implementations live in the Infrastructure module and must not leak JPA entities
 * or any persistence technology details into the Domain.
 * </p>
 */
public interface OrderRepositoryPort {

    /**
     * Persists a new or updated order.
     *
     * @param order the order to save
     * @return the saved order (may include generated values set by the store)
     */
    Order save(Order order);

    /**
     * Retrieves an order by its unique identifier.
     *
     * @param id the order ID
     * @return an {@link Optional} containing the order, or empty if not found
     */
    Optional<Order> findById(String id);

    /**
     * Retrieves all orders for a given festival goer filtered by status.
     *
     * @param festivalierId the festival goer's identifier
     * @param statut        the order status to filter by
     * @return a list of matching orders; never {@code null}
     */
    List<Order> findByFestivalierIdAndStatut(String festivalierId, OrderStatut statut);
}
