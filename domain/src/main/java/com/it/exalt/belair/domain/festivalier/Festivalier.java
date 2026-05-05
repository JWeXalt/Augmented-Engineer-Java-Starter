package com.it.exalt.belair.domain.festivalier;

/**
 * Aggregate root representing a festival goer attending the Bel'Air festival.
 * <p>
 * A festival goer holds a {@link TokenBalance} covering both food and drink tokens.
 * Token balances must never go negative; the invariant is enforced by {@link TokenBalance}.
 * </p>
 *
 * <p>Unspent tokens are not carried over to the next festival day. A fresh daily allocation
 * should be obtained via {@link TokenBalance#dailyAllocation()}.</p>
 */
public class Festivalier {

    private final String id;
    private TokenBalance tokenBalance;

    /**
     * Creates a festival goer with the given identifier and token balance.
     *
     * @param id           unique identifier
     * @param tokenBalance initial token balance; must not contain negative values
     */
    public Festivalier(String id, TokenBalance tokenBalance) {
        this.id = id;
        this.tokenBalance = tokenBalance;
    }

    /** @return the unique identifier of this festival goer */
    public String getId() {
        return id;
    }

    /** @return the current token balance (food and drink) */
    public TokenBalance getTokenBalance() {
        return tokenBalance;
    }

    /** @return the current food token balance */
    public int getFoodTokenBalance() {
        return tokenBalance.foodTokens();
    }

    /** @return the current drink token balance */
    public int getDrinkTokenBalance() {
        return tokenBalance.drinkTokens();
    }

    /**
     * Adjusts the food token balance by the given amount.
     * <p>
     * Pass a positive value to deduct tokens (for an order), or a negative value
     * to credit tokens back (for a cancellation refund).
     * </p>
     *
     * @param amount the number of food tokens to deduct; use a negative value for refunds
     * @throws NegativeTokenBalanceException if the resulting balance would be negative
     */
    public void deductFoodTokens(int amount) {
        this.tokenBalance = tokenBalance.deductFood(amount);
    }

    /**
     * Adjusts the drink token balance by the given amount.
     * <p>
     * Pass a positive value to deduct tokens (for an order), or a negative value
     * to credit tokens back (for a cancellation refund).
     * </p>
     *
     * @param amount the number of drink tokens to deduct; use a negative value for refunds
     * @throws NegativeTokenBalanceException if the resulting balance would be negative
     */
    public void deductDrinkTokens(int amount) {
        this.tokenBalance = tokenBalance.deductDrink(amount);
    }
}
