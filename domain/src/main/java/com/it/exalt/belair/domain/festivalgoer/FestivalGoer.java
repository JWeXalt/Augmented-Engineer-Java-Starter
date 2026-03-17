package com.it.exalt.belair.domain.festivalgoer;

import java.util.UUID;

/**
 * Entity representing a festival goer.
 */
public class FestivalGoer {
    private final UUID id;
    private final String name;
    private Tokens tokens;

    public FestivalGoer(UUID id, String name, Tokens tokens) {
        this.id = id;
        this.name = name;
        this.tokens = tokens;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Tokens getTokens() {
        return tokens;
    }

    public void setTokens(Tokens tokens) {
        this.tokens = tokens;
    }
}
