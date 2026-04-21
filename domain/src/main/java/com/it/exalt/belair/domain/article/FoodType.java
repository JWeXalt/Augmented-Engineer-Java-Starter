package com.it.exalt.belair.domain.article;

public enum FoodType {

    SNACK(1),
    MEAL(3);

    private final int tokenCost;

    FoodType(int tokenCost) {
        this.tokenCost = tokenCost;
    }

    public int getTokenCost() {
        return tokenCost;
    }
}
