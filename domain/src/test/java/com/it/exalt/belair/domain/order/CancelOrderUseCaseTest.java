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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CancelOrderUseCaseTest {

    @Mock
    private OrderRepositoryPort orderRepositoryPort;

    @Mock
    private FestivalierPort festivalierPort;

    @Mock
    private ArticleCatalogPort articleCatalogPort;

    @InjectMocks
    private CancelOrderService cancelOrderService;

    @Test
    void givenUnacknowledgedOrder_whenCancelingOrder_thenOrderIsCancelledAndTokensAreRefunded() {
        // Given
        Festivalier festivalier = new Festivalier("festivalier-42", 4);
        Order order = new Order("order-1", "festivalier-42", OrderStatut.EN_ATTENTE, List.of(new OrderLine("snack-cola", 1)));

        when(festivalierPort.findById("festivalier-42")).thenReturn(Optional.of(festivalier));
        when(orderRepositoryPort.findById("order-1")).thenReturn(Optional.of(order));
        when(articleCatalogPort.findById("snack-cola")).thenReturn(Optional.of(new Article("snack-cola", "Cola", FoodType.SNACK)));
        when(orderRepositoryPort.save(any())).thenAnswer(inv -> inv.getArgument(0));

        CancelOrderCommand command = new CancelOrderCommand("festivalier-42", "order-1");

        // When
        cancelOrderService.execute(command);

        // Then
        assertEquals(5, festivalier.getFoodTokenBalance());
        verify(orderRepositoryPort).save(argThat(o -> o.getStatut() == OrderStatut.ANNULEE));
    }

    @Test
    void givenAcknowledgedOrder_whenRequestingCancellation_thenCancellationIsRejected() {
        // Given
        Order order = new Order("order-2", "festivalier-42", OrderStatut.PRETE, List.of(new OrderLine("snack-cola", 1)));
        when(orderRepositoryPort.findById("order-2")).thenReturn(Optional.of(order));

        CancelOrderCommand command = new CancelOrderCommand("festivalier-42", "order-2");

        // When / Then
        assertThrows(OrderAlreadyAcknowledgedException.class, () -> cancelOrderService.execute(command));
    }
}
