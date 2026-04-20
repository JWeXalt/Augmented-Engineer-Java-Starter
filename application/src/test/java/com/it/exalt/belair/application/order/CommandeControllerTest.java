package com.it.exalt.belair.application.order;

import com.it.exalt.belair.domain.order.OrderStatut;
import com.it.exalt.belair.domain.order.PlaceOrderCommand;
import com.it.exalt.belair.domain.order.PlaceOrderResult;
import com.it.exalt.belair.domain.order.PlaceOrderUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandeControllerTest {

    @Mock
    private PlaceOrderUseCase placeOrderUseCase;

    @InjectMocks
    private CommandeController controller;

    @Test
    void givenAuthenticatedFestivalierAndValidArticles_whenPlaceOrder_thenReturns201WithCommandeIdAndEnAttenteStatus() {
        // Given
        PlaceOrderRequest request = new PlaceOrderRequest(
                "festivalier-42",
                List.of(new PlaceOrderRequest.ArticleItem("mojito", 2))
        );
        when(placeOrderUseCase.execute(any(PlaceOrderCommand.class)))
                .thenReturn(new PlaceOrderResult("commande-123", OrderStatut.EN_ATTENTE));

        // When
        ResponseEntity<PlaceOrderResponse> response = controller.placerCommande(request);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        PlaceOrderResponse body = response.getBody();
        assertNotNull(body);
        assertNotNull(body.commandeId());
        assertFalse(body.commandeId().isBlank());
        assertEquals(OrderStatut.EN_ATTENTE, body.statut());
    }

    @Test
    void givenNoAuthenticatedFestivalier_whenPlaceOrder_thenReturns401() {
        // Given - null festivalierId signals no authenticated festivalier
        PlaceOrderRequest request = new PlaceOrderRequest(null, List.of());

        // When
        ResponseEntity<PlaceOrderResponse> response = controller.placerCommande(request);

        // Then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void givenAuthenticatedFestivalierWithNoArticles_whenPlaceOrder_thenReturns400() {
        // Given
        PlaceOrderRequest request = new PlaceOrderRequest(
                "festivalier-42",
                List.of()
        );

        // When
        ResponseEntity<PlaceOrderResponse> response = controller.placerCommande(request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
