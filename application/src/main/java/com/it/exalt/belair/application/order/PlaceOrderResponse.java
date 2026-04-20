package com.it.exalt.belair.application.order;

import com.it.exalt.belair.domain.order.OrderStatut;

public record PlaceOrderResponse(
        String commandeId,
        OrderStatut statut
) {}
