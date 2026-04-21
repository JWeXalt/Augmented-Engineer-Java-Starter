package com.it.exalt.belair.domain.order;

import com.it.exalt.belair.domain.article.Article;
import com.it.exalt.belair.domain.article.ArticleCatalogPort;
import com.it.exalt.belair.domain.article.FoodType;
import com.it.exalt.belair.domain.festivalier.Festivalier;
import com.it.exalt.belair.domain.festivalier.FestivalierPort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaceOrderUseCaseTest {

    @Mock
    private FestivalierPort festivalierPort;

    @Mock
    private ArticleCatalogPort articleCatalogPort;

    @Mock
    private OrderPort orderPort;

    @InjectMocks
    private PlaceOrderService placeOrderUseCase;

    @Test
    void givenFestivalierWithEnoughFoodTokens_whenOrderingSnack_thenSnackCosts1TokenAndBalanceDecreases() {
        // Given
        Festivalier festivalier = new Festivalier("festivalier-42", 5);
        when(festivalierPort.findById("festivalier-42")).thenReturn(Optional.of(festivalier));
        when(articleCatalogPort.findById("snack-cola")).thenReturn(Optional.of(new Article("snack-cola", "Cola", FoodType.SNACK)));
        when(orderPort.save(any())).thenAnswer(inv -> inv.getArgument(0));

        PlaceOrderCommand command = new PlaceOrderCommand(
                "festivalier-42",
                List.of(new PlaceOrderCommand.ArticleItem("snack-cola", 1))
        );

        // When
        PlaceOrderResult result = placeOrderUseCase.execute(command);

        // Then
        assertNotNull(result);
        assertNotNull(result.commandeId());
        assertFalse(result.commandeId().isBlank());
        assertEquals(OrderStatut.EN_ATTENTE, result.statut());
        assertEquals(4, festivalier.getFoodTokenBalance());
    }

    @Test
    void givenFestivalierWithEnoughFoodTokens_whenOrderingMeal_thenMealCosts3TokensAndBalanceDecreases() {
        // Given
        Festivalier festivalier = new Festivalier("festivalier-42", 5);
        when(festivalierPort.findById("festivalier-42")).thenReturn(Optional.of(festivalier));
        when(articleCatalogPort.findById("plat-pasta")).thenReturn(Optional.of(new Article("plat-pasta", "Pasta", FoodType.MEAL)));
        when(orderPort.save(any())).thenAnswer(inv -> inv.getArgument(0));

        PlaceOrderCommand command = new PlaceOrderCommand(
                "festivalier-42",
                List.of(new PlaceOrderCommand.ArticleItem("plat-pasta", 1))
        );

        // When
        PlaceOrderResult result = placeOrderUseCase.execute(command);

        // Then
        assertNotNull(result);
        assertNotNull(result.commandeId());
        assertFalse(result.commandeId().isBlank());
        assertEquals(OrderStatut.EN_ATTENTE, result.statut());
        assertEquals(2, festivalier.getFoodTokenBalance());
    }

    @Test
    void givenFestivalierWithZeroFoodTokens_whenOrderingSnack_thenOrderIsRejected() {
        // Given
        Festivalier festivalier = new Festivalier("festivalier-42", 0);
        when(festivalierPort.findById("festivalier-42")).thenReturn(Optional.of(festivalier));
        when(articleCatalogPort.findById("snack-cola")).thenReturn(Optional.of(new Article("snack-cola", "Cola", FoodType.SNACK)));

        PlaceOrderCommand command = new PlaceOrderCommand(
                "festivalier-42",
                List.of(new PlaceOrderCommand.ArticleItem("snack-cola", 1))
        );

        // When / Then
        assertThrows(InsufficientTokensException.class, () -> placeOrderUseCase.execute(command));
    }

    @Test
    void givenFestivalierWithInsufficientTokens_whenOrderWouldResultInNegativeBalance_thenOrderIsRejected() {
        // Given
        Festivalier festivalier = new Festivalier("festivalier-42", 2);
        when(festivalierPort.findById("festivalier-42")).thenReturn(Optional.of(festivalier));
        when(articleCatalogPort.findById("plat-pasta")).thenReturn(Optional.of(new Article("plat-pasta", "Pasta", FoodType.MEAL)));

        PlaceOrderCommand command = new PlaceOrderCommand(
                "festivalier-42",
                List.of(new PlaceOrderCommand.ArticleItem("plat-pasta", 1))
        );

        // When / Then
        assertThrows(InsufficientTokensException.class, () -> placeOrderUseCase.execute(command));
    }
}
