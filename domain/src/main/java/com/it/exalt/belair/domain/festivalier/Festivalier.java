package com.it.exalt.belair.domain.festivalier;

public class Festivalier {

    private final String id;
    private int foodTokenBalance;

    public Festivalier(String id, int foodTokenBalance) {
        this.id = id;
        this.foodTokenBalance = foodTokenBalance;
    }

    public String getId() {
        return id;
    }

    public int getFoodTokenBalance() {
        return foodTokenBalance;
    }

    public void deductFoodTokens(int amount) {
        this.foodTokenBalance -= amount;
    }
}
