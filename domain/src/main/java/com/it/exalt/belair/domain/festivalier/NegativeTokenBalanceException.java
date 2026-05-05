package com.it.exalt.belair.domain.festivalier;

/**
 * Thrown when an operation would result in a negative token balance.
 * <p>
 * This domain exception enforces the invariant that a festival goer
 * cannot have a negative balance of food or drink tokens.
 * </p>
 */
public class NegativeTokenBalanceException extends RuntimeException {

    public NegativeTokenBalanceException(String message) {
        super(message);
    }
}
