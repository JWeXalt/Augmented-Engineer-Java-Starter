package com.it.exalt.belair.domain.article;

/**
 * Enum representing the type of food item and its associated token cost.
 * <p>
 * Token costs are business rules defined in FEATURES.md:
 * <ul>
 *   <li>{@link #SNACK} — costs 1 food token</li>
 *   <li>{@link #MEAL}  — costs 3 food tokens</li>
 * </ul>
 * </p>
 */
public enum FoodType {

    /** A light snack item costing 1 food token. */
    SNACK(1),
    /** A full meal costing 3 food tokens. */
    MEAL(3);

    private final int tokenCost;

    FoodType(int tokenCost) {
        this.tokenCost = tokenCost;
    }

    /**
     * Returns the number of food tokens required to order one item of this type.
     *
     * @return the token cost per item
     */
    public int getTokenCost() {
        return tokenCost;
    }
}
