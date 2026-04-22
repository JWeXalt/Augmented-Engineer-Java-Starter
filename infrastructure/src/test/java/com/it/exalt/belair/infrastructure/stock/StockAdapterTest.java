package com.it.exalt.belair.infrastructure.stock;

import com.it.exalt.belair.domain.stock.Stock;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Import({StockRepositoryAdapter.class, StockEntityMapper.class})
class StockAdapterTest {

    @Autowired
    private StockRepositoryAdapter adapter;

    @Test
    void givenStockSavedForArticle_whenFindByArticleId_thenReturnsStockWithCorrectQuantity() {
        // Given
        Stock stock = new Stock("cola-33cl", 50);
        adapter.save(stock);

        // When
        Optional<Stock> found = adapter.findByArticleId("cola-33cl");

        // Then
        assertTrue(found.isPresent());
        assertEquals("cola-33cl", found.get().articleId());
        assertEquals(50, found.get().quantite());
    }

    @Test
    void givenNoStockEntryForArticle_whenFindByArticleId_thenReturnsEmpty() {
        // Given — no stock saved

        // When
        Optional<Stock> found = adapter.findByArticleId("unknown-article");

        // Then
        assertFalse(found.isPresent());
    }

    @Test
    void givenStockOf10_whenDeductStock5_thenRemainingQuantityIs5() {
        // Given
        Stock stock = new Stock("mojito", 10);
        adapter.save(stock);

        // When
        adapter.deductStock("mojito", 5);

        // Then
        Optional<Stock> found = adapter.findByArticleId("mojito");
        assertTrue(found.isPresent());
        assertEquals(5, found.get().quantite());
    }

    @Test
    void givenStockOf3_whenDeductingAllStock_thenQuantityIsZero() {
        // Given
        Stock stock = new Stock("eau-plate", 3);
        adapter.save(stock);

        // When
        adapter.deductStock("eau-plate", 3);

        // Then
        Optional<Stock> found = adapter.findByArticleId("eau-plate");
        assertTrue(found.isPresent());
        assertEquals(0, found.get().quantite());
    }
}
