package com.it.exalt.belair.application.festivalgoer;

import com.it.exalt.belair.domain.festivalgoer.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FestivalGoerTokensControllerTest {
    @Test
    void testGetTokensBalance() {
        //given
        UUID goerId = UUID.randomUUID();
        Tokens tokens = new Tokens(5, 8);
        ConsultTokensBalanceUseCase useCase = Mockito.mock(ConsultTokensBalanceUseCase.class);
        Mockito.when(useCase.handle(Mockito.any())).thenReturn(tokens);
        FestivalGoerTokensController controller = new FestivalGoerTokensController(useCase);

        //when
        ResponseEntity<TokensBalanceDTO> response = controller.getTokensBalance(goerId);

        //then
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(5, response.getBody().getDrinkTokens());
        assertEquals(8, response.getBody().getSnackTokens());
    }
}
