package com.it.exalt.belair.domain.festivalgoer;

import java.util.Optional;

/**
 * Use case for consulting the token balance of a festival goer.
 */
public class ConsultTokensBalanceUseCase {
    private final FestivalGoerRepository repository;

    public ConsultTokensBalanceUseCase(FestivalGoerRepository repository) {
        this.repository = repository;
    }

    /**
     * Returns the token balance for the given festival goer id.
     * @param query the query containing the festival goer id
     * @return Tokens balance
     * @throws IllegalArgumentException if festival goer not found
     */
    public Tokens handle(ConsultTokensBalanceQuery query) {
        Optional<FestivalGoer> goerOpt = repository.findById(query.getFestivalGoerId());
        FestivalGoer goer = goerOpt.orElseThrow(() -> new IllegalArgumentException("Festival goer not found"));
        return goer.getTokens();
    }
}
