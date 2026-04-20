package com.it.exalt.belair.domain.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PlaceOrderUseCaseTest {

    private final Map<String, String> availableArticles = new HashMap<>();
    private PlaceOrderUseCase useCase;

    @BeforeEach
    void setUp() {
        ArticleRepository articleRepository = articleId -> availableArticles.containsKey(articleId);
        useCase = new PlaceOrderService(articleRepository);
    }

    @Test
    void givenAvailableItem_whenFestivalierPlacesOrder_thenOrderIsCreatedWithPendingStatusAndCommandeIdIsReturned() {
        // Given
        availableArticles.put("mojito-001", "Mojito");
        String festivalierId = "festivalier-1";
        PlaceOrderCommand command = new PlaceOrderCommand(
                festivalierId,
                List.of(new PlaceOrderCommand.ArticleItem("mojito-001", 1))
        );

        // When
        PlaceOrderResult result = useCase.execute(command);

        // Then
        assertNotNull(result.commandeId(), "The order ID must not be null");
        assertFalse(result.commandeId().isBlank(), "The order ID must not be blank");
        assertEquals(OrderStatut.EN_ATTENTE, result.statut(), "The order status must be EN_ATTENTE");
    }
}
