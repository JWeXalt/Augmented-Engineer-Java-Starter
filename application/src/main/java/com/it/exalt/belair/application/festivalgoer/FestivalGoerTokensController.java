package com.it.exalt.belair.application.festivalgoer;

import com.it.exalt.belair.domain.festivalgoer.ConsultTokensBalanceQuery;
import com.it.exalt.belair.domain.festivalgoer.ConsultTokensBalanceUseCase;
import com.it.exalt.belair.domain.festivalgoer.Tokens;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST Controller for consulting festival goer token balances.
 */
@RestController
@RequestMapping("/api/festivalgoers/{id}/tokens")
public class FestivalGoerTokensController {
    private final ConsultTokensBalanceUseCase useCase;

    public FestivalGoerTokensController(ConsultTokensBalanceUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public ResponseEntity<TokensBalanceDTO> getTokensBalance(@PathVariable("id") UUID id) {
        Tokens tokens = useCase.handle(new ConsultTokensBalanceQuery(id));
        return ResponseEntity.ok(new TokensBalanceDTO(tokens.getDrinkTokens(), tokens.getSnackTokens()));
    }
}
