package com.it.exalt.belair.application.festivalgoer;

/**
 * DTO for returning token balances.
 */
public class TokensBalanceDTO {
    private int drinkTokens;
    private int snackTokens;

    public TokensBalanceDTO(int drinkTokens, int snackTokens) {
        this.drinkTokens = drinkTokens;
        this.snackTokens = snackTokens;
    }

    public int getDrinkTokens() {
        return drinkTokens;
    }

    public int getSnackTokens() {
        return snackTokens;
    }
}
