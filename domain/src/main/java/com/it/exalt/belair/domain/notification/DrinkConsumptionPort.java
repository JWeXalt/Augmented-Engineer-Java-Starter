package com.it.exalt.belair.domain.notification;

import java.time.Instant;

/**
 * Driven port exposing drink consumption related queries.
 */
public interface DrinkConsumptionPort {
    /**
     * Count alcoholic drinks consumed by the given festivalier between from (inclusive) and to (exclusive).
     */
    int countAlcoholicDrinks(String festivalierId, Instant fromInclusive, Instant toExclusive);
}
