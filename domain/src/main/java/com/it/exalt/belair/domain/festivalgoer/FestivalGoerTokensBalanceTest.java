package com.it.exalt.belair.domain.festivalgoer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import java.util.UUID;

class FestivalGoerTokensBalanceTest {
    @Test
    void testConsultTokensBalance() {
        //given
        UUID goerId = UUID.randomUUID();
        Tokens tokens = new Tokens(6, 9);
        FestivalGoer goer = new FestivalGoer(goerId, "Alice", tokens);
        FestivalGoerRepository repo = id -> id.equals(goerId) ? Optional.of(goer) : Optional.empty();
        ConsultTokensBalanceUseCase useCase = new ConsultTokensBalanceUseCase(repo);
        ConsultTokensBalanceQuery query = new ConsultTokensBalanceQuery(goerId);

        //when
        Tokens result = useCase.handle(query);

        //then
        assertEquals(6, result.getDrinkTokens());
        assertEquals(9, result.getSnackTokens());
    }

    @Test
    void testConsultTokensBalanceGoerNotFound() {
        //given
        FestivalGoerRepository repo = id -> Optional.empty();
        ConsultTokensBalanceUseCase useCase = new ConsultTokensBalanceUseCase(repo);
        ConsultTokensBalanceQuery query = new ConsultTokensBalanceQuery(UUID.randomUUID());

        //when & then
        assertThrows(IllegalArgumentException.class, () -> useCase.handle(query));
    }
}
