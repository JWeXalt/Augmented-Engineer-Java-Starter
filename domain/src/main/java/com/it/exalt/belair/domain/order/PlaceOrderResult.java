package com.it.exalt.belair.domain.order;

public record PlaceOrderResult(
        String commandeId,
        OrderStatut statut
) {}
