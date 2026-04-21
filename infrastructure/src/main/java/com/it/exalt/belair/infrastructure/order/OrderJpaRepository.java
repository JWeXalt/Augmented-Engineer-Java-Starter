package com.it.exalt.belair.infrastructure.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, String> {

    List<OrderEntity> findByFestivalierIdAndStatut(String festivalierId, String statut);
}
