package com.it.exalt.belair.domain.stock;

import java.util.Optional;

/**
 * Driven port defining stock management operations.
 * <p>
 * Implementations in the Infrastructure module handle the actual stock tracking,
 * whether in-memory or backed by a persistent store.
 * </p>
 */
public interface StockPort {

    /**
     * Retrieves the current stock level for a given article.
     *
     * @param articleId the article identifier
     * @return an {@link Optional} containing the stock record, or empty if no stock entry exists
     */
    Optional<Stock> findByArticleId(String articleId);

    /**
     * Deducts the specified quantity from the stock of a given article.
     *
     * @param articleId the article identifier
     * @param quantite  the quantity to deduct; must be positive
     */
    void deductStock(String articleId, int quantite);
}
