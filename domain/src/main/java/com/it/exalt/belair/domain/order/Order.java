package com.it.exalt.belair.domain.order;

import java.util.List;

/**
 * Aggregate root representing a food/drink order placed by a festival goer.
 * <p>
 * An order is immutable once created. State transitions (e.g. cancellation, acknowledgement)
 * produce a new {@code Order} instance with the updated status.
 * </p>
 */
public class Order {

    private final String id;
    private final String festivalierId;
    private final OrderStatut statut;
    private final List<OrderLine> lignes;

    /**
     * Creates a fully specified order.
     *
     * @param id            unique identifier for the order
     * @param festivalierId identifier of the festival goer who placed the order
     * @param statut        current lifecycle status of the order
     * @param lignes        ordered items; the list is defensively copied
     */
    public Order(String id, String festivalierId, OrderStatut statut, List<OrderLine> lignes) {
        this.id = id;
        this.festivalierId = festivalierId;
        this.statut = statut;
        this.lignes = List.copyOf(lignes);
    }

    /**
     * Convenience constructor for creating a bare order with no lines and no festivalier link.
     *
     * @param id     unique identifier for the order
     * @param statut current lifecycle status of the order
     */
    public Order(String id, OrderStatut statut) {
        this(id, null, statut, List.of());
    }

    /** @return the unique identifier of this order */
    public String getId() {
        return id;
    }

    /** @return the identifier of the festival goer who placed this order, or {@code null} if not set */
    public String getFestivalierId() {
        return festivalierId;
    }

    /** @return the current lifecycle status of this order */
    public OrderStatut getStatut() {
        return statut;
    }

    /** @return an unmodifiable list of ordered items */
    public List<OrderLine> getLignes() {
        return lignes;
    }
}
