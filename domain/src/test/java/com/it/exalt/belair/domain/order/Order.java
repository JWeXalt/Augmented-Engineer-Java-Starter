package com.it.exalt.belair.domain.order;

public class Order {

    private final String id;
    private final OrderStatut statut;

    public Order(String id, OrderStatut statut) {
        this.id = id;
        this.statut = statut;
    }

    public String getId() {
        return id;
    }

    public OrderStatut getStatut() {
        return statut;
    }
}
