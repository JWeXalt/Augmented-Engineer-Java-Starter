package com.it.exalt.belair.domain.order;

import java.util.List;
import java.util.Optional;

public interface OrderRepositoryPort {

    Order save(Order order);

    Optional<Order> findById(String id);

    List<Order> findByFestivalierIdAndStatut(String festivalierId, OrderStatut statut);
}
