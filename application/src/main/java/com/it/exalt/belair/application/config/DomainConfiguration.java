package com.it.exalt.belair.application.config;

import com.it.exalt.belair.domain.article.ArticleCatalogPort;
import com.it.exalt.belair.domain.festivalier.FestivalierPort;
import com.it.exalt.belair.domain.order.OrderRepositoryPort;
import com.it.exalt.belair.domain.order.PlaceOrderService;
import com.it.exalt.belair.domain.order.PlaceOrderUseCase;
import com.it.exalt.belair.domain.stock.StockPort;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration Spring pour les beans du domaine.
 * Assure le câblage des Use Cases avec leurs ports (adapters).
 */
@Configuration
public class DomainConfiguration {

    @Bean
    public PlaceOrderUseCase placeOrderUseCase(
            FestivalierPort festivalierPort,
            ArticleCatalogPort articleCatalogPort,
            StockPort stockPort,
            OrderRepositoryPort orderRepositoryPort) {
        return new PlaceOrderService(festivalierPort, articleCatalogPort, stockPort, orderRepositoryPort);
    }
}
