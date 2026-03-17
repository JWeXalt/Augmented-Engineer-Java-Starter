package com.it.exalt.belair.domain.festivalgoer;

import java.util.Objects;

/**
 * Value Object representing the token balances for a festival goer.
 */
public final class Tokens {
    private final int drinkTokens;
    private final int snackTokens;

    public Tokens(int drinkTokens, int snackTokens) {
        if (drinkTokens < 0 || snackTokens < 0) {
            throw new IllegalArgumentException("Token balances cannot be negative");
        }
        this.drinkTokens = drinkTokens;
        this.snackTokens = snackTokens;
    }

    public int getDrinkTokens() {
        return drinkTokens;
    }

    public int getSnackTokens() {
        return snackTokens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tokens tokens = (Tokens) o;
        return drinkTokens == tokens.drinkTokens && snackTokens == tokens.snackTokens;
    }

    @Override
    public int hashCode() {
        return Objects.hash(drinkTokens, snackTokens);
    }

    @Override
    public String toString() {
        return "Tokens{" +
                "drinkTokens=" + drinkTokens +
                ", snackTokens=" + snackTokens +
                '}';
    }
}
