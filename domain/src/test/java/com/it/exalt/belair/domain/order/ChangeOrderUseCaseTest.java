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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChangeOrderUseCaseTest {

    @Mock
    private OrderRepositoryPort orderRepositoryPort;

    @Mock
    private FestivalierPort festivalierPort;

    @Mock
    private ArticleCatalogPort articleCatalogPort;

    @Mock
    private StockPort stockPort;

    @InjectMocks
    private ChangeOrderService changeOrderService;

    @Test
    void givenUnacknowledgedOrder_whenChangingOrder_thenOrderIsUpdatedAndTokensAdjusted() {
        // Given
        Festivalier festivalier = new Festivalier("festivalier-42", 5);
        Order order = new Order("order-1", "festivalier-42", OrderStatut.EN_ATTENTE,
                List.of(new OrderLine("snack-cola", 1)));

        when(festivalierPort.findById("festivalier-42")).thenReturn(Optional.of(festivalier));
        when(orderRepositoryPort.findById("order-1")).thenReturn(Optional.of(order));
        when(articleCatalogPort.findById("snack-cola")).thenReturn(Optional.of(new Article("snack-cola", "Cola", FoodType.SNACK)));
        when(articleCatalogPort.findById("plat-pasta")).thenReturn(Optional.of(new Article("plat-pasta", "Pasta", FoodType.MEAL)));
        when(stockPort.findByArticleId("plat-pasta")).thenReturn(Optional.of(new Stock("plat-pasta", 100)));
        when(orderRepositoryPort.save(any())).thenAnswer(inv -> inv.getArgument(0));

        ChangeOrderCommand command = new ChangeOrderCommand(
                "festivalier-42",
                "order-1",
                List.of(new ChangeOrderCommand.ArticleItem("plat-pasta", 1))
        );

        // When
        changeOrderService.execute(command);

        // Then
        // Meal costs 3 tokens, old snack cost 1 => delta 2, balance 5 -> 3
        assertEquals(3, festivalier.getFoodTokenBalance());
        verify(orderRepositoryPort).save(argThat(o -> o.getLignes().stream().anyMatch(l -> l.articleId().equals("plat-pasta"))));
        verify(stockPort).deductStock("plat-pasta", 1);
    }

    @Test
    void givenAcknowledgedOrder_whenRequestingChange_thenChangeIsRejected() {
        // Given
        Order order = new Order("order-2", "festivalier-42", OrderStatut.PRETE, List.of(new OrderLine("snack-cola", 1)));
        when(orderRepositoryPort.findById("order-2")).thenReturn(Optional.of(order));

        ChangeOrderCommand command = new ChangeOrderCommand(
                "festivalier-42",
                "order-2",
                List.of(new ChangeOrderCommand.ArticleItem("plat-pasta", 1))
        );

        // When / Then
        assertThrows(OrderAlreadyAcknowledgedException.class, () -> changeOrderService.execute(command));
    }

    @Test
    void givenChangeWouldResultInNegativeBalance_whenChangingOrder_thenChangeIsRejected() {
        // Given: festivalier has 0 tokens but change requires 3
        Festivalier festivalier = new Festivalier("festivalier-42", 0);
        Order order = new Order("order-3", "festivalier-42", OrderStatut.EN_ATTENTE, List.of());

        when(festivalierPort.findById("festivalier-42")).thenReturn(Optional.of(festivalier));
        when(orderRepositoryPort.findById("order-3")).thenReturn(Optional.of(order));
        when(articleCatalogPort.findById("plat-pasta")).thenReturn(Optional.of(new Article("plat-pasta", "Pasta", FoodType.MEAL)));

        ChangeOrderCommand command = new ChangeOrderCommand(
                "festivalier-42",
                "order-3",
                List.of(new ChangeOrderCommand.ArticleItem("plat-pasta", 1))
        );

        // When / Then
        assertThrows(InsufficientTokensException.class, () -> changeOrderService.execute(command));
    }
}
