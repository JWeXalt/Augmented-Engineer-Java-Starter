package com.it.exalt.belair.domain.festivalier;

/**
 * Represents a festival goer attending the Bel'Air festival.
 * <p>
 * A festival goer holds a balance of food tokens used to purchase snacks and meals.
 * Token balances must never go negative.
 * </p>
 *
 * <p><strong>Note:</strong> Drink token balance is not yet implemented; see FEATURES.md.</p>
 */
public class Festivalier {

    private final String id;
    private int foodTokenBalance;

    /**
     * Creates a festival goer with the given identifier and initial food token balance.
     *
     * @param id               unique identifier
     * @param foodTokenBalance starting food token balance; must be non-negative
     */
    public Festivalier(String id, int foodTokenBalance) {
        this.id = id;
        this.foodTokenBalance = foodTokenBalance;
    }

    /** @return the unique identifier of this festival goer */
    public String getId() {
        return id;
    }

    /** @return the current food token balance */
    public int getFoodTokenBalance() {
        return foodTokenBalance;
    }

    /**
     * Adjusts the food token balance by the given amount.
     * <p>
     * Pass a positive value to deduct tokens (for an order), or a negative value
     * to credit tokens back (for a cancellation refund).
     * </p>
     *
     * @param amount the number of tokens to deduct; use a negative value for refunds
     */
    public void deductFoodTokens(int amount) {
        this.foodTokenBalance -= amount;
    }
}
