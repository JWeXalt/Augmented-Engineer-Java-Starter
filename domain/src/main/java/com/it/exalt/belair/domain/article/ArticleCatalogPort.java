package com.it.exalt.belair.domain.article;

import java.util.Optional;

/**
 * Driven port providing access to the article catalog.
 * <p>
 * Implementations in the Infrastructure module may back this port with an in-memory
 * catalog, a database, or a remote catalog service.
 * </p>
 */
public interface ArticleCatalogPort {

    /**
     * Looks up an article by its unique identifier.
     *
     * @param id the article identifier
     * @return an {@link Optional} containing the article, or empty if not found in the catalog
     */
    Optional<Article> findById(String id);
}
