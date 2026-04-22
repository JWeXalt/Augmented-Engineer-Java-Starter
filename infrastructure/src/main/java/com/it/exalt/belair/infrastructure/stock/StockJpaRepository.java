package com.it.exalt.belair.infrastructure.stock;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockJpaRepository extends JpaRepository<StockEntity, String> {
}
