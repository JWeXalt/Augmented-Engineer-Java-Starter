package com.it.exalt.belair.domain.order;

import java.util.List;

public class Order {

    private final String id;
    private final String festivalierId;
    private final OrderStatut statut;
    private final List<OrderLine> lignes;

    public Order(String id, String festivalierId, OrderStatut statut, List<OrderLine> lignes) {
        this.id = id;
        this.festivalierId = festivalierId;
        this.statut = statut;
        this.lignes = List.copyOf(lignes);
    }

    public Order(String id, OrderStatut statut) {
        this(id, null, statut, List.of());
    }

    public String getId() {
        return id;
    }

    public String getFestivalierId() {
        return festivalierId;
    }

    public OrderStatut getStatut() {
        return statut;
    }

    public List<OrderLine> getLignes() {
        return lignes;
    }
}
