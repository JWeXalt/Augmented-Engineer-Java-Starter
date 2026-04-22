package com.it.exalt.belair.domain.festivalier;

import java.util.Optional;

/**
 * Driven port defining the persistence contract for {@link Festivalier} entities.
 * <p>
 * Implementations live in the Infrastructure module and must not expose JPA
 * or any persistence technology details to the Domain.
 * </p>
 */
public interface FestivalierPort {

    /**
     * Retrieves a festival goer by their unique identifier.
     *
     * @param id the festival goer's identifier
     * @return an {@link Optional} containing the festival goer, or empty if not found
     */
    Optional<Festivalier> findById(String id);

    /**
     * Persists a new or updated festival goer.
     *
     * @param festivalier the festival goer to save
     */
    void save(Festivalier festivalier);

    /**
     * Checks whether a festival goer with the given identifier exists.
     *
     * @param id the festival goer's identifier
     * @return {@code true} if the festival goer exists, {@code false} otherwise
     */
    boolean existsById(String id);
}
