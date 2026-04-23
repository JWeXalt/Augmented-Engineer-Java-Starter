package com.it.exalt.belair.domain.notification;

import java.time.Instant;

/**
 * Driven port exposing a method to count alcoholic drinks consumed by a festival goer
 * in a given time window.
 */
public interface DrinkConsumptionPort {

    /**
     * Count the number of alcoholic drinks consumed by the given festivalier between
     * {@code from} (inclusive) and {@code to} (exclusive).
     */
    int countAlcoholicDrinks(String festivalierId, Instant from, Instant to);
}
