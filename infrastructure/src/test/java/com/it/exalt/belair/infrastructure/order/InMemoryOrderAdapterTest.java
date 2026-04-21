package com.it.exalt.belair.infrastructure.order;

import com.it.exalt.belair.domain.order.OrderLine;
import com.it.exalt.belair.domain.order.Order;
import com.it.exalt.belair.domain.order.OrderStatut;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import({OrderRepositoryAdapter.class, OrderEntityMapper.class})
class InMemoryOrderAdapterTest {

    @Autowired
    private OrderRepositoryAdapter adapter;

    @Test
    void givenOrderWithTwoLines_whenSaved_thenCanBeFoundByIdWithCorrectStatusAndLines() {
        // Given
        List<OrderLine> lignes = List.of(
                new OrderLine("mojito", 2),
                new OrderLine("eau-plate", 1)
        );
        Order order = new Order("order-1", "festivalier-42", OrderStatut.EN_ATTENTE, lignes);

        // When
        adapter.save(order);

        // Then
        Optional<Order> found = adapter.findById("order-1");
        assertTrue(found.isPresent());
        assertEquals(OrderStatut.EN_ATTENTE, found.get().getStatut());
        assertEquals(2, found.get().getLignes().size());
    }

    @Test
    void givenSavedOrderWithEnAttenteStatus_whenStatusUpdatedToPrete_thenCanBeFoundByIdWithPreteStatus() {
        // Given
        Order order = new Order("order-2", "festivalier-42", OrderStatut.EN_ATTENTE, List.of());
        adapter.save(order);

        // When
        Order updatedOrder = new Order("order-2", "festivalier-42", OrderStatut.PRETE, List.of());
        adapter.save(updatedOrder);

        // Then
        Optional<Order> found = adapter.findById("order-2");
        assertTrue(found.isPresent());
        assertEquals(OrderStatut.PRETE, found.get().getStatut());
    }

    @Test
    void givenThreeOrdersForSameFestivalier_whenSearchingByStatusEnAttente_thenReturnsExactlyTwo() {
        // Given
        Order order1 = new Order("order-3", "festivalier-42", OrderStatut.EN_ATTENTE, List.of());
        Order order2 = new Order("order-4", "festivalier-42", OrderStatut.EN_ATTENTE, List.of());
        Order order3 = new Order("order-5", "festivalier-42", OrderStatut.PRETE, List.of());
        adapter.save(order1);
        adapter.save(order2);
        adapter.save(order3);

        // When
        List<Order> result = adapter.findByFestivalierIdAndStatut("festivalier-42", OrderStatut.EN_ATTENTE);

        // Then
        assertEquals(2, result.size());
    }
}

