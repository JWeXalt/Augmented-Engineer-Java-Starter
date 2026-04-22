package com.it.exalt.belair.infrastructure.festivalier;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "festivaliers")
public class FestivalierEntity {

    @Id
    private String id;

    @Column(name = "food_token_balance")
    private int foodTokenBalance;

    protected FestivalierEntity() {}

    public FestivalierEntity(String id, int foodTokenBalance) {
        this.id = id;
        this.foodTokenBalance = foodTokenBalance;
    }

    public String getId() {
        return id;
    }

    public int getFoodTokenBalance() {
        return foodTokenBalance;
    }
}
