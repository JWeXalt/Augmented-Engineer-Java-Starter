package com.it.exalt.belair.domain.order;

import com.it.exalt.belair.domain.article.Article;
import com.it.exalt.belair.domain.article.ArticleCatalogPort;
import com.it.exalt.belair.domain.article.FoodType;
import com.it.exalt.belair.domain.festivalier.Festivalier;
import com.it.exalt.belair.domain.festivalier.FestivalierPort;
import com.it.exalt.belair.domain.stock.Stock;
import com.it.exalt.belair.domain.stock.StockPort;

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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaceOrderUseCaseTest {

    @Mock
    private FestivalierPort festivalierPort;

    @Mock
    private ArticleCatalogPort articleCatalogPort;

    @Mock
    private StockPort stockPort;

    @Mock
    private OrderRepositoryPort orderPort;

    @InjectMocks
    private PlaceOrderService placeOrderUseCase;

    @Test
    void givenFestivalierWithEnoughFoodTokens_whenOrderingSnack_thenSnackCosts1TokenAndBalanceDecreases() {
        // Given
        Festivalier festivalier = new Festivalier("festivalier-42", 5);
        when(festivalierPort.findById("festivalier-42")).thenReturn(Optional.of(festivalier));
        when(articleCatalogPort.findById("snack-cola")).thenReturn(Optional.of(new Article("snack-cola", "Cola", FoodType.SNACK)));
        when(stockPort.findByArticleId("snack-cola")).thenReturn(Optional.of(new Stock("snack-cola", 100)));
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
        when(stockPort.findByArticleId("plat-pasta")).thenReturn(Optional.of(new Stock("plat-pasta", 100)));
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
        when(stockPort.findByArticleId("snack-cola")).thenReturn(Optional.of(new Stock("snack-cola", 100)));

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
        when(stockPort.findByArticleId("plat-pasta")).thenReturn(Optional.of(new Stock("plat-pasta", 100)));

        PlaceOrderCommand command = new PlaceOrderCommand(
                "festivalier-42",
                List.of(new PlaceOrderCommand.ArticleItem("plat-pasta", 1))
        );

        // When / Then
        assertThrows(InsufficientTokensException.class, () -> placeOrderUseCase.execute(command));
    }

    @Test
    void givenSufficientStock_whenOrderingArticle_thenOrderSucceedsAndStockIsDeducted() {
        // Given
        Festivalier festivalier = new Festivalier("festivalier-42", 5);
        when(festivalierPort.findById("festivalier-42")).thenReturn(Optional.of(festivalier));
        when(articleCatalogPort.findById("mojito")).thenReturn(Optional.of(new Article("mojito", "Mojito", FoodType.SNACK)));
        when(stockPort.findByArticleId("mojito")).thenReturn(Optional.of(new Stock("mojito", 10)));
        when(orderPort.save(any())).thenAnswer(inv -> inv.getArgument(0));

        PlaceOrderCommand command = new PlaceOrderCommand(
                "festivalier-42",
                List.of(new PlaceOrderCommand.ArticleItem("mojito", 2))
        );

        // When
        PlaceOrderResult result = placeOrderUseCase.execute(command);

        // Then
        assertNotNull(result);
        assertEquals(OrderStatut.EN_ATTENTE, result.statut());
        verify(stockPort).deductStock("mojito", 2);
    }

    @Test
    void givenInsufficientStock_whenRequestedQuantityExceedsAvailableStock_thenOrderIsRejected() {
        // Given
        Festivalier festivalier = new Festivalier("festivalier-42", 5);
        when(festivalierPort.findById("festivalier-42")).thenReturn(Optional.of(festivalier));
        when(articleCatalogPort.findById("mojito")).thenReturn(Optional.of(new Article("mojito", "Mojito", FoodType.SNACK)));
        when(stockPort.findByArticleId("mojito")).thenReturn(Optional.of(new Stock("mojito", 1)));

        PlaceOrderCommand command = new PlaceOrderCommand(
                "festivalier-42",
                List.of(new PlaceOrderCommand.ArticleItem("mojito", 2))
        );

        // When / Then
        assertThrows(OutOfStockException.class, () -> placeOrderUseCase.execute(command));
    }
}
