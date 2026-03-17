package com.it.exalt.belair.domain.festivalgoer;

import java.util.UUID;

/**
 * Query for consulting the token balance of a festival goer.
 */
public class ConsultTokensBalanceQuery {
    private final UUID festivalGoerId;

    public ConsultTokensBalanceQuery(UUID festivalGoerId) {
        this.festivalGoerId = festivalGoerId;
    }

    public UUID getFestivalGoerId() {
        return festivalGoerId;
    }
}
