package com.it.exalt.belair.application.festivalgoer;

import org.springframework.stereotype.Component;

/**
 * Validator for TokensBalanceDTO (example, can be extended for input validation if needed).
 */
@Component
public class TokensBalanceDTOValidator {
    public boolean isValid(TokensBalanceDTO dto) {
        return dto.getDrinkTokens() >= 0 && dto.getSnackTokens() >= 0;
    }
}
