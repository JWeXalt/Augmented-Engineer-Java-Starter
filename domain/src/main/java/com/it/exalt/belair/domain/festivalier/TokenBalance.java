package com.it.exalt.belair.domain.festivalier;

/**
 * Immutable value object representing the token balance of a festival goer for a single festival day.
 * <p>
 * A balance holds two distinct token types:
 * <ul>
 *   <li><strong>foodTokens</strong> – used to order snacks and meals</li>
 *   <li><strong>drinkTokens</strong> – used to order alcoholic drinks</li>
 * </ul>
 * Both quantities must be non-negative. Attempting to create a {@code TokenBalance} with a
 * negative value throws a {@link NegativeTokenBalanceException}.
 * </p>
 *
 * <p>Unspent tokens are <em>not</em> carried over to the next day; a fresh daily allocation
 * should be obtained via {@link #dailyAllocation()}.</p>
 */
public final class TokenBalance {

    /** Number of food tokens allocated to a festival goer per day. */
    public static final int DAILY_FOOD_TOKENS = 9;

    /** Number of drink tokens allocated to a festival goer per day. */
    public static final int DAILY_DRINK_TOKENS = 6;

    private final int foodTokens;
    private final int drinkTokens;

    /**
     * Creates a {@code TokenBalance} with the given food and drink token quantities.
     *
     * @param foodTokens  number of food tokens; must be ≥ 0
     * @param drinkTokens number of drink tokens; must be ≥ 0
     * @throws NegativeTokenBalanceException if either value is negative
     */
    public TokenBalance(int foodTokens, int drinkTokens) {
        if (foodTokens < 0 || drinkTokens < 0) {
            throw new NegativeTokenBalanceException(
                    "Token balance cannot be negative (foodTokens=" + foodTokens + ", drinkTokens=" + drinkTokens + ")"
            );
        }
        this.foodTokens = foodTokens;
        this.drinkTokens = drinkTokens;
    }

    /**
     * Returns the standard daily token allocation: {@value #DAILY_FOOD_TOKENS} food tokens
     * and {@value #DAILY_DRINK_TOKENS} drink tokens.
     *
     * @return a new {@code TokenBalance} representing one day's allocation
     */
    public static TokenBalance dailyAllocation() {
        return new TokenBalance(DAILY_FOOD_TOKENS, DAILY_DRINK_TOKENS);
    }

    /**
     * Returns a new {@code TokenBalance} with the given number of food tokens deducted.
     *
     * @param amount the number of food tokens to deduct; must not make the balance negative
     * @return a new {@code TokenBalance} with the updated food token count
     * @throws NegativeTokenBalanceException if the resulting food token balance would be negative
     */
    public TokenBalance deductFood(int amount) {
        return new TokenBalance(this.foodTokens - amount, this.drinkTokens);
    }

    /**
     * Returns a new {@code TokenBalance} with the given number of drink tokens deducted.
     *
     * @param amount the number of drink tokens to deduct; must not make the balance negative
     * @return a new {@code TokenBalance} with the updated drink token count
     * @throws NegativeTokenBalanceException if the resulting drink token balance would be negative
     */
    public TokenBalance deductDrink(int amount) {
        return new TokenBalance(this.foodTokens, this.drinkTokens - amount);
    }

    /** @return the current food token balance */
    public int foodTokens() {
        return foodTokens;
    }

    /** @return the current drink token balance */
    public int drinkTokens() {
        return drinkTokens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TokenBalance other)) return false;
        return foodTokens == other.foodTokens && drinkTokens == other.drinkTokens;
    }

    @Override
    public int hashCode() {
        return 31 * foodTokens + drinkTokens;
    }

    @Override
    public String toString() {
        return "TokenBalance{foodTokens=" + foodTokens + ", drinkTokens=" + drinkTokens + "}";
    }
}
